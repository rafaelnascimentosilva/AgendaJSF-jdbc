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

	public void Inserir(Usuario usuario) throws SQLException {
		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "INSERT INTO ag_usuario(nome, fone) VALUES (?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getFone());
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
	}

	public List<Usuario> getLista() throws SQLException {
		
		try {
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			this.connection = new ConnectionFactory().getConnection();
			String sql = "SELECT * FROM ag_usuario";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(new Integer(resultSet.getInt("id_usuario")));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setFone(resultSet.getString("fone"));
				listaUsuario.add(usuario);
			}
			return listaUsuario;

		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
		
	}

	public void deletar(int id) throws SQLException {
		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "DELETE FROM ag_usuario WHERE id_usuario=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
	}

	public void editar(Usuario usuario) throws SQLException {
		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "UPDATE ag_usuario SET  nome=?, fone=? WHERE id_usuario=?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getFone());
			statement.setInt(3, usuario.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
	}

}
