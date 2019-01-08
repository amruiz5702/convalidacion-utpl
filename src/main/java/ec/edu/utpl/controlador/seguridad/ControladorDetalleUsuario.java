package ec.edu.utpl.controlador.seguridad;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Named;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

import ec.edu.utpl.modelo.entidad.EntidadUsuarioAutenticado;

@Named
@SessionScope
public class ControladorDetalleUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	public EntidadUsuarioAutenticado getUsuarioLogeado() {
		EntidadUsuarioAutenticado entidadUsuarioLogeado = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			entidadUsuarioLogeado = (EntidadUsuarioAutenticado) auth.getPrincipal();
		}
		return entidadUsuarioLogeado;
	}

	public String getNombreUsuarioAutenticado() {
		String nombresUsuario = null;
		if (Objects.nonNull(getUsuarioLogeado())) {
			if (Objects.nonNull(getUsuarioLogeado().getEntidadUsuario().getDocenteUsuario())) {
				nombresUsuario = getUsuarioLogeado().getEntidadUsuario().getDocenteUsuario()
						.getNombreCompletoDocente();
			} else {
				nombresUsuario = getUsuarioLogeado().getUsername();
			}
		}
		return nombresUsuario;
	}

	public Boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}

}
