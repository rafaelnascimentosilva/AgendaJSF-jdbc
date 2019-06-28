package agenda.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import agenda.connection.ConnectionFactory;
import agenda.model.Contato;

public class ContatoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public void inserir(Contato contato) throws SQLException, ParseException {

		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "INSERT INTO ag_contato(id_usuario, nome, fone, email, dt_nasc) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, contato.getUsuario().getId());
			statement.setString(2, contato.getNome());
			statement.setString(3, contato.getFone());
			statement.setString(4, contato.getEmail());
			statement.setDate(5, new java.sql.Date(contato.getDtNasc().getTime()));
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}

	}

	public void editar(Contato contato) throws SQLException {

		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "UPDATE ag_contato SET  nome=?, fone=?, email=?,dt_nasc=? WHERE id_contato=?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setString(3, contato.getEmail());
			statement.setDate(4, new java.sql.Date(contato.getDtNasc().getTime()));
			statement.setInt(5, contato.getId());

			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
	}

	public void deletar(int id) throws SQLException {

		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "delete from ag_contato where id_contato =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}

	}

	public List<Contato> getListaContato(int id) throws SQLException {

		try {
			this.connection = new ConnectionFactory().getConnection();
			List<Contato> lista = new ArrayList<Contato>();
			String sql = "select * from ag_contato where id_usuario =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Contato contato = new Contato();
				contato.setId(new Integer(resultSet.getInt("id_contato")));
				contato.setNome(resultSet.getString("nome"));
				contato.setFone(resultSet.getString("fone"));
				contato.setEmail(resultSet.getString("email"));
				contato.setDtNasc(resultSet.getDate(("dt_nasc")));
				lista.add(contato);
			}

			return lista;
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			this.connection.close();
		}
	}
}
