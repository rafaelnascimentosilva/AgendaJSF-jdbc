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

import agenda.dao.ContatoDAO;
import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private Contato contato = new Contato();

	private UsuarioDAO usuarioDAO;

	private ContatoDAO contatoDAO;

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
			Messages.addGlobalWarn("Não foi possível listar os dados", e);
		}
	}

	public void btnDlgNovoUsuario() {
		this.usuario = new Usuario();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgInserir').show();");
	}

	public void inserir() {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.Inserir(this.usuario);
			this.usuarioLista.add(this.usuario);
			listaDeUsuarios();
			Messages.addGlobalInfo("Usuário inserido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usuário não inserido", e);
		}

	}

	public void deletar(Usuario usuario) throws SQLException {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.deletar(usuario.getId());
			this.usuarioLista.remove(usuario);
			Messages.addGlobalInfo("Usuário removido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usuário não pode ser removido", e);
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
			Messages.addGlobalInfo("Usuário alterado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Usuário não pode ser alterado", e);
		}
	}

	public List<Usuario> listaDeUsuarios() throws SQLException {
		try {
			this.usuarioDAO = new UsuarioDAO();
			this.usuarioLista = this.usuarioDAO.getLista();
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Não foi possível listar os dados", e);
		}
		return this.usuarioLista;
	}

	/* ao abrir o p:dialogo instanciando o obj contato */
	public void btnDlgNovoContato(Usuario usuario) {
		this.usuarioSelecionado = usuario;
		this.contato = new Contato();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgNovoContato').show();");
	}

	/* após preencheer os inputs referentes as propridedades de contato */
	public void novoContato() throws SQLException {
		try {
			this.contatoDAO = new ContatoDAO();
			this.contato.setUsuario(usuarioSelecionado);
			this.contatoDAO.inserir(contato);
			this.contatoLista = new ArrayList<Contato>();
			this.contatoLista.add(contato);
			this.contatoLista = contatoDAO.getListaContato(contato.getUsuario().getId());

			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('dlgNovoContato').hide();");
			Messages.addGlobalInfo("Contato inserido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Não foi possível inserir o contato", e);
		}
	}

	public List<Contato> obterContotatosPorUsuario() throws SQLException {

		this.contatoDAO = new ContatoDAO();
		this.contatoLista = new ArrayList<Contato>();
		return this.contatoLista = contatoDAO.getListaContato(this.usuarioSelecionado.getId());
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

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public ContatoDAO getContatoDAO() {
		return contatoDAO;
	}

	public void setContatoDAO(ContatoDAO contatoDAO) {
		this.contatoDAO = contatoDAO;
	}

}
