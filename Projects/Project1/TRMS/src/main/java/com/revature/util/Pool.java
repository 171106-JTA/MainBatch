package com.revature.util;

public interface Pool<T> {
	
	void createPool(int size);
	
	T borrowObject(boolean atomic);
	
	boolean returnObject(T type, boolean atomic);
	
	boolean freePool();
	
	
}
