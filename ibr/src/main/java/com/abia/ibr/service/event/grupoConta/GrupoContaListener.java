package com.abia.ibr.service.event.grupoConta;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.GrupoContas;

@Component
public class GrupoContaListener {


	
	@Autowired
	private GrupoContas grupoContas;
	
	@EventListener
	public void subGrupoIncluido(GrupoContaEvent subGrupoEvent) {
		GrupoConta grupoConta = grupoContas.findOne(subGrupoEvent.getGrupoConta().getCodigo());
		  Long valor;
		if (grupoConta != null) {
			  Long referencia1 = (grupoContas.buscaUltimaReferencia());
			  if (referencia1 !=null) {
				  System.out.println(referencia1);
			  valor = referencia1 + new Long(1);
			  System.out.println(valor);
			  }else{
				  valor =  new Long(1);			 
			  }
		      grupoConta.setReferencia(valor);
		      grupoContas.save(grupoConta);
		}
	}
	
}
