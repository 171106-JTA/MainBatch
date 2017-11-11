package com.revature.processor;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

public interface Processorable {
	public Resultset process(Request request) throws RequestException;
}

