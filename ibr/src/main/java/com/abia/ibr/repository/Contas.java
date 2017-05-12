package com.abia.ibr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.Conta;
import com.abia.ibr.repository.helper.conta.ContasQueries;

@Repository
public interface Contas extends JpaRepository<Conta, Long>, ContasQueries{

	public Optional<Conta> findByNomeIgnoreCase(String nome);
}
