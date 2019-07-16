package agenda.connection;

import java.io.Serializable;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@SuppressWarnings("unused")
public class ConnectionFactory implements Serializable {

	private static final long serialVersionUID = 1L;
	static Connection connection = null;
	static {

		InitialContext cxt;
		try {
			cxt = new InitialContext();
			if (cxt == null) {
				throw new Exception("Uh oh -- no context!");
			}

			DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
			connection = ds.getConnection();
			if (ds == null) {
				throw new Exception("Data source not found!");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {

		return connection;
	}
}
