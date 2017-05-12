package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.Transferencia;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.MovimentosContas;
import com.abia.ibr.service.event.transferencia.TransferenciaEvent;
import com.abia.ibr.service.exception.SaldoInsuficienteException;



@Service
public class CadastroTransferenciaService {
	
	@Autowired
	private MovimentosContas movimentosContas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	Contas contas;
	
	@Transactional
	public void salvar(Transferencia transferencia) {
		Conta ContaADebitar = contas.findOne(transferencia.getContaADebitar().getCodigo());
		if (ContaADebitar.getSaldo().doubleValue() < transferencia.getValor().doubleValue() ) {
			throw new SaldoInsuficienteException("Saldo da conta " + ContaADebitar.getNome() + " insuficiente!");
		}
		 movimentosContas.save(transferencia.getMovimentoConta());
		 publisher.publishEvent(new TransferenciaEvent(transferencia));
	}
	
}