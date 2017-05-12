var Ibr = Ibr || {};

Ibr.MudaValorCorBox = (function() {
	
	function MudaValorCorBox() {
		this.valorAno= $('.js_valor_ano').html();
		this.valoMes = $('.js_valor_mes').html();

	}
	
	MudaValorCorBox.prototype.iniciar = function() {
		
	var valor1 = Ibr.recuperarValor(this.valorAno);
	if( valor1 < 0){
		$('.js_valor_ano_class').toggleClass('negativo');
	}
	
	var valor2 = Ibr.recuperarValor(this.valoMes);
	if( valor2 < 0){
		$('.js_valor_mes_class').toggleClass('negativo');
	}
		
	}
	
	return MudaValorCorBox;
	
}());

$(function() {
	var mudaValorCorBox = new Ibr.MudaValorCorBox();
	mudaValorCorBox.iniciar();

});
