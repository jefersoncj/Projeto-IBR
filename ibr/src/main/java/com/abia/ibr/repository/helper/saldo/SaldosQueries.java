package com.abia.ibr.repository.helper.saldo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.abia.ibr.model.Saldo;

public interface SaldosQueries {
	
	public  List<Saldo> buscaSaldosPorMes(LocalDate data);
	public  LocalDate mesAnterior(LocalDate data);
	public  BigDecimal buscaSaldoMesAnteiror(LocalDate data);
	public  List<Saldo> buscaSaldosApos(LocalDate data);
}
