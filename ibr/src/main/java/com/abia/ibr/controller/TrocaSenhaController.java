package com.abia.ibr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.filter.UsuarioTrocaSenhaFilter;
import com.abia.ibr.security.UsuarioSistema;
import com.abia.ibr.service.CadastroUsuarioService;
import com.abia.ibr.service.exception.SenhaAtualUsuarioException;
import com.abia.ibr.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/account")
public class TrocaSenhaController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
		
	@RequestMapping("/troca-senha")
	public ModelAndView novo(UsuarioTrocaSenhaFilter usuarioTrocaSenhaFilter) {
		ModelAndView mv = new ModelAndView("account/TrocaSenha");
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		Usuario usuario = new Usuario();
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			if (obj instanceof UsuarioSistema){
			UsuarioSistema u = (UsuarioSistema) obj;
			usuario=  u.getUsuario();
			}
		}
		mv.addObject(usuario);
		return mv;
	}
	
	@PostMapping("/troca-senha")
	public ModelAndView salvar(@Valid UsuarioTrocaSenhaFilter usuarioTrocaSenhaFilter, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuarioTrocaSenhaFilter);
		}
	
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		Usuario usuario = new Usuario();
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			if (obj instanceof UsuarioSistema){
			UsuarioSistema u = (UsuarioSistema) obj;
			usuario=  u.getUsuario();
			}
		}
		
		try {
			usuario.setSenha(usuarioTrocaSenhaFilter.getNovaSenha());
			cadastroUsuarioService.salvar(usuario);
		} catch (SenhaAtualUsuarioException e) {
			result.rejectValue("senhaDigitada", e.getMessage(), e.getMessage());
			return novo(usuarioTrocaSenhaFilter);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("novaSenha", e.getMessage(), e.getMessage());
			return novo(usuarioTrocaSenhaFilter);
		}
		
		attributes.addFlashAttribute("mensagem", "Senha alterada com sucesso");
		return new ModelAndView("redirect:/account/troca-senha");
	}
	
}
