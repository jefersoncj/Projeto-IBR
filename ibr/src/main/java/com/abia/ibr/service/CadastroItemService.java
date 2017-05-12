package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.Item;
import com.abia.ibr.repository.Itens;
import com.abia.ibr.service.event.Item.ItemEvent;

@Service
public class CadastroItemService {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private Itens itens;
	
	@Transactional
	public void salvar(Item item) {
		 itens.save(item);
		 publisher.publishEvent(new ItemEvent(item));
	}
	
}