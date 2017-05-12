package com.abia.ibr.repository.helper.saldo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.abia.ibr.model.Saldo;

public class SaldosImpl implements SaldosQueries{

	@PersistenceContext
	private EntityManager manager;
	

	@Override	
	public  List<Saldo> buscaSaldosPorMes(LocalDate data) {
		
		String jpql = "SELECT s FROM Saldo s WHERE month(data) = :mes and year(data) = :ano";
		List<Saldo> saldo =   manager.createQuery(jpql, Saldo.class)
				.setParameter("mes", data.getMonthValue())
				.setParameter("ano", data.getYear())
				.getResultList();
	
			return saldo;
		}

	@Override	
	public  LocalDate mesAnterior(LocalDate data) {
			
		LocalDate dataAnteriro = manager.createQuery("SELECT MAX(data) FROM Saldo s WHERE s.data < :mes ", LocalDate.class)
					.setParameter("mes", data)
						.getSingleResult();
			return dataAnteriro;
		}
	
	@Override	
	public  BigDecimal buscaSaldoMesAnteiror(LocalDate data) {
		LocalDate dataAnterior = mesAnterior(data);
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("SELECT SUM(saldo) FROM Saldo s WHERE data = :mes) ", BigDecimal.class)
				.setParameter("mes", dataAnterior)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
		
		}
	
	
	@Override	
	public  List<Saldo> buscaSaldosApos(LocalDate data) {
		
		String jpql = "SELECT s FROM Saldo s WHERE data > :data ORDER BY  data ASC";
		List<Saldo> saldo =   manager.createQuery(jpql, Saldo.class)
				.setParameter("data", data)
				.getResultList();
	
			return saldo;
		}

}
