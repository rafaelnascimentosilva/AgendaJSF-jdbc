package agenda.controller;

import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import agenda.dao.UsuarioDAO;
import agenda.util.ReportUtil;

@ManagedBean(name = "report")
@ViewScoped
public class ReportController {

	private StreamedContent arquivoRetorno;

	UsuarioController usuarioController = new UsuarioController();

	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		ReportUtil reportUtil = new ReportUtil();
		HashMap<String, Object> parametrosRelatorio = new HashMap<String, Object>();
		String id = context.getExternalContext().getRequestParameterMap().get("usuario_id");
		int id_usuario_logado = Integer.parseInt(id);
		try {

			this.arquivoRetorno = reportUtil.geraRelatorio(parametrosRelatorio,
					usuarioController.obterContatosPorUsuario(new UsuarioDAO().getUsuario(id_usuario_logado)));

		} catch (Exception e) {
			context.addMessage("não foi possivel imprimir", new FacesMessage(e.getMessage()));
			return null;
		}
		return this.arquivoRetorno;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}
}