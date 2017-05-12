package com.abia.ibr.dto;

import java.math.BigDecimal;

import com.abia.ibr.model.Tipo;

public class TotalMesAno {

	
	Tipo tipo;
	BigDecimal valor;
	
	public TotalMesAno() {
	
	}
	public TotalMesAno(Tipo tipo, BigDecimal valor) {
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
}
