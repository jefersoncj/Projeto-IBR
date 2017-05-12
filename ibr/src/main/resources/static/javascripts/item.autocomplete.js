var Ibr = Ibr || {};

Ibr.Autocomplete = (function() {
	
	function Autocomplete() {
		this.nomeGrupoOuSubgrupInput = $('.js-nome-item-input');
		this.inputHiddenItemSelecionado = $('#inputHiddenItemSelecionado');
		this.inputHiddenCentroCustoSelecionado = $('#inputHiddenCentroCustoSelecionado');
		
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			
			url: function(nomeGrupoOuSubgrupo) {
				return this.nomeGrupoOuSubgrupInput.data('url') +'?nomeGrupoContaOuSubgrupo='+ nomeGrupoOuSubgrupo;
			}.bind(this),
		
			getValue: function(element) {
				 	
			        return element.subGrupo.grupoConta.nome + ' - ' + element.subGrupo.nome + ' - ' + element.nome 
			    },
			    minCharNumber: 3,
				requestDelay: 300,
				ajaxSettings: {
					contentType: 'application/json'
				},
			    
			list :{
				onChooseEvent:function(){
					var item = ('item', this.nomeGrupoOuSubgrupInput.getSelectedItemData());
					this.inputHiddenItemSelecionado.val(item.codigo);
					this.inputHiddenCentroCustoSelecionado.val(item.subGrupo.grupoConta.nome + ' - ' + item.subGrupo.nome + ' - ' + item.nome);
				}.bind(this)
			}
			    
		};
		
		this.nomeGrupoOuSubgrupInput.easyAutocomplete(options);
	}
	
	return Autocomplete
	
}());

$(function() {
	
	var autocomplete = new Ibr.Autocomplete();
	autocomplete.iniciar();
	
})

