package com.revature.model.dao;

import java.util.HashMap;
import java.util.Map;

public interface Dao<T> {

	public void createDao();

	public default Dao<T> readFrom(int prot, Class<?> clazz) {
		String query = 
	}

	public default void writeTo(int prot) {

	}

	public default Dao<T> readFromAtomic(int prot) {

	}

	public default boolean writeToAtomic(int prot) {

	}

	public static enum DAO {
		USER, ADMIN, REQ, TRANSACT
	}

	// sql protocols
	public static enum DB_PROT {
		CREATE, FETCH, INSERT, UPDATE, DELETE, QUERY
	}

//	public static enum USER {
//		FETCH_FIN
//	}
}
