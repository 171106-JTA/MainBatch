package com.peterson.bowsercastle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * A class used for logging messages and serialization.
 * @author Alex
 */
public class UserStorage {
	private Logger logger;

	public UserStorage() {
		logger = Logger.getLogger(BowserCastle.class);
	}

	/**
	 * Deserialize all of our user objects and put them in our arraylist data structure.
	 * @param <T>
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<User> grabUsers(final String filename) throws IOException {
		List<User> users = new ArrayList<User>();
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			if (fis.available() != 0) {//this is a new file, create King Bowser
				ois = new ObjectInputStream(fis);
				users.addAll((ArrayList<User>) ois.readObject()); //store all of our users in our arraylist
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (ois != null) ois.close();
		}

		return users;
	}
	
	/**
	 * Upon exiting the program, serialize our queue of users.
	 * @param <T>
	 * @throws IOException 
	 */
	public void serializeUsers(String filename, List<User> allUsers) throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(allUsers); //serialize our queue of users
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close(); //close our input and output streams
			if (oos != null) oos.close();
		}
	}

	/**
	 * Send a message to the log
	 * @param message to be logged
	 */ 
	public void log(final String message) {
		logger.info(message);
	}
}