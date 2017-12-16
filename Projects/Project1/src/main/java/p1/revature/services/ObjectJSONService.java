package p1.revature.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The base service class for converting objects to and from JSON strings. 
 * All services should extend this. 
 */
public class ObjectJSONService {
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	/**
	 * Converts service objects into JSON
	 * @param obj : the Object to convert
	 * @return the JSON string representation to send to the client
	 */
	public static String toJSON(Object obj) {
		return gson.toJson(obj);
	}
	
	/**
	 * Converts service objects from JSON
	 * @param json : the String representation of the JSON object received from the client
	 * @param type : the type of object we want back
	 * @return the Java Object representation of {@code json}
	 */
	public static Object fromJSON(String json, Class<Object> type)
	{
		return gson.fromJson(json, type);
	}
	
	/**
	 * convenience method for appending JSON. 
	 * NOTE: if the JSON to append is wrapped in object, that argument is discarded. if it's wrapped in array, this
	 * method will throw IllegalArgumentException
	 * @param json : the JSON to append to (in String form)
	 * @param moreJSON : the JSON to append (also in String form)
	 * @return json (a String representing JSON!)
	 */
	public static String appendTo(String json, String moreJSON) throws IllegalArgumentException
	{
		// trim the JSON strings being passed in
		json     = json.trim();
		moreJSON = moreJSON.trim();
		// for now, if we found '[' at the beginning of moreJSON, we throw IllegalArgumentException
		if (moreJSON.indexOf('[') == 0) throw new IllegalArgumentException(
				"Attempted to append array to JSON without property name");
		// find the last '}' in the string
		int jsonClosingBracket = json.lastIndexOf('}');
		// from there, insert ',', followed by moreJSON
		return new StringBuilder(json).insert(jsonClosingBracket, String.format(",%s", moreJSON)).toString();
	}
}
