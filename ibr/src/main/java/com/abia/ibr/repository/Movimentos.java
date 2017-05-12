package com.abia.ibr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.Movimento;
import com.abia.ibr.repository.helper.movimento.MovimentosQueries;

@Repository
public interface Movimentos extends JpaRepository<Movimento, Long>,MovimentosQueries{

 List<Movimento>  findAllByOrderByDataMovimentoDescCodigoDesc();
 				 

}
