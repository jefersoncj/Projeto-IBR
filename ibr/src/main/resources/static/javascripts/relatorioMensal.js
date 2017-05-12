var Ibr = Ibr || {};

Ibr.ComboMesAno = (function(){

	function ComboMesAno(){
		this.combo = $('#combo-mesAno');
		
	}
	
	ComboMesAno.prototype.iniciar = function() {
		this.combo.on('change', mesAnoAterado.bind(this));
	}
	
	function mesAnoAterado(){
		var url = this.combo.data('url');
		window.location = url+this.combo.val();
		
	}
	
	return ComboMesAno;

}());


$(function(){
	
	var ComboMesAno = new Ibr.ComboMesAno();
	ComboMesAno.iniciar();

	
}())



