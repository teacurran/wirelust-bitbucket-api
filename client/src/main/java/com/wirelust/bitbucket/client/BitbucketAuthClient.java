package com.wirelust.bitbucket.client;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Date: 04-Nov-2015
 *
 * @author T. Curran
 */
public interface BitbucketAuthClient {

	/**
	 * POST https://bitbucket.org/site/oauth2/access_token
	 */
	@POST
	@Path("/oauth2/access_token")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTokenByUsernamePassword(
		@FormParam("grant_type") String grantType,
		@FormParam("username") String username,
		@FormParam("password") String password);

}
