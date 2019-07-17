package agenda.model;

import java.io.Serializable;
import java.util.Arrays;

public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private byte[] foto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foto other = (Foto) obj;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
