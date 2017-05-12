package com.abia.ibr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.controller.page.PageWrapper;
import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.repository.GrupoContas;
import com.abia.ibr.repository.filter.GrupoContaFilter;
import com.abia.ibr.service.CadastroGrupoContaService;
import com.abia.ibr.service.exception.NomeGrupoJaCadastradoException;

@Controller
@RequestMapping("/grupos")
public class GrupoContasController {

	@Autowired
	CadastroGrupoContaService cadastroGrupoContaService;
	
	@Autowired
	private GrupoContas grupoContas;
	
	@RequestMapping("/novo")
	public ModelAndView  novo(GrupoConta grupo){
		ModelAndView mv = new ModelAndView("grupoConta/CadastroGrupoConta");
		return mv;
	}
	
	@RequestMapping(value= "/novo", method = RequestMethod.POST)
	public ModelAndView  cadastrar(@Valid GrupoConta grupoConta, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(grupoConta);
		}

		try {
			cadastroGrupoContaService.salvar(grupoConta);
		} catch (NomeGrupoJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(grupoConta);
		}
		
		attributes.addFlashAttribute("mensagem", "Grupo salvo com sucesso!");
		return new ModelAndView("redirect:/grupos/novo");
		
	}
	@GetMapping
	public ModelAndView pesquisar(GrupoContaFilter grupoContaFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("grupoConta/PesquisaGrupoConta");
		
		PageWrapper<GrupoConta> paginaWrapper = new PageWrapper<>(grupoContas.filtrar(grupoContaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}
