package com.vrx.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSingleton {

	private static volatile DBSingleton instance = null;
	private static volatile Connection conn = null;
	
	private DBSingleton() {	
		try {
			Class.forName("org.h2.Driver");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(conn != null) {
			throw new RuntimeException("User getConnection() method to create");
		}
		if(instance != null) { // prevent from using reflection on the class
			throw new RuntimeException("Use getInstance() mwethod to create");
		}
	}
	
	public static DBSingleton getInstance() {
		if(instance == null) {  // this will lazily load the instance
			synchronized(DBSingleton.class) { // blocks if another thread has access to it. Thread safe
				if(instance == null) {
					instance = new DBSingleton();
				}
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		if(conn == null) {
			synchronized (DBSingleton.class) {
				if(conn == null) {
					try {
						String dbUrl = "jdbc:h2:~/testdb";
						
						conn = DriverManager.getConnection(dbUrl, "sa", "");
						System.out.println(conn.getMetaData()); 
						return conn;
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return conn;
	}
}