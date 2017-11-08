package com.revature.persistence.file;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.persistence.Persistenceable;

public class FilePersistence implements Persistenceable {

	@Override
	public List<BusinessObject> select(String name, List<SimpleEntry<String, String>> cnds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String name, List<SimpleEntry<String, String>> cnds, List<SimpleEntry<String, String>> values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(String name, List<SimpleEntry<String, String>> values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String name, List<SimpleEntry<String, String>> cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

}
