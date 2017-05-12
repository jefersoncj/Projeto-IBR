package com.abia.ibr.repository.helper.subGrupo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.filter.SubGrupoFilter;

public interface SubGruposQueries {
	public List<SubGrupo> porSubGrupo(String tipo);
	public String buscaUltimaReferencia(String referencia);
	public Page<SubGrupo> filtrar(SubGrupoFilter filtro, Pageable pageable);
}
