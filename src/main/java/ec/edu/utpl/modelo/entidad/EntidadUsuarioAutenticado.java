package ec.edu.utpl.modelo.entidad;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class EntidadUsuarioAutenticado extends User {

	private static final long serialVersionUID = 1L;
	private EntidadUsuario entidadUsuario;

	public EntidadUsuarioAutenticado(EntidadUsuario entidadUsuario,
			Collection<? extends GrantedAuthority> authorities) {
		super(entidadUsuario.getNombreUsuario(),
				PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(entidadUsuario.getClaveUsuario()),
				entidadUsuario.isActivoUsuario(), true, true, true, authorities);
		this.entidadUsuario = entidadUsuario;
	}

	public EntidadUsuario getEntidadUsuario() {
		return entidadUsuario;
	}

}
