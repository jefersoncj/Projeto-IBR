package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.repository.MovimentosContas;



@Service
public class CadastroMovimentoContaService {
	
	@Autowired
	private MovimentosContas movimentosContas;
	
	@Transactional
	public void salvar(MovimentoConta movimentoConta) {
		 movimentosContas.save(movimentoConta);
		 
	}
	
}