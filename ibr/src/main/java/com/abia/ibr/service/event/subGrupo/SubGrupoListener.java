package com.abia.ibr.service.event.subGrupo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.SubGrupos;

@Component
public class SubGrupoListener {


	
	@Autowired
	private SubGrupos subGrupos;
	
	@EventListener
	public void subGrupoIncluido(SubGrupoEvent subGrupoEvent) {
		SubGrupo subGrupo = subGrupos.findOne(subGrupoEvent.getSubGrupo().getCodigo());
		if (subGrupo != null) {
			System.out.println(subGrupo.getReferencia());
			  String referencia1 = (subGrupos.buscaUltimaReferencia(subGrupo.getReferencia()+"."));
			  System.out.println(referencia1);
			  int valor2;
			  if(referencia1 != null){
			  String[] stringDividida = referencia1.split("[.]");
			  int valor = Integer.parseInt(stringDividida[1]);
			  valor2 = valor+1;
			  }else{
				  valor2 = 1;			 
			  }
			  String referencia = (subGrupo.getGrupoConta().getReferencia()+"."+valor2);
		      subGrupo.setReferencia(referencia);
		      subGrupos.save(subGrupo);
		}
			
	}
	
	
	
}
