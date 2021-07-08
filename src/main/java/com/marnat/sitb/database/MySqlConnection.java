package com.marnat.sitb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
	public static Connection createConnection() throws SQLException, ClassNotFoundException{  
		Connection con = DriverManager.getConnection("jdbc:mysql://174.138.23.68:3306/tweet_list","tweet_user","Ad1428min_");
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		return con;  
	}  
}
