package com.abia.ibr.service.event.movimento;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abia.ibr.model.Conta;
import com.abia.ibr.model.Movimento;
import com.abia.ibr.model.MovimentoConta;
import com.abia.ibr.model.Saldo;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.Contas;
import com.abia.ibr.repository.Movimentos;
import com.abia.ibr.repository.MovimentosContas;
import com.abia.ibr.repository.Saldos;
import com.abia.ibr.repository.Usuarios;

@Component
public class MovimentoListener {

	@Autowired
	private Movimentos movimentos;
	
	@Autowired
	private Contas contas;
	
	@Autowired
	Usuarios usuarios;
	
	@Autowired
	MovimentosContas movimentosContas;
	
	@Autowired
	Saldos saldos;
	
	@EventListener
	public void movimentoIncluido(MovimentoEvent movimentoEvent) {
		Movimento movimento = movimentos.findOne(movimentoEvent.getMovimento().getCodigo());
		if (movimento != null) {
			
			 Conta conta = contas.findOne(movimento.getConta().getCodigo());
			
		     MovimentoConta movimentoConta = new MovimentoConta();
			 movimentoConta.setDataMovimento(movimento.getDataMovimento());
			 movimentoConta.setCentroCusto(movimento.getCentroCusto());
			 movimentoConta.setValor(movimento.getValor());
			 movimentoConta.setSaldoAnterior(conta.getSaldo());
			 Usuario  usuario = usuarios.findOne(movimento.getUsuario().getCodigo());
			 movimentoConta.setUsuario(usuario);
			 movimentoConta.setMovimento(movimento);
			 movimentoConta.setConta(movimento.getConta());
			 movimentoConta.setTipo(movimento.getTipo());
			 movimentosContas.save(movimentoConta);
			 
			
		    if(movimento.getTipo().equals(Tipo.C)){
		    	conta.setSaldo(conta.getSaldo().add(movimento.getValor()));
		    	contas.save(conta);
		    }else{
		    	conta.setSaldo(conta.getSaldo().subtract(movimento.getValor()));
		    	contas.save(conta);
		    }
		     
		    
		    
			BigDecimal totalEntradaMes = movimentos.totalEtradaMes(movimento.getDataMovimento()); 
			BigDecimal totalSaidaMes = movimentos.totalSaidaMes(movimento.getDataMovimento()); 
			BigDecimal saldoMes = totalEntradaMes.subtract(totalSaidaMes);
			
			
			LocalDate dataSaldo = movimento.getDataMovimento().withDayOfMonth(1);
			
			BigDecimal saldMesAnteriro = saldos.buscaSaldoMesAnteiror(dataSaldo);
			List<Saldo> sald = saldos.buscaSaldosPorMes(dataSaldo);
			
			if (sald.isEmpty()) {
				
				Saldo saldo = new Saldo();
				saldo.setData(dataSaldo);
				saldo.setSaldo(saldoMes.add(saldMesAnteriro));
				saldos.save(saldo);
			}else{
				Saldo s = sald.get(0);
				s.setSaldo((saldoMes.add(saldMesAnteriro)));
				saldos.save(s);
			}
			
			List<Saldo> saldoApos = saldos.buscaSaldosApos(dataSaldo);
			if (!saldoApos.isEmpty()) {
				for (Saldo saldo : saldoApos) {
					LocalDate dataSald = saldo.getData();
					BigDecimal totalEntradaMe = movimentos.totalEtradaMes(dataSald); 
					BigDecimal totalSaidaMe = movimentos.totalSaidaMes(dataSald); 
					BigDecimal saldoMe = totalEntradaMe.subtract(totalSaidaMe);
					     
					BigDecimal saldMesAnterir = saldos.buscaSaldoMesAnteiror(dataSald);
					saldo.setSaldo(saldoMe.add(saldMesAnterir));
					saldos.save(saldo);
					
				}
				
			}
			
			
		}
		
	

		
		
	}
	
}
