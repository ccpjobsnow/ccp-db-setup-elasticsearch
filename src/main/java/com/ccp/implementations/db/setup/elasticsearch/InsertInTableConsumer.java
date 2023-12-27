package com.ccp.implementations.db.setup.elasticsearch;

import java.util.Map;
import java.util.function.Consumer;

import com.ccp.decorators.CcpFileDecorator;
import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.http.CcpHttpResponse;
import com.ccp.especifications.json.CcpJsonHandler;

public class InsertInTableConsumer implements Consumer<CcpFileDecorator> {

	private static final CcpJsonHandler json = CcpDependencyInjection.getDependency(CcpJsonHandler.class);
	
	private final HttpRequester httpRequester = new HttpRequester();

	private final String prefix;
	
	public InsertInTableConsumer(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void accept(CcpFileDecorator t) {
		String _json = t.extractStringContent();
		String index = t.parent.getName();
		Map<String, Object> fromJson = json.fromJson(_json);
		this.create(index, fromJson);
		

	}

	private void create(String index, Map<String, Object> fromJson) {
		CcpMapDecorator values = new CcpMapDecorator(fromJson);
		String camelCase = new CcpStringDecorator(index).text().toCamelCase();
		CcpEntity baseEntity;
		try {
			String packageName = "com.{prefix}.commons.entities".replace("{prefix}", this.prefix);

			String capitalize = new CcpStringDecorator(this.prefix).text().capitalize();
			String className = packageName + ".{prefix}Entity".replace("{prefix}", capitalize) + camelCase;
			baseEntity = (CcpEntity)Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String id = baseEntity.getId(values);
		String url = "/" + index + "/_doc/" + id;
		String asJson = values.asJson();
		CcpHttpResponse executeHttpRequest = this.httpRequester.executeHttpRequest(url, "POST", asJson);

		if(executeHttpRequest.isSuccess()) {
			return;
		}
		
		
		throw new RuntimeException("erro ao inserir em " + index);
	}

}
