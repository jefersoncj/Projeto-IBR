package com.abia.ibr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abia.ibr.model.Saldo;
import com.abia.ibr.repository.Saldos;



@Service
public class SaldoService {
	
	@Autowired
	private Saldos saldos;
	
	@Transactional
	public void salvar(Saldo saldo) {
		 saldos.save(saldo);
		 
	}
	
}