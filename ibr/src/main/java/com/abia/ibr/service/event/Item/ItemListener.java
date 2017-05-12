package com.abia.ibr.service.event.Item;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.Item;
import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.Itens;
import com.abia.ibr.repository.SubGrupos;

@Component
public class ItemListener {

	@Autowired
	private Itens itens;
	
	@Autowired
	private SubGrupos subgrupos;

	
	@EventListener
	public void itemIncluido(ItemEvent itemEvent) {
		Item item = itens.findOne(itemEvent.getItem().getCodigo());
		SubGrupo sb = subgrupos.findOne(itemEvent.getItem().getSubGrupo().getCodigo());
		if (item != null) {
			  String referencia1 = (itens.buscaUltimaReferencia(sb.getReferencia()+"."));
			  int valor2;
			  String[] stringDividida = null;
			  if(referencia1 != null){
			  stringDividida = referencia1.split("[.]");
			  int valor = Integer.parseInt(stringDividida[2]);
			  valor2 = valor+1;
			  }else{
				  valor2 = 1;	
			  }
			  String referencia = (sb.getReferencia()+"."+valor2);
			//  String referencia = (item.getGrupoConta().getCodigo()+"."+item.getSubGrupo().getCodigo()+"."+item.getCodigo());
		      item.setReferencia(referencia);
		      itens.save(item);
		}
			
	}
	
}
