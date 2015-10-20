package com.wirelust.bitbucket.example.services;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.wirelust.bitbucket.client.BitbucketV2Client;
import com.wirelust.bitbucket.client.representations.User;
import com.wirelust.bitbucket.example.AuthService;

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
			response.close();
		}
		return user;
	}

}
