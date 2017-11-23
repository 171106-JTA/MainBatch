package com.revature.model.interfaces.factory;

import com.revature.model.interfaces.product.Pool;

public class PoolFactory implements AbstractFactory{

	@Override
	public <T> Class<Pool<T>> getFactory() {
		return new Pool<T>;
	}
	

}
