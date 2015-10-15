package com.wirelust.bitbucket.example.providers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.example.ApplicationConfig;
import com.wirelust.bitbucket.example.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.auth.oauth.OAuthFilter;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * Date: 15-Oct-2015
 *
 * @author T. Curran
 */
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

					//String base64Token = Base64.encodeBase64String(token.getBytes(StandardCharsets.UTF_8));
					requestContext.getHeaders().add("Authorization", "Bearer " + token);
				}
			});
		}

		ResteasyWebTarget target = client.target(ApplicationConfig.BITBUCKET_ENDPOINT_URL);
		BitbucketV2Client bitbucketV2Client = target.proxy(BitbucketV2Client.class);

		return bitbucketV2Client;
	}
}