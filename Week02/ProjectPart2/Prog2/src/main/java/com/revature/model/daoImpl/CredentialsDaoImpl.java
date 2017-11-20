package com.revature.model.daoImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.revature.model.DBProperties;
import com.revature.model.interfaces.dao.CredentialsDao;
import com.revature.model.interfaces.dao.Dao;
import com.revature.util.JDBC;

public class CredentialsDaoImpl<T> implements CredentialsDao<T> {

	private static String fetchCreds = DBProperties.ROOT_SQL + CredentialsDaoImpl.class.getSimpleName();

//	public CredentialsDaoImpl() {
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
//	}

	@SuppressWarnings("unchecked")
	@Override
	public T readFrom() {
		return (T) fetchCredentials(false);
	}

	@Override
	public void writeTo(Protocol comm) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public T readFromAtomic() {
		return (T) fetchCredentials(true);
	}

	@Override
	public boolean writeToAtomic(Protocol comm) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <U> List<List<U>> fetchCredentials(boolean atomic) {
		List<String> queries = new LinkedList<String>();
		queries.add(fetchCreds);
		return (List<List<U>>) JDBC.query(queries, Protocol.FETCH, atomic);
	}

}
