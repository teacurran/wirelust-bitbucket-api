/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wirelust.bitbucket.example.jaxrs;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

public class JaxRsClientFactory {

	public static ResteasyClientBuilder newClientBuilder() {
		final ResteasyClientBuilder builder = new ResteasyClientBuilder();
		configureHttpEngine(builder);
		return builder;
	}

	private static void configureHttpEngine(ResteasyClientBuilder clientBuilder) {
		final HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setRedirectStrategy(new DefaultRedirectStrategy() {
			@Override
			protected boolean isRedirectable(String method) {
				return true;
			}
		});

		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(40);
		connectionManager.setDefaultMaxPerRoute(40);

		final HttpClient client = builder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(30 * 1000).build()
		).setDefaultRequestConfig(customRequestConfig(RequestConfig.custom())).setConnectionManager(connectionManager)
			.build();
		final ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(client) {
			@Override
			protected HttpRequestBase createHttpMethod(String url, String restVerb) {
				final HttpRequestBase result = super.createHttpMethod(url, restVerb);
				final Builder base = result.getConfig() == null ? RequestConfig.custom() : RequestConfig.copy(result
					.getConfig());
				result.setConfig(customRequestConfig(base));
				return result;
			}
		};

		clientBuilder.httpEngine(engine);
	}

	private static RequestConfig customRequestConfig(RequestConfig.Builder base) {
		base.setRedirectsEnabled(true);
		base.setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000);
		return base.build();
	}
}
