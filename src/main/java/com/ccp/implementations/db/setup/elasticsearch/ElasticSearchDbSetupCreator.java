package com.ccp.implementations.db.setup.elasticsearch;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpFolderDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.especifications.db.setup.CcpDbSetupCreator;
import com.ccp.especifications.http.CcpHttpResponse;

class ElasticSearchDbSetupCreator implements CcpDbSetupCreator {
	
	private final HttpRequester httpRequester = new HttpRequester();

	@Override
	public void createTables(String folder) {
		CcpFolderDecorator directory = new CcpStringDecorator(folder).folder();
		directory.readFiles(new CreateTableConsumer());
	}

	@Override
	public void insertValues(String prefix, String folder) {
		CcpStringDecorator ccpStringDecorator = new CcpStringDecorator(folder);
		CcpFolderDecorator directory = ccpStringDecorator.folder();
		InsertValuesConsumer consumer = new InsertValuesConsumer(prefix);
		directory.readFolders(consumer);
		
	}

	@Override
	public void dropAllTables() {
		String uri = "/_all";
		this.httpRequester.executeHttpRequest(uri, "DELETE", "", 200);
	}

	@Override
	public void deleteAllData() {
		String url = "/_all/_delete_by_query";
		
		String matchAll = CcpConstants.EMPTY_JSON
						.putSubKey("query", "match_all", CcpConstants.EMPTY_JSON)
						.asJson();
		
		CcpHttpResponse endpointResponse = this.httpRequester.executeHttpRequest(url, "POST", matchAll);

		if (endpointResponse.isClientError()) {
			new CcpTimeDecorator().sleep(1000);
			this.deleteAllData();
			return;
		}
		
	}
	

}
