package com.abia.ibr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.repository.helper.movimentoConta.MovimentosContasQueries;

@Repository
public interface MovimentosContas extends JpaRepository<MovimentoConta, Long>,MovimentosContasQueries{

}
