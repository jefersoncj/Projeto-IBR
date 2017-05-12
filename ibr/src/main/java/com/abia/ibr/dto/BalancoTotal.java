package com.abia.ibr.dto;

public class BalancoTotal {
	String tipo;
	
	Double valorJan;
	
	Double valorFev;
	
	Double valorMar;
	
	Double valorAbr;
	
	Double valorMai;

	Double valorJun;
	
	Double valorJul;
	
	Double valorAgo;
	
	Double valorSet;
	
	Double valorOut;
	
	Double valorNov;
	
	Double valorDez;
	
	public BalancoTotal() {
		
	}

	public BalancoTotal(String tipo, Double valorJan, Double valorFev, Double valorMar, Double valorAbr,
			Double valorMai, Double valorJun, Double valorJul, Double valorAgo, Double valorSet, Double valorOut,
			Double valorNov, Double valorDez) {
		super();
		this.tipo = tipo;
		this.valorJan = valorJan;
		this.valorFev = valorFev;
		this.valorMar = valorMar;
		this.valorAbr = valorAbr;
		this.valorMai = valorMai;
		this.valorJun = valorJun;
		this.valorJul = valorJul;
		this.valorAgo = valorAgo;
		this.valorSet = valorSet;
		this.valorOut = valorOut;
		this.valorNov = valorNov;
		this.valorDez = valorDez;
	}
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getValorJan() {
		return valorJan;
	}

	public void setValorJan(Double valorJan) {
		this.valorJan = valorJan;
	}

	public Double getValorFev() {
		return valorFev;
	}

	public void setValorFev(Double valorFev) {
		this.valorFev = valorFev;
	}

	public Double getValorMar() {
		return valorMar;
	}

	public void setValorMar(Double valorMar) {
		this.valorMar = valorMar;
	}

	public Double getValorAbr() {
		return valorAbr;
	}

	public void setValorAbr(Double valorAbr) {
		this.valorAbr = valorAbr;
	}

	public Double getValorMai() {
		return valorMai;
	}

	public void setValorMai(Double valorMai) {
		this.valorMai = valorMai;
	}

	public Double getValorJun() {
		return valorJun;
	}

	public void setValorJun(Double valorJun) {
		this.valorJun = valorJun;
	}

	public Double getValorJul() {
		return valorJul;
	}

	public void setValorJul(Double valorJul) {
		this.valorJul = valorJul;
	}

	public Double getValorAgo() {
		return valorAgo;
	}

	public void setValorAgo(Double valorAgo) {
		this.valorAgo = valorAgo;
	}

	public Double getValorSet() {
		return valorSet;
	}

	public void setValorSet(Double valorSet) {
		this.valorSet = valorSet;
	}

	public Double getValorOut() {
		return valorOut;
	}

	public void setValorOut(Double valorOut) {
		this.valorOut = valorOut;
	}

	public Double getValorNov() {
		return valorNov;
	}

	public void setValorNov(Double valorNov) {
		this.valorNov = valorNov;
	}

	public Double getValorDez() {
		return valorDez;
	}

	public void setValorDez(Double valorDez) {
		this.valorDez = valorDez;
	}

	
}
