package com.revature.processor;

import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exceptions.RequestException;

public interface Processorable {
	public Resultset handleRequest(FieldParams requestParams) throws RequestException;
}
