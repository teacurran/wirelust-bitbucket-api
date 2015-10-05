package com.wirelust.bitbucket.example;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * Date: 04-Oct-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class AuthService implements Serializable {

	private static final long serialVersionUID = -8425071345263257300L;

	@Inject
	ApplicationConfig applicationConfig;

	public void login() throws OAuthSystemException, IOException {

		OAuthClientRequest oAuthClientRequest = OAuthClientRequest
			.authorizationLocation(ApplicationConfig.BITBUCKET_AUTH_URL)
			.setClientId(applicationConfig.getBitbucketOauthKey())
			.setRedirectURI(applicationConfig.getBitbucketSecret())
			.buildQueryMessage();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        externalContext.redirect(oAuthClientRequest.getLocationUri());

	}
}
