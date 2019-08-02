package agenda.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import agenda.dao.ContatoDAO;
import agenda.dao.FotoDAO;
import agenda.dao.UsuarioDAO;
import agenda.model.Contato;
import agenda.model.Foto;
import agenda.model.Usuario;

@ManagedBean(name = "fotoBean")
@SessionScoped
public class FotoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Contato contato = new Contato();
	@ManagedProperty(value = "#{sessao}")
	private AutenticadorController sessao;

	private Contato contatoSelecionado;

	private List<Contato> contatos = new ArrayList<Contato>();

	private ContatoDAO dao = new ContatoDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private FotoDAO fotoDAO = new FotoDAO();

	private Usuario usuario = new Usuario();

	Foto foto = new Foto();

	byte[] image;
	byte[] fotoUsuario;

	private UploadedFile file;

	private UploadedFile fileSelecionado;

	public FotoController() throws SQLException, IOException {
		image = IOUtils.toByteArray(FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/images/user_null.png"));
		foto.setFoto(image);
		contato.setFoto(foto);
		usuario.setFoto(foto);

	}

	public void handlePhotoUpload(FileUploadEvent event) throws SQLException, IOException {
		file = event.getFile();
		byte[] nova = IOUtils.toByteArray(event.getFile().getInputstream());
		contato = new Contato();
		Foto f = new Foto();
		f.setFoto(nova);
		contato.setFoto(f);

	}

	public void uploadFotoUsuario(FileUploadEvent event) throws SQLException, IOException {
		file = event.getFile();
		byte[] nova = IOUtils.toByteArray(event.getFile().getInputstream());
		usuario = new Usuario();
		Foto f = new Foto();
		f.setFoto(nova);
		usuario.setFoto(f);

	}

	public void fotoUploadEditar(FileUploadEvent event) throws SQLException, IOException {
		fileSelecionado = event.getFile();
		byte[] novaFoto = IOUtils.toByteArray(event.getFile().getInputstream());
		contatoSelecionado = new Contato();
		Foto f = new Foto();
		f.setFoto(novaFoto);
		contatoSelecionado.setFoto(f);
	}

	public void clearForm() throws IOException {
		contato = new Contato();
		image = IOUtils.toByteArray(FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/images/user_null.png"));
		Foto f = new Foto();
		f.setFoto(image);
		contato.setFoto(f);
		contato = null;
		file = null;

	}

	public Foto fotoDefault() throws IOException {

		image = IOUtils.toByteArray(FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/images/user_null.png"));
		Foto f = new Foto();
		f.setFoto(image);

		return f;
	}

	public StreamedContent getStreamedFotos() throws NumberFormatException, SQLException {

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {

			if (this.contatos != null) {
				String id = context.getExternalContext().getRequestParameterMap().get("foto_id");

				byte[] image = dao.getFotoContato(Integer.parseInt(id));
				System.out.println("ID DA REQUISICAO" + id);
				System.out.println("ID: " + id + " Imagem em bytes: " + image);
				return new DefaultStreamedContent(new ByteArrayInputStream(image));

			} else {
				return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/resources/images/user_null.png"));
			}
		}
	}

	public StreamedContent getStreamedFotoById() throws NumberFormatException, SQLException {

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			if (contatoSelecionado != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(contatoSelecionado.getFoto().getFoto()));
			} else {
				return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/resources/images/user_avatar.png"));
			}
		}
	}

	public StreamedContent getStreamedFotoUsuario() throws SQLException, IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			if (usuario != null) {
				byte[] fotow;
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				Usuario usuario = (Usuario) sessionMap.get("USUARIOLogado");
				System.out.println("usuario id" + usuario.getId());

				fotow = usuarioDAO.getFotoUsuario(usuario.getId());
				if (fotow != null) {
					return new DefaultStreamedContent(new ByteArrayInputStream(fotow));
				} else {
					/**
					 * melhorar
					 */
					// * o usuario só cadastro foto quando estiver logado no sistema
					// código responsável por salvar uma imagem padrão pro usuário
					Foto f = new Foto();
					byte[] foto = IOUtils.toByteArray(FacesContext.getCurrentInstance().getExternalContext()
							.getResourceAsStream("/resources/images/user_avatar.png"));
					f.setId(usuario.getId());
					f.setFoto(foto);
					fotoDAO.inserir(f);
					return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
							.getResourceAsStream("/resources/images/user_avatar.png"));
				}

			} else {
				return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/resources/images/user_null.png"));
			}
		}

	}

	public StreamedContent getStreamedFotoU() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			if (this.usuario != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(this.usuario.getFoto().getFoto()));
			} else {
				return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/resources/images/user_null.png"));
			}
		}
	}

	public StreamedContent getStreamedFoto() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			if (this.contato != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(this.contato.getFoto().getFoto()));
			} else {
				return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/resources/images/user_null.png"));
			}
		}
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFileSelecionado() {
		return fileSelecionado;
	}

	public void setFileSelecionado(UploadedFile fileSelecionado) {
		this.fileSelecionado = fileSelecionado;
	}

	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AutenticadorController getSessao() {
		return sessao;
	}

	public void setSessao(AutenticadorController sessao) {
		this.sessao = sessao;
	}

}
