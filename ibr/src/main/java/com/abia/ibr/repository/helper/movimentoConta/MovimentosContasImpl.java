package com.abia.ibr.repository.helper.movimentoConta;

import java.math.BigDecimal;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.filter.MovimentoContaFilter;


public class MovimentosContasImpl  implements MovimentosContasQueries{

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<MovimentoConta> filtrar(MovimentoContaFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MovimentoConta.class);
		
		
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("conta", "c");
		
		return criteria.list();
	}
	

	
	private void adicionarFiltro(MovimentoContaFilter filtro, Criteria criteria) {
	if(filtro != null){
		
			if(!StringUtils.isEmpty(filtro.getDataInicio()) && !StringUtils.isEmpty(filtro.getDataFim())){
			criteria.add(Restrictions.between("dataMovimento", filtro.getDataInicio(),filtro.getDataFim()));
			}
			
			if(filtro.getConta() != null && filtro.getConta().getCodigo() != null){
				criteria.add(Restrictions.eq("c.codigo", filtro.getConta().getCodigo()));
			}
						
			criteria.addOrder(Order.desc("dataMovimento"));
		}
	}
	
	@Override
	public BigDecimal valorTotalNoAno(Tipo tipo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento where year(dataMovimento) = :ano and tipo = :tipo ", BigDecimal.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("tipo", tipo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTotalNoMes(Tipo tipo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento where month(dataMovimento) = :mes and tipo = :tipo ", BigDecimal.class)
					.setParameter("mes", MonthDay.now().getMonthValue())
					.setParameter("tipo", tipo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	

}

