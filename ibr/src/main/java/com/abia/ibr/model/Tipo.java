package com.abia.ibr.model;

public enum Tipo {
	C("Crédito"),
	D("Débito");
	
	private String descricao;
	
	Tipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { 
		return descricao;
	}
	
}
