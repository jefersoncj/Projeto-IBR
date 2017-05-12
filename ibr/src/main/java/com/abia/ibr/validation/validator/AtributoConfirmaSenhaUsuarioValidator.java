package com.abia.ibr.validation.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.abia.ibr.model.Usuario;
import com.abia.ibr.security.UsuarioSistema;
import com.abia.ibr.validation.AtributoConfirmaSenhaUsuario;



public class AtributoConfirmaSenhaUsuarioValidator implements ConstraintValidator<AtributoConfirmaSenhaUsuario, Object> {


	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmaSenhaUsuario constraintAnnotation) {

		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valido = false;
		
		try {
			String valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
			valido =ambosSaoIguais(valorAtributoConfirmacao);
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}
			
	
		if (!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean ambosSaoIguais(String valorAtributoConfirmacao) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		Usuario usuario = new Usuario();
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			if (obj instanceof UsuarioSistema){
			UsuarioSistema u = (UsuarioSistema) obj;
			usuario=  u.getUsuario();
			}
		}
		System.out.println(valorAtributoConfirmacao);
		return passwordEncoder.matches(valorAtributoConfirmacao,usuario.getSenha());
	}

}
