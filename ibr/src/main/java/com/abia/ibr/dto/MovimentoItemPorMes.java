package com.abia.ibr.dto;

import java.math.BigDecimal;

public class MovimentoItemPorMes {

	String codigo;
	
	String nome;
	
	BigDecimal valorMes;
	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorMes() {
		return valorMes;
	}

	public void setValorMes(BigDecimal valorMes) {
		this.valorMes = valorMes;
	}
	
	
}
