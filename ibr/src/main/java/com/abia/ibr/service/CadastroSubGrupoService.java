package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.SubGrupos;
import com.abia.ibr.service.event.subGrupo.SubGrupoEvent;




@Service
public class CadastroSubGrupoService {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private SubGrupos subGrupos;
	
	@Transactional
	public void salvar(SubGrupo subGrupo) {
		 subGrupos.save(subGrupo);
		 publisher.publishEvent(new SubGrupoEvent(subGrupo));
	}
	
}