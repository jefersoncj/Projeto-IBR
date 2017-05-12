package com.abia.ibr.repository.helper.grupoConta;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.filter.GrupoContaFilter;

public interface GrupoContasQueries {
	public List<GrupoConta> porGrupoConta(String tipo);
	public Page<GrupoConta> filtrar(GrupoContaFilter filtro, Pageable pageable);
	public Long buscaUltimaReferencia();
}
