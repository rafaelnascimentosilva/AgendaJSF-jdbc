package agenda.controller;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import agenda.dao.UsuarioDAO;
import agenda.model.Usuario;
import agenda.util.SessaoUtil;

@SessionScoped
@ManagedBean
public class AutenticadorController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;
	private UsuarioDAO dao;
	private Usuario usuario;

	public String autentica() throws SQLException {
		dao = new UsuarioDAO();
		usuario = dao.autentica(login, senha);

		if (usuario != null && (login.equals(usuario.getLogin()) && senha.equals(usuario.getSenha()))) {
			usuario = dao.getUsuario(usuario.getId());
			SessaoUtil.setParam("USUARIOLogado", usuario);
			return "/Usuario.xhtml?faces-redirect=true";
		} else {
			System.out.println("autentica erro..");
			Messages.addGlobalWarn("Login ou Senha Inválidos\"!");
			return null;
		}
	}

	public String logout() {
		SessaoUtil.invalidate();
		return "/Login.xhtml?faces-redirect=true";
	}

	public String registraSaida() {
		return "/Login?faces-redirect=true";
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioDAO getDao() {
		return dao;
	}

	public void setDao(UsuarioDAO dao) {
		this.dao = dao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
