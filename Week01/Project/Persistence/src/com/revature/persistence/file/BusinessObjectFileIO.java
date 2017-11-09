package com.revature.persistence.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class BusinessObjectFileIO {
	// Get class logger 
	private static Logger logger = Logger.getLogger(BusinessObjectFileIO.class);
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String fileName;
	
	/**
	 * Opens object read stream and closes Write stream if opened 
	 * @param name file containing serialized objects 
	 */
	public void openReadStream(String name) {
		close();
		
		try {
			FileInputStream stream = new FileInputStream(name);
			ois = new ObjectInputStream(stream);
		} catch (IOException e) {
			
		}
	}
	
	
	/**
	 * Closes both read and write streams 
	 */
	public void close() {
		try {
			// If Write stream opened
			if (oos != null) {
				oos.close();
				oos = null;
				
			}
			
			// If Read Stream opened
			if (ois != null) {
				ois.close();
				ois = null;
			}
		} catch (IOException e) {
			
		}
	}
	
}
