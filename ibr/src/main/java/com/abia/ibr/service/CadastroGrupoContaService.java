package com.abia.ibr.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.GrupoContas;
import com.abia.ibr.service.event.grupoConta.GrupoContaEvent;
import com.abia.ibr.service.exception.NomeGrupoJaCadastradoException;



@Service
public class CadastroGrupoContaService {

	@Autowired
	private GrupoContas grupos;


	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public GrupoConta salvar(GrupoConta grupo) {
		Optional<GrupoConta> estiloOptional = grupos.findByNomeIgnoreCase(grupo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeGrupoJaCadastradoException("Nome do Grupo já cadastrado");
		}
		grupos.saveAndFlush(grupo);
		publisher.publishEvent(new GrupoContaEvent(grupo));
		return grupo;
		
	}
	
	
}