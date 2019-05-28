package agenda.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import agenda.connection.ConnectionFactory;
import agenda.model.Usuario;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public UsuarioDAO(Connection connection) throws SQLException {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void Inserir(Usuario usuario) throws SQLException {
		try {
			String sql = "INSERT INTO ag_usuario(nome, fone) VALUES (?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getFone());
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> getLista() {
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		try {

			String sql = "SELECT * FROM ag_usuario";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();				
				usuario.setNome(resultSet.getString("nome"));
				usuario.setFone(resultSet.getString("fone"));
				listaUsuario.add(usuario);
			}

		} catch (Exception e) {
		}
		return listaUsuario;
	}
	
	public void deletar (int id) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
