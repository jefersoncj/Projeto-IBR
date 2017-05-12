package com.abia.ibr.service.event.movimento;

import com.abia.ibr.model.Movimento;

public class MovimentoEvent {

	private Movimento movimento;

	public MovimentoEvent(Movimento movimento) {
		this.movimento = movimento;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	

}
