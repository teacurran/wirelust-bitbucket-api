package com.wirelust.bitbucket.example;

import javax.enterprise.context.ApplicationScoped;

/**
 * Date: 30-Sep-2015
 *
 * @author T. Curran
 */
@ApplicationScoped
public class Configuration {
	public static final String SYSTEM_PROPERTY_OAUTH_KEY = "bitbucket.oauth.key";
    public static final String SYSTEM_PROPERTY_OAUTH_SECRET = "bitbucket.oauth.secret";

	public String getOauthKey() {
		return System.getProperty(SYSTEM_PROPERTY_OAUTH_KEY);
	}

	public String getOauthSecret() {
		return System.getProperty(SYSTEM_PROPERTY_OAUTH_SECRET);
	}

}
