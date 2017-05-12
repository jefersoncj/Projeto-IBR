package com.abia.ibr.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.dto.MovimentosMes;
import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.model.Item;
import com.abia.ibr.model.Movimento;
import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.Itens;
import com.abia.ibr.repository.Movimentos;
import com.abia.ibr.repository.filter.MovimentoFilter;
import com.abia.ibr.security.UsuarioSistema;
import com.abia.ibr.service.CadastroMovimentoService;

@Controller
@RequestMapping("/saidas")
public class SaidasController {

	@Autowired
	private Movimentos movimentos;
	
	@Autowired
	private Contas contas;
	
	@Autowired
	private Itens itens;
	
	@Autowired
	CadastroMovimentoService cadastroMovimentoService;
	

	
	@RequestMapping("/nova")
	public ModelAndView nova(Movimento saida) {
		ModelAndView mv = new ModelAndView("saida/CadastroSaida");
		mv.addObject("itens", itens.findAll());
		mv.addObject("contas", contas.findAll());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView cadastrar(@Valid Movimento movimento, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		if (result.hasErrors()) {
			
			return nova(movimento);
		}
		movimento.setTipo(Tipo.D);
		movimento.setUsuario(usuarioSistema.getUsuario());
		cadastroMovimentoService.salvar(movimento);
		attributes.addFlashAttribute("mensagem", "Saída salva com sucesso!");

		return new ModelAndView("redirect:/saidas/nova");
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Item> pesquisar(String nomeGrupoContaOuSubgrupo) {
		return itens.porNomeGrupoContaOuSubgrupo(nomeGrupoContaOuSubgrupo,"D");
	}
	
	@GetMapping
	public ModelAndView pesquisar(MovimentoFilter movimentoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("saida/BuscaSaidas");
		if (movimentoFilter.getDataInicio() == null && movimentoFilter.getDataFim() == null) {
			LocalDate hoje = LocalDate.now();
			LocalDate inicioMes = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			movimentoFilter.setDataInicio(inicioMes);
			movimentoFilter.setDataFim(hoje);
		}
	
		ArrayList<GrupoConta> grupoContas = new ArrayList<>();
		ArrayList<SubGrupo> subGrupos = new ArrayList<>();
		List<Item> listaItem = itens.porNomeGrupoContaOuSubgrupo("","D");
		
		for (Item item : listaItem) {
			grupoContas.add(item.getSubGrupo().getGrupoConta());
			subGrupos.add(item.getSubGrupo());
		}
		mv.addObject("grupoContas", grupoContas);
		mv.addObject("subGrupos", subGrupos);
		mv.addObject("itens", listaItem );
		mv.addObject("contas", contas.findAll());
		movimentoFilter.setTipo(Tipo.D);
		List<Movimento> listMovimento = movimentos.filtrar(movimentoFilter);
		mv.addObject("pagina", listMovimento);
		mv.addObject("totalListMovimento",getValorTotalMovimentos(listMovimento));
		return mv;
	}
	private BigDecimal getValorTotalMovimentos(List<Movimento> ListMovimento) {
		return ListMovimento.stream()
				.map(Movimento::getValor)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<MovimentosMes> listarTotalSaidaPorMes() {
		return movimentos.totalPorMes(Tipo.D);
	}
}
