package com.abia.ibr.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.Conta;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.service.event.conta.ContaEvent;
import com.abia.ibr.service.exception.NomeGrupoJaCadastradoException;



@Service
public class CadastroContaService {

	@Autowired
	private Contas contas;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Conta salvar(Conta conta) {
		Optional<Conta> contaOptional = contas.findByNomeIgnoreCase(conta.getNome());
		if (contaOptional.isPresent()) {
			throw new NomeGrupoJaCadastradoException("Nome da Conta já cadastrado");
		}
		Conta contaRetorno =  contas.saveAndFlush(conta);
		publisher.publishEvent(new ContaEvent(conta));
		return contaRetorno;
		
	}
	
	
}