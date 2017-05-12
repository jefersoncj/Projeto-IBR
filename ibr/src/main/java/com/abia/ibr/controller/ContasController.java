package com.abia.ibr.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.controller.page.PageWrapper;
import com.abia.ibr.model.Conta;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.filter.ContaFilter;
import com.abia.ibr.security.UsuarioSistema;
import com.abia.ibr.service.CadastroContaService;
import com.abia.ibr.service.exception.NomeGrupoJaCadastradoException;

@Controller
@RequestMapping("/contas")
public class ContasController {

	@Autowired
	CadastroContaService cadastroContaService;
	
	@Autowired
	private Contas contas;
	
	
	@RequestMapping("/nova")
	public ModelAndView  nova(Conta conta){
		ModelAndView mv = new ModelAndView("conta/CadastroConta");
		conta.setSaldo(new BigDecimal(0));
		return mv;
	}
	
	@RequestMapping(value= "/nova", method = RequestMethod.POST)
	public ModelAndView  cadastrar(@Valid Conta conta, BindingResult result, Model model, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		if(result.hasErrors()){
			return nova(conta);
		}

		try {
			conta.setUsuario(usuarioSistema.getUsuario());
			cadastroContaService.salvar(conta);
		} catch (NomeGrupoJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(conta);
		}
		
		attributes.addFlashAttribute("mensagem", "Conta salva com sucesso!");
		return new ModelAndView("redirect:/contas/nova");
		
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContaFilter contaFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("conta/PesquisaConta");
		
		PageWrapper<Conta> paginaWrapper = new PageWrapper<>(contas.filtrar(contaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}
