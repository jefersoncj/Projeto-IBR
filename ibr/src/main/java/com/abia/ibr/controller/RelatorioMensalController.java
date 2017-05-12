package com.abia.ibr.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abia.ibr.repository.Movimentos;
import com.abia.ibr.repository.Saldos;

@Controller
@RequestMapping("/relatorios")
public class RelatorioMensalController {
		
	@Autowired
	private Movimentos movimentos;
	
	@Autowired
	private Saldos saldos;

	@RequestMapping("resumo-mensal")
	public ModelAndView relatorio() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioMensal");
		mv.addObject("mesesAnos", movimentos.MesesMovimento());
		return mv;
	}
	
	@RequestMapping("/balanco")
	public ModelAndView balanco() {
		ModelAndView mv = new ModelAndView("relatorio/Balanco");
		mv.addObject("anos", movimentos.anosMovimento());
	
		return mv;
	}
	
	
	@GetMapping("/resumo-mensal/{mesAno}")
	public ModelAndView gerar(@PathVariable String mesAno) {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioMensal");
		String[] stringDividida = mesAno.split("[-]");
		int mes = Integer.parseInt(stringDividida[0]);
		int ano = Integer.parseInt(stringDividida[1]);
		LocalDate mesAn = LocalDate.now().withMonth(mes).withYear(ano);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMMM yyyy");
		mv.addObject("mesAnoFormatado",  mesAn.format(formatador));
		mv.addObject("mesesAnos", movimentos.MesesMovimento());
		mv.addObject("entradasMensal", movimentos.entradasMensal(mesAn));
		mv.addObject("saidasMensal", movimentos.saidasMensal(mesAn));
		mv.addObject("totalEntrada", movimentos.totalEtradaMes(mesAn));
		mv.addObject("totalSaida", movimentos.totalSaidaMes(mesAn));
//		System.out.println(mesAn);
//		if(mes > 1){
//			mes--;	
//		}else{
//			mes = 12;
//			ano--;
//		}
//		
		LocalDate mesAnterio = LocalDate.now().withDayOfMonth(1).withMonth(mes).withYear(ano);
		System.out.println(mesAnterio);
		mv.addObject("saldoMesAnterior", saldos.buscaSaldoMesAnteiror(mesAnterio));

		return mv;
	}
	
	@GetMapping("/balanco/{ano}")
	public ModelAndView editar(@PathVariable Integer ano) {
		ModelAndView mv = new ModelAndView("relatorio/Balanco");
		mv.addObject("anos", movimentos.anosMovimento());
		mv.addObject("balancoSaida", movimentos.balancoSaida(ano));
		mv.addObject("balancoEntrada", movimentos.balancoEntrada(ano));
		mv.addObject("balancoTotal", movimentos.balancoTotal(ano));
		return mv;
	}
}
