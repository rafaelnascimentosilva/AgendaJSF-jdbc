package agenda.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import agenda.connection.ConnectionFactory;
import agenda.model.Contato;

public class ContatoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public void inserir(Contato contato) throws SQLException {

		try {
			this.connection = new ConnectionFactory().getConnection();
			String sql = "INSERT INTO ag_contato(nome, fone, fk_usuario) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setInt(3, contato.getUsuario().getId());
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
			String sql = "UPDATE ag_contato SET  nome=?, fone=?, fk_usuario=? WHERE id_contato=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setInt(3, contato.getUsuario().getId());
			statement.setInt(4, contato.getId());
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
			String sql = "delete from ag_contato where id =?";
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
			String sql = "select * from ag_contato";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet =  statement.executeQuery();
			while (resultSet.next()) {
				Contato contato = new Contato();
				contato.setId(new Integer(resultSet.getInt("id_contato")));
				contato.setNome(resultSet.getString("nome"));
				contato.setFone(resultSet.getString("fone"));			
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
