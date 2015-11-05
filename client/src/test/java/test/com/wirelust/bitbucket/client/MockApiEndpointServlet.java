package test.com.wirelust.bitbucket.client;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class MockApiEndpointServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getRequestURI().substring(req.getContextPath().length());

		String outputMime = null;
		OutputStream out = resp.getOutputStream();

		InputStream is = this.getClass().getResourceAsStream(path + ".json");
		if (is != null) {
			outputMime = MediaType.APPLICATION_JSON;
		} else {
			 is = this.getClass().getResourceAsStream(path + ".txt");
			if (is != null) {
				outputMime = MediaType.APPLICATION_OCTET_STREAM;
			}
		}

		if (outputMime != null) {
			resp.setContentType(outputMime);
		}

		if (is == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			IOUtils.copy(is, out);
			is.close();
		}

		out.close();
	}

}
