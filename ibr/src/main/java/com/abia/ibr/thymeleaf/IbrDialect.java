package com.abia.ibr.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.abia.ibr.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.abia.ibr.thymeleaf.processor.MenuAttributeTagProcessor;
import com.abia.ibr.thymeleaf.processor.MessageElementTagProcessor;
import com.abia.ibr.thymeleaf.processor.OrderElementTagProcessor;
import com.abia.ibr.thymeleaf.processor.PaginationElementTagProcessor;

public class IbrDialect  extends AbstractProcessorDialect {

	public IbrDialect() {
		super("Abia Ibr", "ibr", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
