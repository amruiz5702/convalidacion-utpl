package ec.edu.utpl.controlador.seguridad;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ec.edu.utpl.spring.scope.SpringScopeView;
import ec.edu.utpl.utilitario.UtilitarioMensajeFace;

@Named
@SpringScopeView
public class ControladorAutenticacion implements Serializable {
	private static final long serialVersionUID = 1L;

	public void evtPreRender() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request.getParameter("incorrecto") != null && request.getParameter("incorrecto").equals("true")) {
			UtilitarioMensajeFace.agregarMensaje("usuarioIncorrecto", null, FacesMessage.SEVERITY_ERROR);
		}
	}

	
	public void cmmdBtnAuntenticar(ActionEvent ae) throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
	}
}
