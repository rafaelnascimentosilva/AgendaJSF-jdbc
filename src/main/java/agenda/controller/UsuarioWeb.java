package agenda.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import agenda.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioWeb implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public void loga(Usuario usuario) {
		this.usuario = usuario;
	}

	public void desloga() {
		this.usuario = null;
	}

	public boolean isLogado() {
		return this.usuario != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
