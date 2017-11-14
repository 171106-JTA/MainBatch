package com.peterson.bowsercastle_model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * A class used for logging messages and serialization.
 * @author Alex
 */
public class UserStorage {
	private static final String USERS_TXT = "Users.txt"; //text file with user data
	private Logger logger;

	public UserStorage() {
		logger = Logger.getLogger(BowserCastle.class);
	}

	/**
	 * Deserialize all of our user objects and put them in our arraylist data structure.
	 * @param <T>
	 * @throws IOException 
	 */
	public Queue<User> grabUsers() throws IOException {
		return grabUsers(USERS_TXT);
	}

	/**
	 * Upon exiting the program, serialize our queue of users.
	 * @param <T>
	 * @throws IOException 
	 */
	public void serializeUsers(final Queue<User> q1) throws IOException {
		serializeUsers(USERS_TXT, q1);
	}

	public void serializeUsers(String filename, Queue<User> q1) throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(q1); //serialize our queue of users
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close(); //close our input and output streams
			if (oos != null) oos.close();
		}

	}

	public Queue<User> grabUsers(String filename) throws IOException {
		final Queue<User> users = new PriorityQueue<User>(new UserComparator());

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			if (fis.available() != 0) {//this is a new file, create King Bowser
				ois = new ObjectInputStream(fis);
				users.addAll((PriorityQueue<User>) ois.readObject()); //store all of our users in our arraylist
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
	 * Send a message to the log
	 * @param message to be logged
	 */ 
	public void log(final String message) {
		logger.info(message);
	}
}