
package ec.edu.utpl.exepcion;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ec.edu.utpl.utilitario.UtilitarioMensajeFace;

public class ManejadorExcepcion extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	private static final Logger LOGGER = LogManager.getLogger(ManejadorExcepcion.class);

	@SuppressWarnings("deprecation")
	public ManejadorExcepcion(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {

		Iterator<ExceptionQueuedEvent> eventos = getUnhandledExceptionQueuedEvents().iterator();
		while (eventos.hasNext()) {
			ExceptionQueuedEvent evento = eventos.next();
			ExceptionQueuedEventContext contexto = (ExceptionQueuedEventContext) evento.getSource();

			Throwable excepcion = contexto.getException();

			NegocioExcepcion negocioExcepciones = getNegocioExcepciones(excepcion);

			boolean handled = false;

			try {
				if (excepcion instanceof ViewExpiredException) {
					handled = true;
					LOGGER.error("Error del sistema: " + excepcion.getClass().getName() + excepcion.getMessage(),
							excepcion);
					redireccionar("/error/sessionExpirada.xhtml");
				} else if (negocioExcepciones != null) {
					handled = true;
					LOGGER.error("Error del sistema: " + excepcion.getMessage(), excepcion);
					UtilitarioMensajeFace.agregarMensaje(negocioExcepciones.getMessage(), null,
							FacesMessage.SEVERITY_FATAL);
				} else {
					handled = true;
					LOGGER.error("Error del sistema: " + excepcion.getMessage(), excepcion);
					redireccionar("/error/ocurrioError.xhtml");
				}
			} finally {
				if (handled) {
					eventos.remove();
				}

			}
		}
		getWrapped().handle();
	}

	private NegocioExcepcion getNegocioExcepciones(Throwable excepcion) {
		if (excepcion instanceof NegocioExcepcion) {
			return (NegocioExcepcion) excepcion;
		} else if (excepcion.getCause() != null) {
			return getNegocioExcepciones(excepcion.getCause());
		}
		return null;
	}

	private void redireccionar(String pagina) {
		try {

			FacesContext facesContexto = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContexto.getExternalContext();
			String contextoPath = externalContext.getRequestContextPath();
			externalContext.redirect(contextoPath + pagina);
			facesContexto.responseComplete();

		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}