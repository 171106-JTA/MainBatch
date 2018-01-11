package com.revature.model.daoImpl;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import com.revature.model.DBProperties;
import com.revature.model.interfaces.dao.Dao;
import com.revature.util.JDBC;

public abstract class DaoImpl implements Dao {
	private static String[] files = { DBProperties.FETCH, DBProperties.INSERT, DBProperties.UPDATE,
			DBProperties.DELETE };
	private static String fetch;
	private static String insert;
	private static String update;
	private static String delete;
	private static String[] paths = { fetch, insert, update, delete };

	public static void readSQLFiles(Class<? extends DaoImpl> sub) {
		for (int i = 0; i < files.length; i++) {
			paths[i] = DBProperties.SQL_DIR + DaoImpl.class.asSubclass(sub).getSimpleName() + files[i];
			
			try(Stream<String> stream = Files.lines(Paths.get(paths[i]))){
				paths[i] = "";
				paths[i] += stream.map(line -> line + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public abstract Collection<Object> getObject(Set<Object> objects);
	public abstract void setObject(Object object, int prot);

}
