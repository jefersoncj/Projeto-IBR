package com.abia.ibr.repository.helper.item;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abia.ibr.model.Item;
import com.abia.ibr.repository.filter.ItemFilter;

public interface ItensQueries {

	
	public List<Item> porNomeGrupoContaOuSubgrupo(String nomeGrupoContaOuSubgrupo,String tipo);
	public Page<Item> filtrar(ItemFilter filtro, Pageable pageable);
	public String buscaUltimaReferencia(String referencia);
}
