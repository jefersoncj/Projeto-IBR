package com.abia.ibr.repository.helper.conta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abia.ibr.model.Conta;
import com.abia.ibr.repository.filter.ContaFilter;

public interface ContasQueries {
	public Page<Conta> filtrar(ContaFilter filtro, Pageable pageable);
}
