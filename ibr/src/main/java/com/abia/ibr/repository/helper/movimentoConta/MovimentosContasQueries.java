package com.abia.ibr.repository.helper.movimentoConta;

import java.math.BigDecimal;
import java.util.List;

import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.filter.MovimentoContaFilter;

public interface MovimentosContasQueries {

	
	public  List<MovimentoConta> filtrar(MovimentoContaFilter filtro);
	
	public BigDecimal valorTotalNoAno(Tipo tipo);
	
	public BigDecimal valorTotalNoMes(Tipo tipo);
	
}
