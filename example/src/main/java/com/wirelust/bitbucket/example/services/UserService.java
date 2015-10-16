package com.wirelust.bitbucket.example.services;

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
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.representations.User;
import com.wirelust.bitbucket.example.ApplicationConfig;
import com.wirelust.bitbucket.example.AuthService;
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
public class UserService implements Serializable {

	private static final long serialVersionUID = -8425071345263257300L;

	@Inject
	AuthService authService;

	@Inject
	BitbucketV2Client bitbucketV2Client;

	User user;

	public User getUser() {
		if (user == null && authService.isLoggedIn()) {
			Response response = bitbucketV2Client.getUser();
			if (response.getStatus() == HttpServletResponse.SC_OK) {
				user = response.readEntity(User.class);
			}
		}
		return user;
	}

}
