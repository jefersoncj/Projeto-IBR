package com.abia.ibr.controller.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.GrupoConta;



public class GrupoConverter implements Converter<String, GrupoConta> {

	@Override
	public GrupoConta convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			GrupoConta grupo = new GrupoConta();
			grupo.setCodigo(Long.valueOf(codigo));
			return grupo;
		}
		
		return null;
	}

}