package com.abia.ibr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.helper.subGrupo.SubGruposQueries;

@Repository
public interface SubGrupos extends JpaRepository<SubGrupo, Long>,SubGruposQueries{
	
		public List<SubGrupo> findByGrupoContaCodigo(Long codigoGrupo);
}
