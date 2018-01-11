package com.revature.model.interfaces.product;

import java.sql.Connection;

public interface Pool<T> {

	public void createPool(int size);
	public T borrowObject();
	public boolean freePool();
	boolean returnObject(Connection c);
}
