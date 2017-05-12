package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.Movimento;
import com.abia.ibr.repository.Movimentos;
import com.abia.ibr.service.event.movimento.MovimentoEvent;



@Service
public class CadastroMovimentoService {
	
	@Autowired
	private Movimentos movimentos;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Movimento movimento) {
		 movimentos.save(movimento);
		 publisher.publishEvent(new MovimentoEvent(movimento));
	}
	
}