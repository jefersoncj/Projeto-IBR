package com.abia.ibr.dto;

public class MesAnoData {

	int mes;
	int ano;
	
	String mesAno;
	
	public MesAnoData() {
	}

	public MesAnoData(int mes, int ano, String mesAno) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.mesAno = mesAno;
	}

	public int getMes() {
		return mes;
	}


	public void setMes(int mes) {
		this.mes = mes;
	}


	public int getAno() {
		return ano;
	}


	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}
	
	
	
}
