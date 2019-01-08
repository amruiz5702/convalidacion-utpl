package ec.edu.utpl.servicio.implementacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.edu.utpl.modelo.entidad.EntidadRol;
import ec.edu.utpl.modelo.entidad.EntidadUsuario;
import ec.edu.utpl.modelo.entidad.EntidadUsuarioAutenticado;
import ec.edu.utpl.servicio.interfase.InterfaceUsuarioServicio;

@Service("implementacionDetalleUsuarioServicio")
public class ImplementacionDetalleUsuarioServicio implements UserDetailsService {

	@Autowired
	InterfaceUsuarioServicio<EntidadUsuario> usuarioServicio;
	static final PasswordEncoder codificacionClave = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) {
		EntidadUsuario entidadUsuario = usuarioServicio.buscarPorUsuario(username);
		EntidadUsuarioAutenticado usuarioAutenticado = null;
		if (entidadUsuario != null) {
			usuarioAutenticado = new EntidadUsuarioAutenticado(entidadUsuario, getRoles(entidadUsuario));
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return usuarioAutenticado;
	}

	private Collection<? extends GrantedAuthority> getRoles(EntidadUsuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (EntidadRol rol : usuario.getLstRoles()) {
			authorities.add(new SimpleGrantedAuthority(
					new StringBuilder().append("ROLE_").append(rol.getNombreRol().toUpperCase()).toString()));
		}
		return authorities;
	}

}
