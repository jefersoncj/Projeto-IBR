package com.abia.ibr.service.event.transferencia;



import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.MovimentosContas;

@Component
public class TransferenciaListener {

	@Autowired
	private MovimentosContas movimentosContas;
	
	@Autowired
	Contas contas;
	
	@EventListener
	public void transferenciaIncluida(TransferenciaEvent transferenciaEvent) {
		MovimentoConta movimentoConta = movimentosContas.findOne(transferenciaEvent.getTransferencia().getMovimentoConta().getCodigo());
		MovimentoConta movimento = new MovimentoConta();
		if (movimentoConta != null) {
			
			Conta contaACreditar = contas.findOne(transferenciaEvent.getTransferencia().getContaACreditar().getCodigo());
			 
			 movimento.setDataMovimento(movimentoConta.getDataMovimento());
			 movimento.setCentroCusto("Transferência de "+ contas.findOne( movimentoConta.getConta().getCodigo()).getNome());
			 movimento.setValor(movimentoConta.getValor());
			 movimento.setSaldoAnterior(contaACreditar.getSaldo());
			 movimento.setUsuario(movimentoConta.getUsuario());
			 movimento.setMovimento(movimentoConta.getMovimento());
			 movimento.setConta(transferenciaEvent.getTransferencia().getContaACreditar());
			 movimento.setTipo(Tipo.C);
			 movimentosContas.save(movimento);
		     
			 BigDecimal valor = movimentoConta.getValor();
			 Conta contaADebitar = transferenciaEvent.getTransferencia().getMovimentoConta().getConta();
			 contaADebitar.setSaldo(contaADebitar.getSaldo().subtract(valor));
			 contas.save(contaADebitar);
			 
			
			 contaACreditar.setSaldo(contaACreditar.getSaldo().add(valor));
			 contas.save(contaACreditar);
		}
			
	}
	
}
