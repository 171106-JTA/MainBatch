package com.revature.model.dao;

import java.io.Serializable;
import java.util.Collection;


public interface Dao<T> extends Serializable {

	boolean insert(boolean atomic);
	boolean update(boolean atomic);
	boolean delete(boolean atomic);
	T fetch(boolean atomic);
	Collection<T> fetchAll(boolean atomic);
}
