package com.revature.service.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revature.businessobject.BusinessObject;

public final class ServiceUtil {
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
	public static <T extends BusinessObject> T toJava(String json) {
		Type type = new TypeToken<T>() {}.getType(); 
		return gson.fromJson(json, type);
	}
}
