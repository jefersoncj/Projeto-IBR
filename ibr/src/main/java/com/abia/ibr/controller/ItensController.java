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
import com.abia.ibr.model.Item;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.GrupoContas;
import com.abia.ibr.repository.Itens;
import com.abia.ibr.repository.SubGrupos;
import com.abia.ibr.repository.filter.ItemFilter;
import com.abia.ibr.service.CadastroItemService;

@Controller
@RequestMapping("/itens")
public class ItensController {


	@Autowired
	private GrupoContas grupoContas;
	
	@Autowired
	private SubGrupos subGrupos;
	
	
	Item itemRetoro;
	
	@Autowired
	Itens itens;
	
	@Autowired
	private CadastroItemService cadastroItemService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Item item) {
		ModelAndView mv = new ModelAndView("item/CadastroItem");
		mv.addObject("grupoContas", grupoContas.findAll());
		mv.addObject("tipos", Tipo.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Item item, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			
			return novo(item);
		}
		
		 cadastroItemService.salvar(item);
		 attributes.addFlashAttribute("mensagem", "Item salvo com sucesso!");

		return new ModelAndView("redirect:/itens/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ItemFilter itemFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("item/PesquisaItem");
		mv.addObject("grupoContas", grupoContas.findAll());
		mv.addObject("subGrupos", subGrupos.findAll());
		PageWrapper<Item> paginaWrapper = new PageWrapper<>(itens.filtrar(itemFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
