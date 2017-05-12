package com.abia.ibr.controller.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.SubGrupo;



public class SubGrupoConverter implements Converter<String, SubGrupo> {

	@Override
	public SubGrupo convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			SubGrupo subGrupo = new SubGrupo();
			subGrupo.setCodigo(Long.valueOf(codigo));
			return subGrupo;
		}
		
		return null;
	}

}