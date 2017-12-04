package com.revature.model.dao;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Map;


public interface Dao extends Serializable {

	boolean insert(boolean atomic) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException;
	boolean update(boolean atomic) throws IOException;
	boolean delete(boolean atomic) throws IOException;
	Map<String, String> fetch(boolean atomic);
	Collection<Map<String, String>> fetchAll(boolean atomic);
}
