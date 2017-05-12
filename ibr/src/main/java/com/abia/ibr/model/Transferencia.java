package com.abia.ibr.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


public class Transferencia {

	@NotNull(message = "Conta a debitar obrigatória.")
	private Conta contaADebitar;
	
	@NotNull(message = "Conta a creditar obrigatória.")
	private Conta contaACreditar;
	
	@NotNull(message = "Valor é obrigatória.")
	@Column(name = "valor")
	private BigDecimal valor;
	
	private MovimentoConta movimentoConta;

	public Conta getContaADebitar() {
		return contaADebitar;
	}

	public void setContaADebitar(Conta contaADebitar) {
		this.contaADebitar = contaADebitar;
	}

	public Conta getContaACreditar() {
		return contaACreditar;
	}

	public void setContaACreditar(Conta contaACreditar) {
		this.contaACreditar = contaACreditar;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public MovimentoConta getMovimentoConta() {
		return movimentoConta;
	}

	public void setMovimentoConta(MovimentoConta movimentoConta) {
		this.movimentoConta = movimentoConta;
	}

}
