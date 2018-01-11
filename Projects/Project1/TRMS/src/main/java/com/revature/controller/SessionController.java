package com.revature.controller;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.revature.model.Constants;
import com.revature.model.dao.*;
import com.revature.util.Dao;
import com.revature.util.JDBC;

// Controller functions pertaining to sessions and 
// front-controller app initialization
public class SessionController {

	public static int[] login(String user, String pass)
			throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		List<String> list = new ArrayList<String>();
		Map<String, String> map = new EmployeeDao(user, pass).fetchCreds(false);
		if (map == null || map.isEmpty())
			return new int[] { Constants.AuthFail };

		for (String s : map.values()) {
			list.add(s);
		}

		return new int[] { Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)) };
	}

	public static int register(String user, String pass, String superId, String type)
			throws NumberFormatException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, SQLException {

		Integer id = new EmployeeDao(user, pass, superId, type).insertGetId(false);
		if (id != null)
			return id;
		return Constants.AuthFail;
	}

	public static void startJDBC() {
		// invoke a simple worker thread to start jdbc
		new JDBCStarterThread(Thread.currentThread().getId()).start();
	}

	private static class JDBCStarterThread extends Thread {
		public static long id;

		public JDBCStarterThread(long id) {
			JDBCStarterThread.id = id;
		}

		@Override
		public void run() {
			JDBC.getInstance();
			
		}
	}

	public static String getRequest(String empId) {
		return null;
	}

	public static String getRequests(String... emps) {
		Collection<Map<String, String>> maps;
		Map<String, String> map1;
		String json1, json2;
		Gson gson1, gson2, gson3, gson4;
		Map<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
		Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		maps = new RequestDao().fetchAllRequests(true, emps);
		List<String> jsons = new LinkedList<String>();
		List<String> arr = new LinkedList<String>();
		
		// generate keys
		if (maps == null || maps.isEmpty())
			return null;
		map1 = maps.iterator().next();
		if(map1 == null || map1.isEmpty())
			return null;
		keys.put("keys", map1.keySet());
		gson1 = new Gson();
		json1 = gson1.toJson(keys);
		
		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson2 = new Gson();
			for(String s : map.keySet()) {
				list.add(map.get(s));			
			}

			values.put("values", list);
			json2 = gson2.toJson(values);
			list.clear();
			jsons.add(json1);
			jsons.add(json2);
			values.clear();
			gson3 = new Gson();
			arr.add(gson3.toJson(jsons));
			jsons.clear();
		}
		
		gson4 = new Gson();
		

		return gson4.toJson(arr);
	}

	public static String getEmployees() {
		Collection<Map<String, String>> maps;
		Map<String, String> map1;
		String json1, json2;
		Gson gson1, gson2, gson3, gson4;
		Map<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
		Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		List<String> jsons = new LinkedList<String>();
		List<String> arr = new LinkedList<String>();		
		maps = new EmployeeDao().fetchAll(true);
		
		// generate keys
		if (maps == null || maps.isEmpty())
			return null;
		map1 = maps.iterator().next();
		if(map1 == null || map1.isEmpty())
			return null;
		keys.put("keys", map1.keySet());
		gson1 = new Gson();
		json1 = gson1.toJson(keys);
		
		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson2 = new Gson();
			for(String s : map.keySet()) {
				list.add(map.get(s));			
			}

			values.put("values", list);
			json2 = gson2.toJson(values);
			list.clear();
			jsons.add(json1);
			jsons.add(json2);
			values.clear();
			gson3 = new Gson();
			arr.add(gson3.toJson(jsons));
			jsons.clear();
		}
		
		gson4 = new Gson();
		

		return gson4.toJson(arr);
	}

	public static String getMessages(String mailId) {
		Collection<Map<String, String>> maps;
		Map<String, String> map1;
		String json1, json2;
		Gson gson1, gson2, gson3, gson4;
		Map<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
		Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		List<String> jsons = new LinkedList<String>();
		List<String> arr = new LinkedList<String>();		
		maps = new EmployeeDao().fetchMessages(mailId, true);
		// generate keys
		if (maps == null || maps.isEmpty())
			return null;
		map1 = maps.iterator().next();
		if(map1 == null || map1.isEmpty())
			return null;
		keys.put("keys", map1.keySet());
		gson1 = new Gson();
		json1 = gson1.toJson(keys);
		
		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson2 = new Gson();
			for(String s : map.keySet()) {
				list.add(map.get(s));			
			}

			values.put("values", list);
			json2 = gson2.toJson(values);
			list.clear();
			jsons.add(json1);
			jsons.add(json2);
			values.clear();
			gson3 = new Gson();
			arr.add(gson3.toJson(jsons));
			jsons.clear();
		}
		
		gson4 = new Gson();
		
		return gson4.toJson(arr);	}

	public static List<String> updateEmployee(String emp) {
		return null;
	}

	public static List<String> processRequest(String req) {
		return null;
	}

	public static String getEventsForEmp(String emp) {
		Collection<Map<String, String>> maps;
		String json;
		Gson gson1, gson2;
		List<String> jsons = new LinkedList<String>();
		maps = new EventDao().fetchEventsByEmp(true, emp);
		if (maps == null || maps.isEmpty())
			return null;

		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson1 = new Gson();

			jsons.add(gson1.toJson(map));
		}

		gson2 = new Gson();
		json = gson2.toJson(jsons);

		return json;
	}

	public static String getGradingCriteria(String crit) {
		Collection<Map<String, String>> maps;
		String json;
		Gson gson1, gson2;
		List<String> jsons = new LinkedList<String>();
		maps = new EventDao().fetchGradingCriteria(false, crit);
		if (maps == null || maps.isEmpty())
			return null;

		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson1 = new Gson();
			jsons.add(gson1.toJson(map));
		}

		gson2 = new Gson();
		json = gson2.toJson(jsons);

		return json;
	}

	public static String getFileAttachments(String emp) {
		Collection<Map<String, String>> maps;
		Gson gson1, gson2;
		String json;
		List<String> jsons = new LinkedList<String>();
		maps = new EventDao().fetchEventsByEmp(false, emp);
		if (maps == null || maps.isEmpty())
			return null;

		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson1 = new Gson();
			jsons.add(gson1.toJson(map));
		}

		gson2 = new Gson();
		json = gson2.toJson(jsons);

		return json;
	}

	// Stream blob to screen (somehow)
	public static String getFileContents(String file) {
		Collection<Map<String, String>> maps;
		Gson gson1, gson2;
		List<String> jsons = new LinkedList<String>();
		maps = new EventDao().fetchEventsByEmp(false, file);
		if (maps == null || maps.isEmpty())
			return null;

		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson1 = new Gson();
			jsons.add(gson1.toJson(map));
		}

		return null;
	}

	public static String validateSession(boolean valid) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Gson gson = new Gson();
		if (!valid)
			return null;
		map.put("valid", valid);
		return gson.toJson(map);
	}

	public static String getEvents() {
		Collection<Map<String, String>> maps;
		Map<String, String> map1;
		String json1, json2;
		Gson gson1, gson2, gson3, gson4;
		Map<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
		Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		List<String> jsons = new LinkedList<String>();
		List<String> arr = new LinkedList<String>();	
		
		maps = new EventDao().fetchAll(true);
		
		// generate keys
		if (maps == null || maps.isEmpty())
			return null;
		map1 = maps.iterator().next();
		if(map1 == null || map1.isEmpty())
			return null;
		keys.put("keys", map1.keySet());
		gson1 = new Gson();
		json1 = gson1.toJson(keys);
		
		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson2 = new Gson();
			for(String s : map.keySet()) {
				list.add(map.get(s));			
			}

			values.put("values", list);
			json2 = gson2.toJson(values);
			list.clear();
			jsons.add(json1);
			jsons.add(json2);
			values.clear();
			gson3 = new Gson();
			arr.add(gson3.toJson(jsons));
			jsons.clear();
		}
		
		gson4 = new Gson();
		
		return gson4.toJson(arr);
	}

	public static String getEmployees(String emp) {

		Collection<Map<String, String>> maps;
		Map<String, String> map1;
		String json1, json2;
		Gson gson1, gson2, gson3, gson4;
		Map<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
		Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
		List<String> list = new LinkedList<String>();
		List<String> jsons = new LinkedList<String>();
		List<String> arr = new LinkedList<String>();	
		
		maps = EmployeeDao.fetchSubordinates(true, emp);
		
		// generate keys
		if (maps == null || maps.isEmpty())
			return null;
		map1 = maps.iterator().next();
		if(map1 == null || map1.isEmpty())
			return null;
		keys.put("keys", map1.keySet());
		gson1 = new Gson();
		json1 = gson1.toJson(keys);
		
		for (Map<String, String> map : maps) {
			if (map == null || map.isEmpty())
				return null;
			gson2 = new Gson();
			for(String s : map.keySet()) {
				list.add(map.get(s));			
			}

			values.put("values", list);
			json2 = gson2.toJson(values);
			list.clear();
			jsons.add(json1);
			jsons.add(json2);
			values.clear();
			gson3 = new Gson();
			arr.add(gson3.toJson(jsons));
			jsons.clear();
		}
		
		gson4 = new Gson();
		
		return gson4.toJson(arr);
	}
}
