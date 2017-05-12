package com.abia.ibr.repository.filter;

import com.abia.ibr.model.GrupoConta;

public class SubGrupoFilter {

	private String nome;
	
	private GrupoConta grupoConta;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoConta getGrupoConta() {
		return grupoConta;
	}

	public void setGrupoConta(GrupoConta grupoConta) {
		this.grupoConta = grupoConta;
	}

}
