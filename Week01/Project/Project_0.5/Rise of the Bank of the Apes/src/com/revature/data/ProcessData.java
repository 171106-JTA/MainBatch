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
	
	public static void serialize(HashMap<String, User> users) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream("user.ser"));
			oos.writeObject(users);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, User> unserialize() {
		HashMap<String, User> hm = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream("user.ser"));
			hm = (HashMap) ois.readObject();
			System.out.println(hm.keySet());
			
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
