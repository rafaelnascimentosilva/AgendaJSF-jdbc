package agenda.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import agenda.connection.ConnectionFactory;
import agenda.model.Foto;

public class FotoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public FotoDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void inserir(Foto foto) throws SQLException {
		String sql = "INSERT INTO ag_usuario_foto(id_foto_usuario,foto) VALUES (?,?)";
		PreparedStatement statement2 = this.connection.prepareStatement(sql);
		statement2.setInt(1, foto.getId());
		statement2.setBytes(2, foto.getFoto());
		statement2.execute();
		statement2.close();
	}

	public Foto getFoto(int id) throws SQLException {
		String sql = "SELECT foto FROM ag_usuario_foto WHERE id_foto_usuario=?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		Foto foto = new Foto();
		if (rs.next()) {
			foto.setId(rs.getInt("id_foto_usuario"));
			foto.setFoto(rs.getBytes("foto"));
		}
		return foto;
	}
}
