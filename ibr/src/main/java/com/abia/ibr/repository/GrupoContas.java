package com.abia.ibr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.helper.grupoConta.GrupoContasQueries;

@Repository
public interface GrupoContas extends JpaRepository<GrupoConta, Long>,GrupoContasQueries{

	public Optional<GrupoConta> findByNomeIgnoreCase(String nome);
}
