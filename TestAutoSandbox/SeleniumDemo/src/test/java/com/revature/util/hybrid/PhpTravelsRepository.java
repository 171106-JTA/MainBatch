package com.revature.util.hybrid;

import java.io.*;
import java.util.Properties;

public class PhpTravelsRepository {

	private static Properties props = new Properties();
	
	public static Properties getRepo(String dir) {
		try(FileInputStream fis = new FileInputStream(
				new File(System.getProperty("user.dir") + "/src/test/resources/"+dir+"/repository.properties"))){
			props.load(fis);
			return props;
		} catch(IOException ex) {
			return null;
		}
	}
}
