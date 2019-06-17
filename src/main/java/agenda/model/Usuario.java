package agenda.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String nome;

	private String fone;
	
	private String login;
	
	private String senha;
	
	private List<Contato> contatoLista;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public List<Contato> getContatoLista() {
		return contatoLista;
	}

	public void setContatoLista(List<Contato> contatoLista) {
		this.contatoLista = contatoLista;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
