package database.connector.service.databaseconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceImpl implements DatabaseService {
	
	private static final String URL = "jdbc:mysql://localhost:3306/order_management";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private static Connection connection = null;
	

	@Override
	public synchronized Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		
		return connection;
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
