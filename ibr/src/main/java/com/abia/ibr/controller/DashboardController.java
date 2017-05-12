package com.abia.ibr.controller;



import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abia.ibr.model.Tipo;
import com.abia.ibr.repository.Movimentos;

@Controller
public class DashboardController {
	
	
	
	@Autowired
	private Movimentos movimentos;

	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("entradasNoAno", movimentos.valorTotalNoAno(Tipo.C));
		mv.addObject("entradasNoMes", movimentos.valorTotalNoMes(Tipo.C));
		
		mv.addObject("saidasNoAno", movimentos.valorTotalNoAno(Tipo.D));
		mv.addObject("saidasNoMes", movimentos.valorTotalNoMes(Tipo.D));
		
		LocalDate ano = LocalDate.now();
		mv.addObject("balancoEntrada", movimentos.balancoEntrada(ano.getYear()));
		mv.addObject("balancoTotal", movimentos.balancoTotal(ano.getYear()));
		return mv;
	}
	
}
