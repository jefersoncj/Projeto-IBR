package com.abia.ibr.repository.helper.item;

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

import com.abia.ibr.model.Item;
import com.abia.ibr.repository.filter.ItemFilter;
import com.abia.ibr.repository.paginacao.PaginacaoUtil;

public class ItensImpl implements ItensQueries{


	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public List<Item> porNomeGrupoContaOuSubgrupo(String nomeGrupoContaOuSubgrupo,String tipo) {
		String jpql = "select it from Item it  "
				+ "inner join it.subGrupo sg "
				+ "inner join it.subGrupo.grupoConta g "
				+ "where lower(it.nome) like lower(:nomeGrupoContaOuSubgrupo) and Tipo = (:tipo) or lower(g.nome) like lower(:nomeGrupoContaOuSubgrupo) and Tipo = (:tipo) or lower(sg.nome)like lower(:nomeGrupoContaOuSubgrupo) and Tipo = (:tipo)";
		
		
		
		List<Item> itensFiltrados = manager.createQuery(jpql, Item.class)
					.setParameter("nomeGrupoContaOuSubgrupo", nomeGrupoContaOuSubgrupo + "%")
					.setParameter("tipo", tipo)
					.getResultList();
		return itensFiltrados;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Item> filtrar(ItemFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Item.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("subGrupo", "sb");
		criteria.createAlias("subGrupo.grupoConta", "gc");
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(ItemFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Item.class);
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("subGrupo", "sb");
		criteria.createAlias("subGrupo.grupoConta", "gc");
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ItemFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
		if (filtro.getSubGrupo() != null && filtro.getSubGrupo().getCodigo() != null) {
			criteria.add(Restrictions.eq("sb.codigo", filtro.getSubGrupo().getCodigo()));
		}
		if (filtro.getSubGrupo() != null && filtro.getSubGrupo().getGrupoConta() != null && filtro.getSubGrupo().getGrupoConta().getCodigo() != null) {
			criteria.add(Restrictions.eq("gc.codigo", filtro.getSubGrupo().getGrupoConta().getCodigo()));
		}
		
	}
	
	public String buscaUltimaReferencia(String referencia) {
		
		String optional = manager.createQuery("select max(referencia) from Item where referencia like :referencia", String.class)
						.setParameter("referencia",referencia+"%")
						.getSingleResult();
			return optional;
		}


}
