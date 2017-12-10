package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Loader;
import com.revature.utilities.CloserUtility;

public class LoaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Loader loader;
	private PrintWriter write;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		System.out.println("loader");
		try {
			loader = new Loader();
			response.setContentType("application/json");
			write = response.getWriter();
			write.write(loader.toJSON());
		} catch (IOException e) {
			System.err.println("Loader servlet encountered IOException.");
			response.setStatus(500);
		} catch (SQLException e) {
			System.err.println("Loader servlet encountered SQLException.");
			response.setStatus(500);
		} finally {
			CloserUtility.close(write);
		}
	}
}
