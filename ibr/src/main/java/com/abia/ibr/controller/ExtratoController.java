package com.abia.ibr.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.MovimentosContas;
import com.abia.ibr.repository.filter.MovimentoContaFilter;
import com.abia.ibr.service.CadastroMovimentoService;

@Controller
@RequestMapping("/extrato")
public class ExtratoController {

	@Autowired
	private MovimentosContas movimentosContas;
	
	@Autowired
	private Contas contas;

	@Autowired
	CadastroMovimentoService cadastroMovimentoService;
	
	
	@GetMapping
	public ModelAndView pesquisar(MovimentoContaFilter movimentoContaFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("extrato/Extrato");
			mv.addObject("contas", contas.findAll());
			ArrayList<MovimentoConta> listMovimento = new ArrayList<>();
			if (movimentoContaFilter.getDataInicio() != null && movimentoContaFilter.getDataFim() != null && movimentoContaFilter.getConta() != null ) {
			 listMovimento = (ArrayList<MovimentoConta>) movimentosContas.filtrar(movimentoContaFilter);
			}
			mv.addObject("pagina", listMovimento);
			mv.addObject("totalListMovimento",getValorTotalMovimentos(listMovimento));
			if(!listMovimento.isEmpty()){
			mv.addObject("saldoAnterior" , listMovimento.get(0).getSaldoAnterior());
			}else{
				mv.addObject("saldoAnterior" , BigDecimal.ZERO);
			}
		return mv;
	}
	private BigDecimal getValorTotalMovimentos(List<MovimentoConta> ListMovimento) {
		
		BigDecimal credito = BigDecimal.ZERO;
		BigDecimal debito = BigDecimal.ZERO;
		
		for (MovimentoConta movimentoConta : ListMovimento) {
			if(movimentoConta.getTipo().equals(Tipo.C)){
				credito = credito.add(movimentoConta.getValor());
			}else{
				debito = debito.add(movimentoConta.getValor());
			}
		}
		return credito.subtract(debito);
				
	}
	
}
