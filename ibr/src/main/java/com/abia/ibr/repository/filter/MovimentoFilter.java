package com.abia.ibr.repository.filter;





import java.time.LocalDate;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.Item;
import com.abia.ibr.model.Tipo;

public class MovimentoFilter {
	
	
	private LocalDate dataInicio;
	
	private LocalDate dataFim;
	
	private Tipo tipo;
	
	private Item item;
	
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}
