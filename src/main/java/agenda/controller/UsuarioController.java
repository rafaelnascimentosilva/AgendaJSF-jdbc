package agenda.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private UsuarioDAO usuarioDAO;

	private List<Usuario> usuarioLista;

	private Usuario usuarioSelecionado;

	private List<Contato> contatoLista;

	public UsuarioController() {
	}
	
	@PostConstruct
	public void init() {
		try {
			listaDeUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("N�o foi poss�vel listar os dados", e);
		}
	}

	public void inserir() {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.Inserir(this.usuario);
			this.usuarioLista = new ArrayList<Usuario>();
			this.usuarioLista.add(this.usuario);
			this.usuario = new Usuario();
			Messages.addGlobalInfo("Usu�rio inserido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usu�rio n�o inserido", e);
		}

	}

	public void deletar(Usuario usuario) throws SQLException {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.deletar(usuario.getId());
			this.usuarioLista = new ArrayList<Usuario>();
			this.usuarioLista.remove(usuario);
			Messages.addGlobalInfo("Usu�rio removido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usu�rio n�o pode ser removido", e);
		}
	}

	public void btnDlgEditar(Usuario usuario) {
		this.usuarioSelecionado = new Usuario();
		this.usuarioSelecionado = usuario;
		PrimeFaces current = PrimeFaces.current();
		current.ajax().update("formEditar");
		current.executeScript("PF('dlgEditar').show();");
	}

	public void editar() throws SQLException {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.editar(this.usuarioSelecionado);
			PrimeFaces current = PrimeFaces.current();
			current.ajax().update("formEditar");
			current.executeScript("PF('dlgEditar').hide();");
			Messages.addGlobalInfo("Usu�rio alterado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usu�rio n�o pode ser alterado", e);
		}
	}

	public List<Usuario> listaDeUsuarios() throws SQLException{
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioLista = this.usuarioDAO.getLista();
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("N�o foi poss�vel listar os dados", e);
		}
		return this.usuarioLista;
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
