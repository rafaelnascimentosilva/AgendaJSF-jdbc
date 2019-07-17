package agenda.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import agenda.dao.ContatoDAO;
import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Foto;
import agenda.model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{fotoBean}")
	private FotoBean fotoBean;

	private Usuario usuario = new Usuario();
	private Usuario usuarioSelecionado;
	private List<Usuario> usuarioLista;
	private UsuarioDAO usuarioDAO;

	private Contato contato = new Contato();
	private Contato contatoSelecionado = new Contato();
	private List<Contato> contatoLista;
	private ContatoDAO contatoDAO;

	private Foto fotoObj = new Foto();
	private boolean formStatus = true;

	public UsuarioController() {
	}

	@PostConstruct
	public void init() {
		try {
			listaDeUsuarios();

		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Nï¿½o foi possï¿½vel listar os dados", e);
		}
	}

	public void isStatus() {
		formStatus = !formStatus;
		System.out.println(formStatus);
		Ajax.updateAll();
	}

	public void btnDlgNovoUsuario() {
		this.usuario = new Usuario();
		isStatus();
	}

	public void inserir() {
		try {

			this.usuarioDAO = new UsuarioDAO();
			this.usuarioDAO.Inserir(this.usuario);
			Messages.addGlobalInfo("Usuário inserido com sucesso!");
			isStatus();
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Problema ao tentar inseri usuário", e);
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
			Messages.addGlobalWarn("Não foi possivel listar os dados", e);
		}
		return this.usuarioLista;
	}

	/* ao abrir o p:dialogo instanciando o obj contato */
	public void btnDlgNovoContato(Usuario usuario) throws IOException {
		this.usuarioSelecionado = usuario;
		this.contato = new Contato();
		PrimeFaces current = PrimeFaces.current();
		current.ajax().update("formNovoContato");
		current.executeScript("PF('dlgNovoContato').show();");
		fotoBean.clearForm();

	}

	public void btnDlgEditarContato(Contato contato) throws IOException, NumberFormatException, SQLException {

		byte[] image = contatoDAO.ReadImageContato(contato.getId());
		Foto f = new Foto();
		f.setFoto(image);
		contato.setFoto(f);
		this.contatoSelecionado = contato;
		fotoBean.setContatoSelecionado(contato);
		PrimeFaces current = PrimeFaces.current();
		current.ajax().update("formEditarContato");
		current.executeScript("PF('dlgEditarContato').show();");
		fotoBean.clearForm();
	}

	/* apï¿½s preencheer os inputs referentes as propridedades de contato */
	public void novoContato() throws SQLException, ParseException, IOException {
		try {
			this.contatoDAO = new ContatoDAO();
			this.contato.setUsuario(usuarioSelecionado);

			// PEGANDO OBJETO JÁ SETADO NO OUTRO BEAN FotoBean
			contato.setFoto(fotoBean.getContato().getFoto());// ADICINANDO AO objeto CONTATO NO BEAN UsuarioController

			this.contatoDAO.inserir(contato);
			this.contatoLista = new ArrayList<Contato>();
			this.contatoLista.add(contato);
			this.contatoLista = contatoDAO.getListaContato(this.contato.getUsuario().getId());

			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('dlgNovoContato').hide();");
			Messages.addGlobalInfo("Contato inserido com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Problema ao inserir o contato", e);
		}
	}

	public void deleteContato(Contato contato) throws SQLException {
		try {
			contatoDAO = new ContatoDAO();
			contatoDAO.deletar(contato.getId());
			Messages.addGlobalInfo("Contato removido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Problema ao deletar o contato", e);
		}
	}

	public void editarContato() throws IOException {
		try {
			contatoDAO = new ContatoDAO();
			this.contatoSelecionado.setFoto(fotoBean.getContatoSelecionado().getFoto());
			contatoDAO.editar(this.contatoSelecionado);
			PrimeFaces current = PrimeFaces.current();
			current.ajax().update("formEditarContato");
			current.executeScript("PF('dlgEditarContato').hide();");
			Messages.addGlobalInfo("Contato alterado com sucesso!");
			fotoBean.clearForm();
		} catch (SQLException e) {
			e.printStackTrace();
			Messages.addGlobalWarn("Problema ao editar o contato", e);
		}
	}

	public List<Contato> obterContotatosPorUsuario(Usuario usuario) throws SQLException {

		this.contatoDAO = new ContatoDAO();
		this.contatoLista = new ArrayList<Contato>();
		return this.contatoLista = contatoDAO.getListaContato(usuario.getId());
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

	public boolean isFormStatus() {
		return formStatus;
	}

	public void setFormStatus(boolean formStatus) {
		this.formStatus = formStatus;
	}

	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

	public FotoBean getFotoBean() {
		return fotoBean;
	}

	public void setFotoBean(FotoBean fotoBean) {
		this.fotoBean = fotoBean;
	}

	public Foto getFotoObj() {
		return fotoObj;
	}

	public void setFotoObj(Foto fotoObj) {
		this.fotoObj = fotoObj;
	}

}
