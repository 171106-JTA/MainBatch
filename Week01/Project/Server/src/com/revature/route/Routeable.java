package com.revature.route;

import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

public interface Routeable {
	public Resultset handleRequest(FieldParams requestParams) throws RequestException;
}
