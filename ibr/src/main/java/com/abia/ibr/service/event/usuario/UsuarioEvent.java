package com.abia.ibr.service.event.usuario;

import com.abia.ibr.model.Usuario;

public class UsuarioEvent {

	private Usuario usuario;

	public UsuarioEvent(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	

}
