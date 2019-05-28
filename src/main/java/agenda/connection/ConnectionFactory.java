package agenda.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() throws SQLException {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:postgresql://localhost/db_agenda_jsf", "postgres","123");
	}
}
