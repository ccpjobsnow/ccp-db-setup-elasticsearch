package com.ccp.implementations.db.setup.elasticsearch;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.utils.CcpDbRequester;
import com.ccp.especifications.http.CcpHttpRequester;
import com.ccp.especifications.http.CcpHttpResponse;

class HttpRequester {

	private final CcpHttpRequester httpRequester = CcpDependencyInjection.getDependency(CcpHttpRequester.class);

	private final CcpJsonRepresentation connectionDetails;
	private final String databaseUrl;

	public HttpRequester() {
		CcpDbRequester dependency = CcpDependencyInjection.getDependency(CcpDbRequester.class);
		this.connectionDetails = dependency.getConnectionDetails();
		this.databaseUrl = this.connectionDetails.getAsString("DB_URL");
	}

	public CcpHttpResponse executeHttpRequest(String url, String method, String body) {
		return httpRequester.executeHttpRequest(this.databaseUrl + url, method, this.connectionDetails, body);
	}

	public CcpHttpResponse executeHttpRequest(String url, String method, String body,
			int expectedStatus) {
		return httpRequester.executeHttpRequest(this.databaseUrl + url, method, this.connectionDetails, body, expectedStatus);
	}

}
