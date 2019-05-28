package agenda.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Usuario;


@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Connection connection;
	
	private Usuario usuario = new Usuario();
	
	private UsuarioDAO usuarioDAO;
	
	private List<Usuario> usuarioLista ;
	
	private Usuario usuarioSelecionado;
	
	private List<Contato> contatoLista;	
	
	public UsuarioController() {}
	
	public UsuarioController(Connection connection) throws SQLException {
			this.connection = connection;
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
	
	public void deletar(Usuario usuario) throws SQLException {
		this.usuarioDAO = new UsuarioDAO(this.connection);
		this.usuarioDAO.deletar(usuario.getId());
		
		this.usuarioLista = new ArrayList<Usuario>();		
		this.usuarioLista.remove(usuario);		
	}
	
	
	public void btnDlgEditar(Usuario usuario) {
		this.usuarioSelecionado = new Usuario();
		this.usuarioSelecionado  = usuario;
		PrimeFaces current = PrimeFaces.current();
		current.ajax().update("formEditar");
		current.executeScript("PF('dlgEditar').show();");
	}
	
	public void editar() throws SQLException {
		this.usuarioDAO = new UsuarioDAO(this.connection);
		
		this.usuarioDAO.editar(this.usuarioSelecionado);
		
		PrimeFaces current = PrimeFaces.current();
		current.ajax().update("formEditar");
		current.executeScript("PF('dlgEditar').hide();");
		
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

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
