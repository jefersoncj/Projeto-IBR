var Ibr = Ibr || {};

Ibr.GraficoVendaPorMes = (function() {
	
	function GraficoVendaPorMes() {
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');

	}
	
	GraficoVendaPorMes.prototype.iniciar = function() {
		$.ajax({
			url: 'saidas/totalPorMes',
			method: 'GET', 
			success: onSaidasRecebidas.bind(this)
		});
	}
	
	function onSaidasRecebidas(saidasMes) {
		

		//console.log(saidasMes);
		var saidasMeses = [];
		var saidasValores = [];
		var entradasValores = [];
		saidasMes.forEach(function(obj) {
			if(obj.tipo === 'D'){
			saidasMeses.push(obj.dataMovimento);
			saidasValores.push(obj.valor);
		
			}else{
				entradasValores.push(obj.valor);
			}
		});
		
		
		  
		  
		        
		//console.log(saidasMeses);
		var graficoVendasPorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data : {
					labels: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
				    	
				    	datasets: [{
				    		label: 'Entradas',
				    		backgroundColor: "rgba(92,184,92,0.38)",
			                pointBorderColor: "rgba(26,179,148,1)",
			                pointBackgroundColor: "#fff",
			                data: entradasValores,
			                
				    	},
				    	{
				    		label: 'Saídas',
				    		backgroundColor: "rgba(217,83,79,0.36)",
			                pointBorderColor: "rgba(26,179,148,0.5)",
			                pointBackgroundColor: "#fff",
			                data: saidasValores
				    		
				    	}
				    	]
				    },
				    
				    options: {
				    	 responsive: true,
	                    legend: {
	                        position: "bottom"
	                    },
	                    tooltips: {
	                        mode: 'label',
	                        bodySpacing: 10,
	                        cornerRadius: 5,
	                        titleMarginBottom: 10,
	                        callbacks: {
	                            label: function(tooltipItem, data) {
	                                return data.datasets[tooltipItem.datasetIndex].label+":" +" R$" + Ibr.formatarMoeda(Number(tooltipItem.yLabel).toFixed(0));                             
	                                }
	                           
	                        }
	                       
	                    },
	                    scales: {
	                        xAxes: [{
	                            ticks: {
	                            	
	                            }
	                        }],
	                        yAxes: [{
	                            ticks: {
	                            	beginAtZero: true,

	                                // Return an empty string to draw the tick line but hide the tick label
	                                // Return `null` or `undefined` to hide the tick line entirely
	                                userCallback: function(value, index, values) {
	                                    // Convert the number to a string and splite the string every 3 charaters from the end
	                                    value = value.toString();
	                                    value = Ibr.formatarMoeda(value);
	                                    return 'R$' + value;
	                                    }
	                            }
	                        }]
	                    }},
		    
		}
		
		);
		
	}
	
	return GraficoVendaPorMes;
	
}());

//Ibr.GraficoVendaPorOrigem = (function() {
//	
//	function GraficoVendaPorOrigem() {
//		this.ctx = $('#graficoVendasPorOrigem')[0].getContext('2d');
//	}
//	
//	GraficoVendaPorOrigem.prototype.iniciar = function() {
//		$.ajax({
//			url: '#',
//			method: 'GET', 
//			success: onDadosRecebidos.bind(this)
//		});
//	}
//	
//	function onDadosRecebidos(vendaOrigem) {
//		var meses = [];
//		var cervejasNacionais = [];
//		var cervejasInternacionais = [];
//		
//		vendaOrigem.forEach(function(obj) {
//			meses.unshift(obj.mes);
//			cervejasNacionais.unshift(obj.totalNacional);
//			cervejasInternacionais.unshift(obj.totalInternacional)
//		});
//		
//		var graficoVendasPorOrigem = new Chart(this.ctx, {
//		    type: 'bar',
//		    data: {
//		    	labels: meses,
//		    	datasets: [{
//		    		label: 'Nacional',
//		    		backgroundColor: "rgba(220,220,220,0.5)",
//	                data: cervejasNacionais
//		    	},
//		    	{
//		    		label: 'Internacional',
//		    		backgroundColor: "rgba(26,179,148,0.5)",
//	                data: cervejasInternacionais
//		    	}]
//		    },
//		});
//	}
//	
//	return GraficoVendaPorOrigem;
//	
//}());


$(function() {
	var graficoVendaPorMes = new Ibr.GraficoVendaPorMes();
	graficoVendaPorMes.iniciar();
	
//	var graficoVendaPorOrigem = new Ibr.GraficoVendaPorOrigem();
//	graficoVendaPorOrigem.iniciar();
});
