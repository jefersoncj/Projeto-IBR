package com.abia.ibr.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.model.Grupo;
import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.Grupos;
import com.abia.ibr.service.CadastroUsuarioService;
import com.abia.ibr.service.exception.EmailUsuarioJaCadastradoException;
import com.abia.ibr.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/cadastro")
public class CadastreseController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	Grupos  grupos;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("Cadastrese");
		return mv;
	}
	
	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			usuario.setAtivo(true);
			Grupo grupo = grupos.findOne(new Long(1L));
			ArrayList<Grupo> grupos = new ArrayList<>();
			grupos.add(grupo);
			usuario.setGrupos(grupos);
			cadastroUsuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Cadastro salvo com sucesso");
		return new ModelAndView("redirect:/cadastro/novo");
	}
	

	
	
}
