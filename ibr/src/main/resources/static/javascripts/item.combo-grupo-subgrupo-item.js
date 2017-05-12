var Ibr = Ibr || {};

Ibr.ComboGrupo = (function(){

	function ComboGrupo(){
		this.combo = $('#combo-grupo');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
		
	}
	
	ComboGrupo.prototype.iniciar = function() {
		this.combo.on('change', onGrupoAlterado.bind(this));
	}
	
	function onGrupoAlterado(){
		this.emitter.trigger('alterado', this.combo.val());
		
	}
	
	return ComboGrupo;

}());


Ibr.ComboSubGrupo  = (function() {
	
	function ComboSubGrupo(comboGrupo) {
		this.comboGrupo = comboGrupo;
		this.combo = $('#combo-subGrupo');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenSubGrupoSelecionado = $('#inputHiddenSubGrupoSelecionado');
		
		
	}
	
	ComboSubGrupo.prototype.iniciar = function() {
		reset.call(this);
		this.comboGrupo.on('alterado', onGrupoAlterado.bind(this));
		var codigoGrupo = this.comboGrupo.combo.val();
		inicializarSubgrupo.call(this, codigoGrupo);
	}
	
	function onGrupoAlterado(evento,codigoGrupo ){
		this.inputHiddenSubGrupoSelecionado.val('');
		inicializarSubgrupo.call(this, codigoGrupo);
		
	}
	
	function inicializarSubgrupo(codigoGrupo) {
		if (codigoGrupo) {
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data:{'grupo': codigoGrupo},
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});	
			resposta.done(onBuscarSubGrupoFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarSubGrupoFinalizado(subGrupos) {
		var options = [];
		options.push('<option value="">Selecione o SubGrupo</option>');
		subGrupos.forEach(function(subGrupo) {
			options.push('<option value="' + subGrupo.codigo + '">' + subGrupo.nome + '</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoSubGrupoSelecionad = this.inputHiddenSubGrupoSelecionado.val();
		console.log(codigoSubGrupoSelecionad)
		
		if (codigoSubGrupoSelecionad) {
			this.combo.val(codigoSubGrupoSelecionad);
		}
		this.combo.selectpicker('refresh');
	}
	
	function reset() {
		this.combo.html('<option value="">Selecione o SubGrupo</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
		this.combo.selectpicker('refresh');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	return ComboSubGrupo;
	
}());


$(function(){
	
	var comboGrupo = new Ibr.ComboGrupo();
	comboGrupo.iniciar();
	
	var comboSubGrupo = new Ibr.ComboSubGrupo(comboGrupo);
	comboSubGrupo.iniciar();
	
}())



