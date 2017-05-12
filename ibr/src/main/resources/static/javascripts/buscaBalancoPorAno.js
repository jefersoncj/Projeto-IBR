var Ibr = Ibr || {};

Ibr.ComboAno = (function(){

	function ComboAno(){
		this.combo = $('#combo-ano');
		
	}
	
	ComboAno.prototype.iniciar = function() {
		this.combo.on('change', anoAterado.bind(this));
	}
	
	function anoAterado(){
		var url = this.combo.data('url');
		window.location = url+this.combo.val();
		
	}
	
	return ComboAno;

}());


$(function(){
	
	var ComboAno = new Ibr.ComboAno();
	ComboAno.iniciar();

	
}())



