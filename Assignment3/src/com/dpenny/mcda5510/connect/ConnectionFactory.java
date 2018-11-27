package com.dpenny.mcda5510.connect;

import java.sql.Connection;

public class ConnectionFactory {
	private static Connection single_instance = null;
	public ConnectionFactory(String conType) {
		createConnection(conType);
	}
	// use getShape method to get object of type shape
	private final Connection createConnection(String connectionType) {
		if (connectionType == null) {
			return null;
		}
		if (connectionType.equalsIgnoreCase("mySQLJDBC")) {
			if (single_instance == null) {
				MySQLJDBCConnection dbConnection = new MySQLJDBCConnection();
				single_instance = dbConnection.setupConnection();
			}
			return single_instance;

		} else if (connectionType.equalsIgnoreCase("CSSMUCA")) {
			

		}

		return null;
	}
	
	public Connection getConnection() {
		return single_instance;
	}

}