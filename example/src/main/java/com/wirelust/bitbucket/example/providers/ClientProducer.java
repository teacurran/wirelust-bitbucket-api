package com.wirelust.bitbucket.example.providers;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.wirelust.bitbucket.client.BitBucketUndocumentedClient;
import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.example.ApplicationConfig;
import com.wirelust.bitbucket.example.AuthService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
@ApplicationScoped
public class ClientProducer {

	@Inject
	AuthService authService;

	@Produces
	public BitbucketV2Client getClient() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(JacksonConfigurationProvider.class);

		if (authService.isLoggedIn()) {
			client.register(new ClientRequestFilter() {
				@Override
				public void filter(ClientRequestContext requestContext) throws IOException {
					String token = authService.getAccessToken();

					requestContext.getHeaders().add("Authorization", "Bearer " + token);
				}
			});
		}

		ResteasyWebTarget target = client.target(ApplicationConfig.BITBUCKET_ENDPOINT_URL);

		return target.proxy(BitbucketV2Client.class);
	}

	@Produces
	public BitBucketUndocumentedClient getUndocumentedClient() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(JacksonConfigurationProvider.class);

		if (authService.isLoggedIn()) {
			client.register(new ClientRequestFilter() {
				@Override
				public void filter(ClientRequestContext requestContext) throws IOException {
					String token = authService.getAccessToken();

					requestContext.getHeaders().add("Authorization", "Bearer " + token);
				}
			});
		}

		ResteasyWebTarget target = client.target(ApplicationConfig.BITBUCKET_INTERNAL_ENDPOINT_URL);

		return target.proxy(BitBucketUndocumentedClient.class);
	}

}
