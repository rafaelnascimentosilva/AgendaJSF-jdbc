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

	private String email;
	private String senha;
	private UsuarioDAO dao;

	public String autentica() throws SQLException {
		dao = new UsuarioDAO();
		Usuario usuario = dao.autentica(email, senha);

		if (usuario != null && (email.equals(usuario.getLogin()) && senha.equals(usuario.getSenha()))) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
