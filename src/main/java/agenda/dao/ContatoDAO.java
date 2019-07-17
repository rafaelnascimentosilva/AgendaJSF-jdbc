package agenda.dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import agenda.connection.ConnectionFactory;
import agenda.model.Contato;

public class ContatoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public ContatoDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void inserir(Contato contato) throws SQLException, ParseException, IOException {

		try {

			String sql = "INSERT INTO ag_contato(id_usuario, nome, fone, email, dt_nasc) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, contato.getUsuario().getId());
			statement.setString(2, contato.getNome());
			statement.setString(3, contato.getFone());
			statement.setString(4, contato.getEmail());
			statement.setDate(5, new java.sql.Date(contato.getDtNasc().getTime()));
			statement.execute();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					contato.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			String sql2 = "INSERT INTO ag_contato_foto(id_foto,foto) VALUES (?,?)";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setInt(1, contato.getId());
			statement2.setBytes(2, contato.getFoto().getFoto());

			statement2.execute();

			statement2.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	public void editar(Contato contato) throws SQLException {

		try {

			String sql = "UPDATE ag_contato SET  nome=?, fone=?, email=?,dt_nasc=? WHERE id_contato=?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, contato.getNome());
			statement.setString(2, contato.getFone());
			statement.setString(3, contato.getEmail());
			statement.setDate(4, new java.sql.Date(contato.getDtNasc().getTime()));
			statement.setInt(5, contato.getId());

			String sql2 = "UPDATE ag_contato_foto SET foto=? WHERE id_foto=?";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setBytes(1, contato.getFoto().getFoto());
			statement2.setInt(2, contato.getId());

			statement2.execute();
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public void deletar(int id) throws SQLException {

		try {

			String sql = "delete from ag_contato where id_contato =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			String sql2 = "DELETE FROM ag_contato_foto WHERE id_foto=?";
			PreparedStatement statement2 = connection.prepareStatement(sql2);

			statement2.setInt(1, id);

			statement2.execute();

			statement.execute();
			statement.close();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	public List<Contato> getListaContato(int id) throws SQLException {

		try {

			List<Contato> lista = new ArrayList<Contato>();
			String sql = "select * from ag_contato where id_usuario =? ORDER BY id_contato DESC";
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
		}
	}

	public byte[] getFotoContato(int id) throws SQLException {
		byte[] imgBytes = null;
		try {
			PreparedStatement ps = this.connection.prepareStatement("SELECT foto FROM ag_contato_foto where id_foto=?");
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
