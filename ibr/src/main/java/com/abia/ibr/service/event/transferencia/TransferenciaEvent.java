package com.abia.ibr.service.event.transferencia;

import com.abia.ibr.model.Transferencia;

public class TransferenciaEvent {

	private Transferencia transferencia;

	public TransferenciaEvent(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

}
