package com.abia.ibr.dto;

import java.math.BigDecimal;

import com.abia.ibr.model.Tipo;

public class MovimentosMes {

	private String dataMovimento;
	private BigDecimal valor;
	
	private Tipo tipo;

	public MovimentosMes() {
	}

	public MovimentosMes(String dataMovimento, BigDecimal valor, Tipo tipo) {
		this.dataMovimento = dataMovimento;
		this.valor = valor;
		this.tipo = tipo;
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
