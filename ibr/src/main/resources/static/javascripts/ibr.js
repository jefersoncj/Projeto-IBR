var Ibr = Ibr || {}

Ibr.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
		this.numero = $('.js-numero');
	}
	
	MaskMoney.prototype.enable = function() {
//		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
//		this.plain.maskMoney({ precision: 0, thousands: '.' });
		this.decimal.maskNumber({ decimal: ',', thousands: '.' });
		this.plain.maskNumber({ integer: true, thousands: '.' });
		this.numero.maskNumber({ integer: true, thousands: '' });
	}
	
	return MaskMoney;
	
}());

Ibr.LoadButton = (function() {
	
	function LoadButton() {
		this.button = $(".button");
	}
	LoadButton.prototype.enable = function() {
		this.button.on('click', onButtonClicado.bind(this));
	}
	
	function onButtonClicado(){
		this.button.html("<i class='fa fa-circle-o-notch fa-spin fa-1x fa-fw'></i>&nbsp;"+this.button.text());
	}
	
	return LoadButton;
	
	}());

Ibr.VerificaValor = (function() {
	
	function VerificaValor() {
		this.valor = $(".js-valor");
	}
	VerificaValor.prototype.enable = function() {
		this.valor.on('blur', onSaiuDoCampo.bind(this));
	}
	
	function onSaiuDoCampo(){
		var val = this.valor.val();
		if (val <= 0) {
			this.valor.val(0);
		
		}
	}
	
	return VerificaValor;
	
	}());

Ibr.MaskDate = (function() {
	
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
	
}());


Ibr.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

numeral.language('pt-br');

Ibr.formatarMoeda = function(valor) {
	return numeral(valor).format('0,0.00');
}

Ibr.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

$(function() {
	var maskMoney = new Ibr.MaskMoney();
	maskMoney.enable();
	
	var maskDate = new Ibr.MaskDate();
	maskDate.enable();
	
	var security = new Ibr.Security();
	security.enable();
	
	var loadButton = new Ibr.LoadButton();
	loadButton.enable();
	
	var verificaValor = new Ibr.VerificaValor();
	verificaValor.enable();
});