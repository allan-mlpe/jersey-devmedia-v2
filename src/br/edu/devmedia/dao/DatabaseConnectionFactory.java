package br.edu.devmedia.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {
	
	private static String host;
	private static String database;
	private static String user;
	private static String password;
	private static String port;
	
	static {
		Properties prop = new Properties();
		try {
			InputStream is = DatabaseConnectionFactory.class.getResourceAsStream("database.properties");
			prop.load(is);
			
			host = prop.getProperty("host");
			database = prop.getProperty("database");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			port = prop.getProperty("port");
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://" + host + ":" + port + "/" + database, 
				user, 
				password);
		
		return con;
	}

}
