package com.abia.ibr.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.abia.ibr.validation.validator.AtributoConfirmaSenhaUsuarioValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoConfirmaSenhaUsuarioValidator.class })
public @interface  AtributoConfirmaSenhaUsuario {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Atributos senhaAtual não confere";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String atributoConfirmacao();
}
