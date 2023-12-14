package com.ccp.implementations.db.setup.elasticsearch;

import com.ccp.decorators.CcpFolderDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.especifications.db.setup.CcpDbSetupCreator;

class ElasticSearchDbSetupCreator implements CcpDbSetupCreator {
	
	private final HttpRequester httpRequester = new HttpRequester();

	@Override
	public void createTables(String folder) {
		CcpFolderDecorator directory = new CcpStringDecorator(folder).folder();
//		directory.readFiles(new CreateTableConsumer());
	}

	@Override
	public void insertValues(String folder) {
		
	}

	@Override
	public void dropAllTables() {
		String uri = "/_all";
		this.httpRequester.executeHttpRequest(uri, "DELETE", "", 200);
	}
	

}
