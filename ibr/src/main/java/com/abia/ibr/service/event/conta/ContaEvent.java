package com.abia.ibr.service.event.conta;

import com.abia.ibr.model.Conta;

public class ContaEvent {

	private Conta conta;

	public ContaEvent(Conta conta) {
		this.conta = conta;
	}

	public Conta getConta() {
		return conta;
	}

	

}
