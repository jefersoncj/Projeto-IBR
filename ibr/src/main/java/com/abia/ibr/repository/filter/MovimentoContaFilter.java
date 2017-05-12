package com.abia.ibr.repository.filter;





import java.time.LocalDate;

import com.abia.ibr.model.Conta;

public class MovimentoContaFilter {
	
	private LocalDate dataInicio;
	
	private LocalDate dataFim;
	
	private Conta conta;

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}
