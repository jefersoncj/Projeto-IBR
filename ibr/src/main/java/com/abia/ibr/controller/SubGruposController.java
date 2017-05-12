package com.abia.ibr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abia.ibr.controller.page.PageWrapper;
import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.repository.GrupoContas;
import com.abia.ibr.repository.SubGrupos;
import com.abia.ibr.repository.filter.SubGrupoFilter;
import com.abia.ibr.service.CadastroSubGrupoService;



@Controller
@RequestMapping("/subgrupos")
public class SubGruposController {
	
	@Autowired
	CadastroSubGrupoService cadastroSubGrupoService;
	
	@Autowired
	private GrupoContas grupoContas;
	
	@Autowired
	private SubGrupos subgrupos;
	
	@RequestMapping("/novo")
	public ModelAndView novo(SubGrupo subGrupo){
		ModelAndView mv = new ModelAndView("subgrupo/CadastroSubGrupo");
		mv.addObject("grupoContas", grupoContas.findAll());
		return mv;
	}
	
	@RequestMapping(value= "/novo", method = RequestMethod.POST)
	public ModelAndView  cadastrar(@Valid SubGrupo subGrupo, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(subGrupo);
		}
		cadastroSubGrupoService.salvar(subGrupo);
		attributes.addFlashAttribute("mensagem", "SubGrupo salvo com sucesso!");
		
		return new ModelAndView("redirect:/subgrupos/novo");
		
	}
	
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<SubGrupo> pesquisaPorCodigoGrupo(
			@RequestParam(name = "grupo", defaultValue = "-1") Long codigoGrupo){
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {	}
		return subgrupos.findByGrupoContaCodigo(codigoGrupo);
	}
	
	
	@GetMapping
	public ModelAndView pesquisar(SubGrupoFilter subGrupoFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("subgrupo/PesquisaSubGrupo");
		mv.addObject("grupoContas", grupoContas.findAll());
		PageWrapper<SubGrupo> paginaWrapper = new PageWrapper<>(subgrupos.filtrar(subGrupoFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	
}
