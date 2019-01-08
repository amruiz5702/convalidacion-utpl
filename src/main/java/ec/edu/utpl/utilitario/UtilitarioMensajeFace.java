package ec.edu.utpl.utilitario;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilitarioMensajeFace {

	private static final Logger LOGGER = LogManager.getLogger(UtilitarioMensajeFace.class);
	private static String TITULO_MENSAJE;
	private static String DETALLE_MENSAJE;

	public static void agregarMensaje(String strClaveMensaje, Object[] argumentos, FacesMessage.Severity serverity) {
		try {
			if (serverity == FacesMessage.SEVERITY_INFO) {
				TITULO_MENSAJE = UtilitarioMensajeBundle.getValor("TituloInformacion", argumentos);
			} else if (serverity == FacesMessage.SEVERITY_WARN) {
				TITULO_MENSAJE = UtilitarioMensajeBundle.getValor("TituloAdvertencia", argumentos);
			} else if (serverity == FacesMessage.SEVERITY_ERROR) {
				TITULO_MENSAJE = UtilitarioMensajeBundle.getValor("TituloError", argumentos);
				FacesContext.getCurrentInstance().validationFailed();
			} else if (serverity == FacesMessage.SEVERITY_FATAL) {
				TITULO_MENSAJE = UtilitarioMensajeBundle.getValor("TituloFatal", argumentos);
				FacesContext.getCurrentInstance().validationFailed();
			}
			DETALLE_MENSAJE = UtilitarioMensajeBundle.getValor(strClaveMensaje, argumentos);
			FacesMessage facesMensaje = new FacesMessage(serverity, TITULO_MENSAJE, DETALLE_MENSAJE);
			FacesContext.getCurrentInstance().addMessage(null, facesMensaje);
		} catch (Exception exception) {	
			LOGGER.fatal(exception.getMessage(), exception);
		}
	}
}
