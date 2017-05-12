package com.abia.ibr.controller.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.abia.ibr.model.Item;



public class ItemConverter implements Converter<String, Item> {

	@Override
	public Item convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Item item = new Item();
			item.setCodigo(Long.valueOf(codigo));
			return item;
		}
		
		return null;
	}

}