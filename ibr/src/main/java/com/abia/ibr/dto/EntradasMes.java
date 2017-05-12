package com.abia.ibr.dto;

import java.math.BigDecimal;

import com.abia.ibr.model.Tipo;

public class EntradasMes {

	private String dataMovimento;
	private BigDecimal valor;
	private Tipo tipo;

	public EntradasMes() {
	}

	public EntradasMes(String dataMovimento, BigDecimal valor) {
		this.dataMovimento = dataMovimento;
		this.valor = valor;
	}

	public String getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(String dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	

}
