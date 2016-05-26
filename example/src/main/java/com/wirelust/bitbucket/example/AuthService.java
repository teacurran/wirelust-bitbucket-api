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

import com.wirelust.bitbucket.example.exceptions.AuthException;
import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
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

	String authCode;
	String accessToken;
	Long expiresIn;

	public void login() throws AuthException {
		OAuthClientRequest oAuthClientRequest;
		try {
			oAuthClientRequest = OAuthClientRequest
				.authorizationLocation(ApplicationConfig.BITBUCKET_AUTH_URL)
				.setClientId(applicationConfig.getBitbucketOauthKey())
				.setRedirectURI(applicationConfig.getBitbucketOauthRedirectUrl())
				.setResponseType(ResponseType.CODE.toString())
				.buildQueryMessage();
		} catch (OAuthSystemException e) {
			throw new AuthException("Unable to make oAuthClientRequest", e);
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		try {
			externalContext.redirect(oAuthClientRequest.getLocationUri());
		} catch (IOException e) {
			throw new AuthException("unable to redirect", e);
		}
	}

	public void checkOauthCode() throws AuthException {
		if (isLoggedIn() || authCode == null) {
			return;
		}

		OAuthClientRequest oAuthClientRequest;
		try {
			oAuthClientRequest = OAuthClientRequest
			.tokenLocation(ApplicationConfig.BITBUCKET_TOKEN_URL)
			.setGrantType(GrantType.AUTHORIZATION_CODE)
			.setRedirectURI(applicationConfig.getBitbucketOauthRedirectUrl())
			.setCode(authCode)
			.buildBodyMessage();
		} catch (OAuthSystemException e) {
			throw new AuthException("Unable to check oAuth code", e);
		}

		// Bitbucket requires the client_id and secret to be sent with Basic Auth
		// OLTU doesn't appear to support this, so we are going to extend the client
		// and add the proper request header for basic auth.
		String authString = applicationConfig.getBitbucketOauthKey() + ":" + applicationConfig.getBitbucketSecret();
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		final String authStringEnc = new String(authEncBytes);

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient()) {
			@Override
			public <T extends OAuthAccessTokenResponse> T accessToken(
				OAuthClientRequest request, String requestMethod, Class<T> responseClass)
				throws OAuthSystemException, OAuthProblemException {

				Map<String, String> headers = new HashMap<>();
				headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);
				headers.put("Authorization", "Basic " + authStringEnc);

				return httpClient.execute(request, headers, requestMethod, responseClass);
			}
		};

		OAuthJSONAccessTokenResponse oAuthResponse;
		try {
			oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuth.HttpMethod.POST);
		} catch (OAuthSystemException | OAuthProblemException e) {
			throw new AuthException("Unable to get oAuth token", e);
		}

		accessToken = oAuthResponse.getAccessToken();
		expiresIn = oAuthResponse.getExpiresIn();
	}

	public boolean isLoggedIn() {
		return accessToken != null;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
