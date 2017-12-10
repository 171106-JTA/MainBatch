package revature.trms.connectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorUtil {
	
	private static Properties prop;
	private final static String FILE_NAME = "DB.properties";
	
	
	public static Connection getConnected(){
		/*
		File file = new File("");
		System.out.println(file.getAbsolutePath());*/
		
		try{
			prop = new Properties();
			prop.load(new FileInputStream(FILE_NAME));
			Class.forName(prop.getProperty("className"));
			return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
		}catch(SQLException | ClassNotFoundException | IOException e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static void close(CallableStatement cs){
		if(cs!=null){
		try{
			cs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
	public static void close(PreparedStatement cs){
		if(cs!=null){
		try{
			cs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
	public static void close(ResultSet cs){
		if(cs!=null){
		try{
			cs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
}
