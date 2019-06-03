package agenda.connection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory implements Serializable{

	private static final long serialVersionUID = 1L;

	public Connection getConnection() throws SQLException {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:postgresql://localhost/db_agenda_jsf_jdbc", "postgres","1234");
	}
}
