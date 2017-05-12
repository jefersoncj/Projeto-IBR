package com.abia.ibr.service.event.Item;

import com.abia.ibr.model.Item;

public class ItemEvent {

	private Item item;

	public ItemEvent(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	

}
