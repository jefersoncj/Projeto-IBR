package com.abia.ibr.repository.filter;

import org.hibernate.validator.constraints.NotBlank;

import com.abia.ibr.validation.AtributoConfirmaSenhaUsuario;
import com.abia.ibr.validation.AtributoConfirmacao;

@AtributoConfirmaSenhaUsuario( atributoConfirmacao = "senhaAtualDigitada"
, message = "Senna atual não confere")

@AtributoConfirmacao(atributo = "novaSenha", atributoConfirmacao = "confirmacaoSenha"
, message = "Confirmação da senha não confere")
public class UsuarioTrocaSenhaFilter {
	
	private String senhaAtualDigitada;
	private String senhaAtualDB;
	@NotBlank(message = "Informe a nova senha")
	private String novaSenha;
	private String confirmacaoSenha;
	
	
	public UsuarioTrocaSenhaFilter() {
	}


	public UsuarioTrocaSenhaFilter(String senhaAtualDigitada, String senhaAtualDB, String novaSenha,
			String confirmacaoSenha) {
		super();
		this.senhaAtualDigitada = senhaAtualDigitada;
		this.senhaAtualDB = senhaAtualDB;
		this.novaSenha = novaSenha;
		this.confirmacaoSenha = confirmacaoSenha;
	}


	public String getSenhaAtualDigitada() {
		return senhaAtualDigitada;
	}


	public void setSenhaAtualDigitada(String senhaAtualDigitada) {
		this.senhaAtualDigitada = senhaAtualDigitada;
	}


	public String getSenhaAtualDB() {
		return senhaAtualDB;
	}


	public void setSenhaAtualDB(String senhaAtualDB) {
		this.senhaAtualDB = senhaAtualDB;
	}


	public String getNovaSenha() {
		return novaSenha;
	}


	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}


	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}


	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	
	
}
