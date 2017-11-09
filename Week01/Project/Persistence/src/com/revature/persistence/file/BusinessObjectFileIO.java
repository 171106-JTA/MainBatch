package com.revature.persistence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;

public class BusinessObjectFileIO {
	// Get class logger 
	private static Logger logger = Logger.getLogger(BusinessObjectFileIO.class);
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String fileName;
	
	/**
	 * @return true is stream is open else false
	 */
	public boolean isReadStreamOpen() {
		return ois != null;
	}
	
	/**
	 * @return true is stream is open else false
	 */
	public boolean isWriteStreamOpen() {
		return oos != null;
	}
	
	/**
	 * Opens object read stream and closes Write stream if opened 
	 * @param name file containing serialized objects 
	 */
	public void openReadStream(String name) {
		// Ensure no stream is opened 
		close();
		
		try {
			// Open read stream
			FileInputStream stream = new FileInputStream(fileName = name);
			ois = new ObjectInputStream(stream);
			
			logger.debug("Opened object read stream for file:>" + fileName);
		} catch (IOException e) {
			logger.debug("Opened object read stream for file:>" + fileName);
		}
	}
	
	/**
	 * Opens object write stream and closes read stream if opened 
	 * @param name file used to store serialized objects 
	 */
	public void openWriteStream(String name) {
		// Ensure no stream is opened 
		close();
		
		try {
			// Open read stream
			FileOutputStream stream = new FileOutputStream(fileName = name);
			oos = new ObjectOutputStream(stream);
			
			logger.debug("Opened object write stream for file:>" + fileName);
		} catch (IOException e) {
			logger.error("Failed to opened object write stream for file:>" + fileName + " message:>" + e.getMessage());
		}
	}
	
	/**
	 * Attempts to read BusinessObject from file
	 * @return instance of BusinessObject if successful else null
	 */
	public BusinessObject read() {
		BusinessObject result = null;
		
		try {
			// Attempt file read 
			result = ois != null ? (BusinessObject)ois.readObject() : null;
			logger.debug("fread data from file:>" + fileName);
		} catch (ClassNotFoundException e) {
			logger.warn("failed to read data from file:>" + fileName + " message:>" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn("failed to read data from file:>" + fileName + " message:>" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Attempts to write BusinessObject to file
	 */
	public void write(BusinessObject object) {
		try {
			// Attempt file read 
			if (oos != null) {
			   oos.writeObject(object);
			   logger.debug("wrote data to file:>" + fileName);
			} else {
				throw new IOException("Attempted to write object to a uninitalized stream");
			}
		} catch (IOException e) {
			logger.warn("failed to write data to file:>" + fileName + " message:>" + e.getMessage());
			e.printStackTrace();
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
				logger.debug("Closed object read stream for file:>" + fileName);
			}
			
			// If Read Stream opened
			if (ois != null) {
				ois.close();
				ois = null;
				logger.debug("Closed object write stream for file:>" + fileName);
			}
		} catch (IOException e) {
			logger.error("Failed to closed object stream for file:>" + fileName + " message:>" + e.getMessage());
		}
	}
	
	/**
	 * Deletes file at specified path
	 * @param path where file is located
	 * @return true if file was deleted else false
	 */
	public boolean deleteFile(String path) {
		File file = new File(path);
		logger.debug("removing file:>" + path);
		return file.delete();
	}
	
	/**
	 * Ensure streams are closed
	 */
	@Override
	protected void finalize() {
		close();
	}
}
