package com.dpenny.mcda5510.connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLJDBCConnection implements DBConnection {

	public Connection setupConnection() {

		Connection connection = null;
		try {
			// This will load the MySQL driver, each DB has its own driver
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Setup the connection with the DB

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment2?"

					+ "user=root&password=Johnty@2016" + "&useSSL=false"
					+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); // timezone
			
			
			/*connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/p_bandyopadhyay?" + "user=p_bandyopadhyay&password=A00430847" + "&useSSL=false"
							+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");*/

		} catch (Exception e) {
			System.out.println("problem setting connection");
			e.printStackTrace();
		} finally {

		}
		return connection;
	}

}