package com.vrx.singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSingletonDemo {

	public static void main(String[] args) {
		DBSingleton instance = DBSingleton.getInstance();
		System.out.println(instance);
		
		long timeBefore = 0;
		long timeAfter = 0;
		
		timeBefore = System.currentTimeMillis();
		Connection conn = instance.getConnection();
		timeAfter = System.currentTimeMillis();
		
		System.out.println(timeAfter - timeBefore);
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			int count = stmt.executeUpdate("Create table ADDRESS1 (id int, street_name varchar(20)); ");
			System.out.println("Table created");
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		timeBefore = System.currentTimeMillis();
		Connection conn1 = instance.getConnection(); //optimised - so does not takes more time when calling on second time
		timeAfter = System.currentTimeMillis();
		
		System.out.println(timeAfter - timeBefore);
	}

}
