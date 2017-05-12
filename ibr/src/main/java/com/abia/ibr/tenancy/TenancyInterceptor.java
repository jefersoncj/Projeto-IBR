package com.abia.ibr.tenancy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abia.ibr.security.UsuarioSistema;

public class TenancyInterceptor extends HandlerInterceptorAdapter {
	
	
	public static String getTenantId() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			if (obj instanceof UsuarioSistema){
			UsuarioSistema u = (UsuarioSistema) obj;
			return u.getUsuario().getTenantId();
			}
		}
		
		return null;
	}

	
}
