package com.abia.ibr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.Saldo;
import com.abia.ibr.repository.helper.saldo.SaldosQueries;

@Repository
public interface Saldos extends JpaRepository<Saldo, Long>, SaldosQueries{

}
