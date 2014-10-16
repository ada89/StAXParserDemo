package org.parser.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertDataDemo {
	
	public static void main(String args[]) {
		
		Connection connection = null;
		Statement stmt = null;
		
		//for database connection
		String url = "jdbc:mysql://127.0.0.1:3307/test";
		String username = "username";
		String password = "password";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, username, password);
			stmt = connection.createStatement();
			
			try{
				//query to be executed
				String query = "INSERT INTO roomprices(room_id, hotel_id, price, currency, hasBreakfast) " + "VALUES (2, 'H124', 345.89, 'USD', false)";
				stmt.execute(query);
			} catch(Exception e){
				e.printStackTrace();
			}
		
		}
		
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
