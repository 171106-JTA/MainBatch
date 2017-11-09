package com.revature.persistence.file;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.persistence.Persistenceable;

/**
 * 
 * @author Antony Lulciuc
 *
 */
public class FilePersistence implements Persistenceable {
	// Where to save files generated by class 
	private String directory;
	
	/**
	 * If used, then current working directory is used on 
	 * instantiation
	 */
	public FilePersistence() {
		super();
		this.directory = System.getProperty("user.dir");
	}
	
	/**
	 * Designates where data is stored in system
	 * @param directory - path to folder to store application data
	 */
	public FilePersistence(String directory) {
		super();
		this.directory = directory;
	}
	
	@Override
	public Resultset select(String name, FieldParams cnds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String name, FieldParams cnds, FieldParams values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(String name, FieldParams values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String name, FieldParams cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return path where system files are stored
	 */
	public String getDirectory() {
		return directory;
	}
}
