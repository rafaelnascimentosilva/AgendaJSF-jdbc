package agenda.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import agenda.util.SessaoUtil;
import org.omnifaces.util.Messages;
import org.omnifaces.util.Messages.Message;

@SessionScoped
@ManagedBean
public class AutenticadorController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

	public String autentica() {
		System.out.println("autentica..");

		if (email.equals("admin") && senha.equals("admin")) {
			System.out.println("Confirmou  usuario e senha ...");

			// ADD USUARIO NA SESSION

			Object b = new Object();

			SessaoUtil.setParam("USUARIOLogado", b);
			System.out.println(b);

			return "/Usuario.xhtml?faces-redirect=true";

		} else {
			Messages.addGlobalInfo("Login ou Senha Inválidos\"!");
			return "/Login.xhtml?faces-redirect=true";

			
		}

	}
	
	public String logout() {

		Object b = new Object();

		SessaoUtil.setParam("", b);
		System.out.println("2"+b);
		return "/Login.xhtml?faces-redirect=true";
	}

	/**
	 * M�todo que efetua o logout
	 * 
	 * @return
	 */
	public String registraSaida() {

		// REMOVER USUARIO DA SESSION

		return "/Login?faces-redirect=true";
	}

	// GETTERS E SETTERS

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

}
