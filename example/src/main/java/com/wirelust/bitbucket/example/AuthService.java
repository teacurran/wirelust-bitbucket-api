package com.wirelust.bitbucket.example;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

/**
 * Date: 04-Oct-2015
 *
 * @author T. Curran
 */
@Named
@SessionScoped
public class AuthService implements Serializable {

	private static final long serialVersionUID = -8425071345263257300L;

	@Inject
	ApplicationConfig applicationConfig;

	String oAuthCode;
	String accessToken;
	Long expiresIn;

	public void login() throws OAuthSystemException, IOException {
		OAuthClientRequest oAuthClientRequest = OAuthClientRequest
			.authorizationLocation(ApplicationConfig.BITBUCKET_AUTH_URL)
			.setClientId(applicationConfig.getBitbucketOauthKey())
			.setRedirectURI(applicationConfig.getBitbucketOauthRedirectUrl())
			.setResponseType(ResponseType.CODE.toString())
			.buildQueryMessage();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		externalContext.redirect(oAuthClientRequest.getLocationUri());
	}

	public void checkOauthCode() throws OAuthSystemException, OAuthProblemException, IOException {
		if (isLoggedIn() || oAuthCode == null) {
			return;
		}

		OAuthClientRequest oAuthClientRequest = OAuthClientRequest
			.tokenLocation(ApplicationConfig.BITBUCKET_TOKEN_URL)
			.setGrantType(GrantType.AUTHORIZATION_CODE)
			.setRedirectURI(applicationConfig.getBitbucketOauthRedirectUrl())
			.setCode(oAuthCode)
			.buildBodyMessage();

		// Bitbucket requires the client_id and secret to be sent with Basic Auth
		// OLTU doesn't appear to support this, so we are going to extend the client
		// and add the proper request header for basic auth.
		String authString = applicationConfig.getBitbucketOauthKey() + ":" + applicationConfig.getBitbucketSecret();
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		final String authStringEnc = new String(authEncBytes);

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient()) {
			public <T extends OAuthAccessTokenResponse> T accessToken(
				OAuthClientRequest request, String requestMethod, Class<T> responseClass)
				throws OAuthSystemException, OAuthProblemException {

				Map<String, String> headers = new HashMap<String, String>();
				headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);
				headers.put("Authorization", "Basic " + authStringEnc);

				return httpClient.execute(request, headers, requestMethod, responseClass);
			}
		};


		OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuth.HttpMethod.POST);

		accessToken = oAuthResponse.getAccessToken();
		expiresIn = oAuthResponse.getExpiresIn();
	}

	public boolean isLoggedIn() {
		return accessToken != null;
	}

	public String getoAuthCode() {
		return oAuthCode;
	}

	public void setoAuthCode(String oAuthCode) {
		this.oAuthCode = oAuthCode;
	}
}
