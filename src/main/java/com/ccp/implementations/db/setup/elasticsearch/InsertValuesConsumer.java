package com.ccp.implementations.db.setup.elasticsearch;

import java.util.function.Consumer;

import com.ccp.decorators.CcpFolderDecorator;

class InsertValuesConsumer implements Consumer<CcpFolderDecorator> {


	private final String prefix;
	
	public InsertValuesConsumer(String prefix) {
		this.prefix = prefix;
	}

	
	public void accept(CcpFolderDecorator t) {
		InsertInTableConsumer consumer = new InsertInTableConsumer(this.prefix);
		t.readFiles(consumer);
		
	}

	
	
	
}
