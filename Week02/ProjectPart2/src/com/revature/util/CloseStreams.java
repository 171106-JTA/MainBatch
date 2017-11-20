package com.revature.util;

public class CloseStreams {
	
	/**
	 * closes resources safely.
	 * @param resource : The resource to close.
	 * NOTE: To use this method, you should import static com.revature.util.CloseStreams.close at the start of the Java file.
	 */
	public static void close(AutoCloseable resource)
	{

		if (resource != null)
		{
			try 
			{
				resource.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
