package p1.revature.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class BaseServlet extends HttpServlet {
	/**
	 * Returns the "last" part of the request URI 
	 * @param req : the request to the servlet
	 * @return the service the caller (client) is requesting, fetched from the end of the request URI
	 */
	public static String getRequestedService(HttpServletRequest req)
	{
		String url  = req.getRequestURI();
		System.out.println("INCOMING REQUEST: " + url);
		String serviceRequested = url.substring(url.lastIndexOf('/') + 1, url.length());
		return serviceRequested;
	}
}
