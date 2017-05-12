package com.abia.ibr.service.event.grupoConta;

import com.abia.ibr.model.GrupoConta;

public class GrupoContaEvent {

	private GrupoConta grupoConta;

	public GrupoContaEvent(GrupoConta grupoConta) {
		this.grupoConta = grupoConta;
	}

	public GrupoConta getGrupoConta() {
		return grupoConta;
	}

	

}
