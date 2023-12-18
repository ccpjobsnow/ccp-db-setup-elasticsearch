package com.ccp.implementations.db.setup.elasticsearch;

import java.util.function.Consumer;

import com.ccp.decorators.CcpFileDecorator;

class InsertValuesConsumer implements Consumer<CcpFileDecorator> {

	private final HttpRequester httpRequester = new HttpRequester();

	@Override
	public void accept(CcpFileDecorator t) {
		String index = t.getName();
		String json = t.extractStringContent();
		this.httpRequester.executeHttpRequest("/" + index, "DELETE", "");
		this.httpRequester.executeHttpRequest("/" + index, "PUT", json, 200);
	}

	
}
