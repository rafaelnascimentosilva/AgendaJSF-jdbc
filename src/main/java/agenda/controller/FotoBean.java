package agenda.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import agenda.dao.ContatoDAO;
import agenda.model.Contato;
import agenda.model.Foto;
import agenda.model.Usuario;

@ManagedBean
@SessionScoped
public class FotoBean {
	private Contato contato = new Contato();
	private Usuario usuario = new Usuario();

	private Contato contatoSelecionado;

	private List<Contato> contatos;

	byte[] image;

	private ContatoDAO dao;

	private UploadedFile file;

	private UploadedFile fileSelecionado;

	public FotoBean() throws SQLException, IOException {
		contatos = new ArrayList<Contato>();
		dao = new ContatoDAO();
		contatos = dao.getListaContato(usuario.getId());
		contato = new Contato();
		Foto foto = new Foto();
		image = IOUtils.toByteArray(FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/images/user_null.png"));
		foto.setFoto(image);
		contato.setFoto(foto);

	}

	public void handlePhotoUpload(FileUploadEvent event) throws SQLException, IOException {
		file = event.getFile();
		byte[] nova = IOUtils.toByteArray(event.getFile().getInputstream());
		contato = new Contato();
		Foto f = new Foto();
		f.setFoto(nova);
		contato.setFoto(f);

	}

	public void fotoUploadEditar(FileUploadEvent event) throws SQLException, IOException {
		fileSelecionado = event.getFile();
		byte[] novaFoto = IOUtils.toByteArray(event.getFile().getInputstream());
		contatoSelecionado = new Contato();
		Foto f = new Foto();
		f.setFoto(novaFoto);
		contatoSelecionado.setFoto(f);
	}

	public void uploadFoto() throws IOException, SQLException {
		if (this.contato != null && file != null) {
			this.contato.getFoto().setFoto(IOUtils.toByteArray(file.getInputstream()));
		}
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

	public StreamedContent getStreamedFotos() throws NumberFormatException, SQLException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {

			if (this.contatos != null) {
				String id = context.getExternalContext().getRequestParameterMap().get("foto_id");

				byte[] image = dao.ReadImageContato(Integer.parseInt(id));
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

}
