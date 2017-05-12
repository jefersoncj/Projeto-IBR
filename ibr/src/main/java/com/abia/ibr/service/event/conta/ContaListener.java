package com.abia.ibr.service.event.conta;



import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.MovimentosContas;
import com.abia.ibr.repository.Usuarios;

@Component
public class ContaListener {

	@Autowired
	private Contas contas;
	
	@Autowired
	Usuarios usuarios;
	
	@Autowired
	MovimentosContas movimentosContas;
	@EventListener
	public void movimentoIncluido(ContaEvent contaEvent) {
		if (contaEvent != null) {
			
			 Conta conta = contas.findOne(contaEvent.getConta().getCodigo());
			
			 LocalDate hoje = LocalDate.now();
		     MovimentoConta movimentoConta = new MovimentoConta();
			 movimentoConta.setDataMovimento(hoje);
			 movimentoConta.setCentroCusto("Saldo anterior");
			 movimentoConta.setValor(conta.getSaldo());
			 movimentoConta.setSaldoAnterior(conta.getSaldo());
			 Usuario  usuario = usuarios.findOne(contaEvent.getConta().getUsuario().getCodigo());
			 movimentoConta.setUsuario(usuario);
			 movimentoConta.setConta(conta);
			 movimentoConta.setTipo(Tipo.C);
			 movimentosContas.save(movimentoConta);
			 
			 conta.setSaldoInicial(conta.getSaldo());
			 contas.save(conta);
		     
		}
			
	}
	
}
