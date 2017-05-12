package com.abia.ibr.repository.helper.grupoConta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.filter.GrupoContaFilter;
import com.abia.ibr.repository.paginacao.PaginacaoUtil;

public class GrupoContasImpl implements GrupoContasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public List<GrupoConta> porGrupoConta(String tipo) {	
		String jpql = "select gc from GrupoConta gc  "
				+ "inner join Item it "
				+ "where ipo = (:tipo)";
		
		List<GrupoConta> grupoContasFiltrados = manager.createQuery(jpql, GrupoConta.class)
					.setParameter("tipo", tipo)
					.getResultList();
		return grupoContasFiltrados;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<GrupoConta> filtrar(GrupoContaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(GrupoConta.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(GrupoContaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(GrupoConta.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(GrupoContaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
	
	@Override
	public Long buscaUltimaReferencia() {
		
		Long ref = manager.createQuery("select max(referencia) from GrupoConta", Long.class)
						.getSingleResult();
			return ref;
		}

}
