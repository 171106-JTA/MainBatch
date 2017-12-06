package p1.revature.services;

import com.google.gson.Gson;

/**
 * The base service class for converting objects to and from JSON strings. 
 * All services should extend this. 
 */
public class ObjectJSONService {
	
	/**
	 * Converts service objects into JSON
	 * @param obj : the Object to convert
	 * @return the JSON string representation to send to the client
	 */
	public static String toJSON(Object obj) {
		return new Gson().toJson(obj);
	}
	
	/**
	 * Converts service objects from JSON
	 * @param json : the String representation of the JSON object received from the client
	 * @param type : the type of object we want back
	 * @return the Java Object representation of {@code json}
	 */
	public static Object fromJSON(String json, Class<Object> type)
	{
		return new Gson().fromJson(json, type);
	}
}
