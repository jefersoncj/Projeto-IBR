package com.abia.ibr.service.event.usuario;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.MovimentosContas;
import com.abia.ibr.repository.Usuarios;

@Component
public class UsuarioListener {

	
	@Autowired
	Usuarios usuarios;
	
	@Autowired
	MovimentosContas movimentosContas;
	@EventListener
	public void movimentoIncluido(UsuarioEvent contaEvent) {
		if (contaEvent != null) {
			
			 Usuario usuario = usuarios.findOne(contaEvent.getUsuario().getCodigo());
			
			 if(usuario.getTenantId() == null || usuario.getTenantId().equals("")){
				 usuario.setTenantId(usuario.getCodigo().toString());
			 }
			 usuarios.save(usuario);
		     
		}
			
	}
	
}
