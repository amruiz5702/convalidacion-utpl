package ec.edu.utpl.modelo.dao.implementacion;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.utpl.modelo.dao.interfase.InterfaceAlumnoDao;
import ec.edu.utpl.modelo.entidad.EntidadAlumno;

@Repository
@Transactional
public class ImplementacionAlumnoDao extends ImplementacionDao<EntidadAlumno>
		implements InterfaceAlumnoDao<EntidadAlumno> {

	public ImplementacionAlumnoDao() {
		super(EntidadAlumno.class);
	}

	@Override
	public List<EntidadAlumno> buscarPorPropiedades(Map<String, Object> propiedades) {
		return this
				.prepararTransaccion()
				.agregarPredicado(propiedades)
				.obtenerListaRegistro();
	}

}
