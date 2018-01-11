package com.revature.model.interfaces.product;

import java.sql.Connection;

public interface Pool<T> {
	
	public void createPool(int size);
	public T borrowObject(boolean...atomic);
	boolean returnObject(Connection c);
	public boolean freePool();
}
