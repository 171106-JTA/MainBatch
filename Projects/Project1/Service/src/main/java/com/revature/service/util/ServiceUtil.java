package com.revature.service.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revature.businessobject.BusinessObject;

public final class ServiceUtil {
	private static final String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static Gson gson = new Gson();
	
	/**
	 * Converts object to JSON using Gson Library (Class must implement BusinessObject)
	 * @param item - what to convert to json
	 * @return json string representation of item 
	 */
	public static <T extends BusinessObject> String toJson(T item) {
		Type type = new TypeToken<T>() {}.getType(); 
		return gson.toJson(item, type);
	}
	
	/**
	 * Converts json to java object (Class must implement BusinessObject)
	 * @param json - what to transform to java
	 * @return Java representation of json
	 */
	public static <T extends BusinessObject> T toJava(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}
	
	
	/**
	 * Converts object to JSON using Gson Library (Class must implement BusinessObject)
	 * @param item - what to convert to json
	 * @return json string representation of item 
	 */
	public static <T extends BusinessObject> String toJson(List<T> items) {
		Type type = new TypeToken<List<T>>() {}.getType(); 
		return gson.toJson(items, type);
	}
	
	/**
	 * Converts json to java object (Class must implement BusinessObject)
	 * @param json - what to transform to java
	 * @return Java representation of json
	 */
	public static <T extends BusinessObject> List<T> toJavaArray(String json, Class<List<T>> clazz) {
		return gson.fromJson(json, clazz);
	}
	
	public static boolean validateEmail(String email) {
		return email != null && email.matches(regexEmail);
	}
	
	
	
	
}
