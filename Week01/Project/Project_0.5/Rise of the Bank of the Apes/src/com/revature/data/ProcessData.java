package com.revature.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.revature.users.User;

public class ProcessData {
	
	public static ObjectOutputStream oos;
	public static ObjectInputStream ois;
	
	/**
	 * Serializes the users HashMap for later use. Writes to a file called user.ser.
	 * Utilizes ObjectOutputStream() and FileOutputStream() to achieved encryption.
	 * 
	 * @param users HashMap<String, User> of all bank customers and admins
	 */
	public static void serialize(HashMap<String, User> users, String fileName) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(users);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Decrypts the user.ser files and restores it back into a HashMap of users.
	 * Utilizes ObjectInputStream() and FileInputStream() to complete decryption.
	 * 
	 * @return HashMap<String, User> of users
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, User> unserialize(String fileName) {
		HashMap<String, User> hm = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			hm = (HashMap<String, User>) ois.readObject();
			
		}catch(IOException | ClassNotFoundException e) {
			return new HashMap<String, User>();
		}finally{
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return hm;
	}
}
