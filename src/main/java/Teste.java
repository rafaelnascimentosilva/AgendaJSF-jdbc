import java.sql.Connection;
import java.sql.SQLException;

import agenda.connection.ConnectionFactory;
import agenda.dao.UsuarioDAO;
import agenda.model.Usuario;

public class Teste {
	public static void main(String[] args) throws SQLException {
		
		Connection connection = 	new ConnectionFactory().getConnection();

		UsuarioDAO dao = new UsuarioDAO(connection);

		for (Usuario u : dao.getLista()) {
				System.out.println(u.getNome() +" - "+u.getFone());
		}

	}
}