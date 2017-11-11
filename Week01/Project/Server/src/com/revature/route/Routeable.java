package com.revature.route;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

public interface Routeable {
	public Resultset handleRequest(Request request) throws RequestException;
}
