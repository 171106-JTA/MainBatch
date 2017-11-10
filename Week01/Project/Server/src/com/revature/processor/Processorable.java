package com.revature.processor;

import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

public interface Processorable {
	public Resultset process(FieldParams requestParams) throws RequestException;
}
