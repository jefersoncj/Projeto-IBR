package com.abia.ibr.repository.helper.movimento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.abia.ibr.dto.BalancoTotal;
import com.abia.ibr.dto.ItemPorDataFilter;
import com.abia.ibr.dto.MesAnoData;
import com.abia.ibr.dto.MovimentosMes;
import com.abia.ibr.dto.RelatorioMensal;
import com.abia.ibr.model.Movimento;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.filter.MovimentoFilter;

public interface MovimentosQueries {

	
	public  List<Movimento> filtrar(MovimentoFilter filtro);
	
	public BigDecimal valorTotalNoAno(Tipo tipo);
	
	public BigDecimal valorTotalNoMes(Tipo tipo);
	
	public List<MovimentosMes> totalPorMes(Tipo tipo);
	
	public List<ItemPorDataFilter> balancoSaida(Integer ano);
	
	public List<ItemPorDataFilter> balancoEntrada(Integer ano);
	
	public  List<Integer> anosMovimento();
	
	public List<BalancoTotal> balancoTotal(Integer ano);
	
	public List<RelatorioMensal> entradasMensal(LocalDate mesAno);
	
	public List<RelatorioMensal> saidasMensal(LocalDate mesAno);
	
	public  List<MesAnoData> MesesMovimento();
	
	public  BigDecimal totalEtradaMes(LocalDate data);
	
	public  BigDecimal totalSaidaMes(LocalDate data);
	
	
	public  BigDecimal saldoMesAnterior(LocalDate data);
	
	
}
