package com.wirelust.bitbucket.client.representations.auth;

import java.io.Serializable;

/**
 * Date: 05-Nov-2015
 *
 * @author T. Curran
 */
public class OauthAccessToken implements Serializable {

	private static final long serialVersionUID = 7387646855881904997L;

	String accessToken;
	String scopes;
	Integer expiresIn;
	String refreshToken;
	String tokenType;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
