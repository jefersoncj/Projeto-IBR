package com.abia.ibr.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.model.Transferencia;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.security.UsuarioSistema;
import com.abia.ibr.service.CadastroTransferenciaService;
import com.abia.ibr.service.exception.SaldoInsuficienteException;

@Controller
@RequestMapping("/trasnferencias")
public class TransferenciaController {

	@Autowired
	private Contas contas;
	
	@Autowired
	CadastroTransferenciaService cadastroTransferenciaService;
	
	@RequestMapping("/nova")
	public ModelAndView  nova(Transferencia transferencia){
		ModelAndView mv = new ModelAndView("transferencia/CadastroTransferencia");
		mv.addObject("contas", contas.findAll());
		return mv;
	}
	
	@RequestMapping(value= "/nova", method = RequestMethod.POST)
	public ModelAndView  cadastrar(@Valid Transferencia transferencia, BindingResult result, Model model, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		if(result.hasErrors()){
			return nova(transferencia);
		}
		
		try {
			 LocalDate data = LocalDate.now();
			 MovimentoConta movimentoConta =  new MovimentoConta();
			 
			 Conta contaADebitar = contas.findOne(transferencia.getContaADebitar().getCodigo());
			 
			 movimentoConta.setDataMovimento(data);
			 movimentoConta.setCentroCusto("Transferência para "+ contas.findOne(transferencia.getContaACreditar().getCodigo()).getNome());
			 movimentoConta.setValor(transferencia.getValor());
			 movimentoConta.setSaldoAnterior(contaADebitar.getSaldo());
			 movimentoConta.setUsuario(usuarioSistema.getUsuario());
			 movimentoConta.setConta(contaADebitar);
			 movimentoConta.setTipo(Tipo.D);
			 
			 transferencia.setMovimentoConta(movimentoConta);
			 cadastroTransferenciaService.salvar(transferencia);
		} catch (SaldoInsuficienteException e) {
			result.rejectValue("valor", e.getMessage(), e.getMessage());
			return nova(transferencia);
		}
		
		 attributes.addFlashAttribute("mensagem", "Transferência realizada com sucesso!");
		return new ModelAndView("redirect:/trasnferencias/nova");
		
	}

}
