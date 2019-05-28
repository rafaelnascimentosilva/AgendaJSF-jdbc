package agenda.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Usuario;


@ManagedBean
@ViewScoped
public class UsuarioController {
	
	private Connection connection;
	
	private Usuario usuario = new Usuario();
	
	private UsuarioDAO usuarioDAO;
	
	private List<Usuario> usuarioLista ;
	
	private List<Contato> contatoLista;
	
	public UsuarioController() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioController(Connection connection) throws SQLException {
			this.connection = connection;
	}
	
	@PostConstruct
	public void init() throws SQLException  {
		
	}
	
	public void inserir() throws SQLException {
		
		this.usuarioDAO = new UsuarioDAO(this.connection);
		this.usuarioDAO.Inserir(this.usuario);
		
		
		
		this.usuarioLista = new ArrayList<Usuario>();
		
		this.usuarioLista.add(this.usuario);
		
		this.usuario = new Usuario();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso", "Adicionado"));
	}
	
	public List<Usuario> listaDeUsuarios() throws SQLException{
		this.usuarioDAO = new UsuarioDAO(this.connection);
		this.usuarioLista=	this.usuarioDAO.getLista();
		return this.usuarioLista;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public List<Usuario> getUsuarioLista() {
		return usuarioLista;
	}

	public void setUsuarioLista(List<Usuario> usuarioLista) {
		this.usuarioLista = usuarioLista;
	}

	public List<Contato> getContatoLista() {
		return contatoLista;
	}

	public void setContatoLista(List<Contato> contatoLista) {
		this.contatoLista = contatoLista;
	}
}
