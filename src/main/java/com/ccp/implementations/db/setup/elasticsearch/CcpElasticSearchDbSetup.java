package com.ccp.implementations.db.setup.elasticsearch;

import com.ccp.dependency.injection.CcpInstanceProvider;

public class CcpElasticSearchDbSetup implements CcpInstanceProvider {

	@Override
	public Object getInstance() {
		return new ElasticSearchDbSetupCreator();
	}

}
