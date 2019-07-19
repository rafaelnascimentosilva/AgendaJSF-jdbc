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

	public UsuarioDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	@SuppressWarnings("deprecation")
	public void Inserir(Usuario usuario) throws SQLException {
		try {

			String sql = "INSERT INTO ag_usuario(nome, dt_nasc, sexo, fone, email, login, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setString(1, usuario.getNome());
			statement.setDate(2, new java.sql.Date(usuario.getDataNasc().getDate()));
			statement.setString(3, usuario.getSexo());
			statement.setString(4, usuario.getFone());
			statement.setString(5, usuario.getEmail());
			statement.setString(6, usuario.getLogin());
			statement.setString(7, usuario.getSenha());
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public List<Usuario> getLista() throws SQLException {

		try {
			List<Usuario> listaUsuario = new ArrayList<Usuario>();

			String sql = "SELECT * FROM ag_usuario";

			PreparedStatement statement = this.connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(new Integer(resultSet.getInt("id_usuario")));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setFone(resultSet.getString("fone"));
				usuario.setDataNasc((resultSet.getDate("dt_nasc")));
				usuario.setEmail(resultSet.getString("email"));
				listaUsuario.add(usuario);
			}
			return listaUsuario;

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public void deletar(int id) throws SQLException {
		try {

			String sql = "DELETE FROM ag_usuario WHERE id_usuario=?";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public void editar(Usuario usuario) throws SQLException {
		try {

			String sql = "UPDATE ag_usuario SET  nome=?, fone=? WHERE id_usuario=?";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getFone());
			statement.setInt(3, usuario.getId());

			String sql2 = "UPDATE ag_usuario_foto SET foto=? WHERE id_foto_usuario=?";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setBytes(1, usuario.getFoto().getFoto());
			statement2.setInt(2, usuario.getId());

			statement2.execute();
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public Usuario getUsuario(int id) throws SQLException {
		try {
			String sql = "SELECT * FROM ag_usuario WHERE id_usuario=?";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setInt(1, id);
			Usuario usuario = new Usuario();
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				usuario.setId(new Integer(resultSet.getInt("id_usuario")));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setFone(resultSet.getString("fone"));
				usuario.setDataNasc(resultSet.getDate("dt_nasc"));
				usuario.setEmail(resultSet.getString("email"));
				usuario.setLogin(resultSet.getString("login"));
				usuario.setSenha(resultSet.getString("senha"));
			}
			return usuario;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

// migrar o banco posteriromente
	public Usuario autentica(String login, String senha) throws SQLException {
		try {

			String sql = "select * from ag_usuario where login=? and senha=?";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			statement.setString(1, login);
			statement.setString(2, senha);

			Usuario usuario = new Usuario();
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				Integer id = rs.getInt("id_usuario");
				String nome = rs.getString("nome");
				String uLogin = rs.getString("login");
				String uSenha = rs.getString("senha");

				usuario.setId(id);
				usuario.setNome(nome);
				usuario.setLogin(uLogin);
				usuario.setSenha(uSenha);

			}
			rs.close();
			statement.close();
			return usuario;

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	public byte[] getFotoUsuario(int id) throws SQLException {
		byte[] imgBytes = null;
		try {
			PreparedStatement ps = this.connection
					.prepareStatement("SELECT foto FROM ag_usuario_foto where id_foto_usuario=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					imgBytes = rs.getBytes(1);
				}
				rs.close();
			}
			ps.close();
		} catch (SQLException e) {
			throw new SQLException();
		}
		return imgBytes;
	}
}
