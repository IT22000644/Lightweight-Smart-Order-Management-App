package database.connector.service.databaseconnector;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseService {
	Connection getConnection() throws SQLException;
	void closeConnection();
}
