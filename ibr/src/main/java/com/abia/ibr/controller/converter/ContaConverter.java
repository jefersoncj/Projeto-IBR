package com.abia.ibr.controller.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.Conta;



public class ContaConverter implements Converter<String, Conta> {

	@Override
	public Conta convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Conta conta = new Conta();
			conta.setCodigo(Long.valueOf(codigo));
			return conta;
		}
		
		return null;
	}

}