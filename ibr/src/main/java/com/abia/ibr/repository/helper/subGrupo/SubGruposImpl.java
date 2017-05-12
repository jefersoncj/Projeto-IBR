package com.abia.ibr.repository.helper.subGrupo;

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

import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.filter.SubGrupoFilter;
import com.abia.ibr.repository.paginacao.PaginacaoUtil;

public class SubGruposImpl implements SubGruposQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public List<SubGrupo> porSubGrupo(String tipo) {	
		String jpql = "select gc from GrupoConta gc  "
				+ "inner join Item it "
				+ "where ipo = (:tipo)";
		
		List<SubGrupo> grupoContasFiltrados = manager.createQuery(jpql, SubGrupo.class)
					.setParameter("tipo", tipo)
					.getResultList();
		return grupoContasFiltrados;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<SubGrupo> filtrar(SubGrupoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(SubGrupo.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(SubGrupoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(SubGrupo.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(SubGrupoFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
		if (filtro.getGrupoConta() != null && filtro.getGrupoConta().getCodigo()!=null) {
			criteria.add(Restrictions.eq("grupoConta", filtro.getGrupoConta()));
		}
	}
	
public String buscaUltimaReferencia(String referencia) {
	String optional = manager.createQuery("select max(referencia) from SubGrupo where referencia like :referencia", String.class)
					.setParameter("referencia",referencia+"%")
					.getSingleResult();
		return optional;
	}


}
