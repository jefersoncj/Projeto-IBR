package com.abia.ibr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.Item;
import com.abia.ibr.repository.helper.item.ItensQueries;

@Repository
public interface Itens extends JpaRepository<Item, Long>,ItensQueries{

}
