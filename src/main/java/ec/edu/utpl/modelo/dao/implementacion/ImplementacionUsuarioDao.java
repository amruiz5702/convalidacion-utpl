package ec.edu.utpl.modelo.dao.implementacion;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.utpl.modelo.dao.interfase.InterfaceUsuarioDao;
import ec.edu.utpl.modelo.entidad.EntidadUsuario;

@Repository
@Transactional
public class ImplementacionUsuarioDao extends ImplementacionDao<EntidadUsuario>
		implements InterfaceUsuarioDao<EntidadUsuario> {

	public ImplementacionUsuarioDao() {
		super(EntidadUsuario.class);
	}

	@Override
	public EntidadUsuario buscarPorUsuario(Map<String, Object> propiedades) {
		return this.prepararTransaccion()
				.agregarPredicado(propiedades)
				.obtenerUnicoRegistro();
	}

}
