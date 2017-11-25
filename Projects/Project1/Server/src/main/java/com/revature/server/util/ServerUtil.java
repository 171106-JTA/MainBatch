package com.revature.server.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public final class ServerUtil {
	private static Gson gson = new Gson();
	
	/**
	 * Converts object to JSON using Gson Library
	 * @param item - what to convert to json
	 * @return json string representation of item 
	 */
	public static <T> String toJson(T item) {
		Type type = new TypeToken<T>() {}.getType(); 
		return gson.toJson(item, type);
	}
	
	/**
	 * Converts json to java object
	 * @param json - 
	 * @return
	 */
	public static <T> T toJava(String json) {
		Type type = new TypeToken<T>() {}.getType(); 
		return gson.fromJson(json, type);
	}
}
