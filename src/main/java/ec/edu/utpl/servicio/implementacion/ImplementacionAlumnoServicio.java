package ec.edu.utpl.servicio.implementacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.utpl.exepcion.NegocioExcepcion;
import ec.edu.utpl.modelo.dao.interfase.InterfaceAlumnoDao;
import ec.edu.utpl.modelo.entidad.EntidadAlumno;
import ec.edu.utpl.servicio.interfase.InterfaceAlumnoServicio;

@Service
public class ImplementacionAlumnoServicio implements InterfaceAlumnoServicio<EntidadAlumno> {

	@Autowired
	InterfaceAlumnoDao<EntidadAlumno> interfaceAlumnoDao;

	@Override
	public void crear(EntidadAlumno entidad) {
		if (!validarAlumnoExistente(entidad))
			interfaceAlumnoDao.crear(entidad);
		else {
			throw new NegocioExcepcion("alumnoDuplicado");
		}
	}

	@Override
	public void editar(EntidadAlumno entidad) {
		if (!validarAlumnoExistente(entidad))
			interfaceAlumnoDao.editar(entidad);
		else {
			throw new NegocioExcepcion("alumnoDuplicado");
		}
	}

	@Override
	public void eliminar(EntidadAlumno entidad) {
		entidad.setEstadoEntidad(Boolean.FALSE);
		interfaceAlumnoDao.editar(entidad);
	}

	@Override
	public List<EntidadAlumno> obtener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadAlumno obtener(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadAlumno> obtener(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int contar(Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntidadAlumno generarNuevaInstancia() throws InstantiationException, IllegalAccessException {
		EntidadAlumno nuevaInstancia = null;
		nuevaInstancia = EntidadAlumno.class.newInstance();
		return nuevaInstancia;
	}

	@Override
	public List<EntidadAlumno> buscarPorPropiedades(EntidadAlumno instanciaAlumno) {
		List<EntidadAlumno> lstAlumno;
		Map<String, Object> mpPropiedadBusqueda = obtenerPropiedadesBusqueda(instanciaAlumno);
		if (!mpPropiedadBusqueda.isEmpty()) {
			mpPropiedadBusqueda.put("estadoEntidad", Boolean.TRUE);
			lstAlumno = interfaceAlumnoDao.buscarPorPropiedades(mpPropiedadBusqueda);
		} else {
			throw new NegocioExcepcion("sinParametroBusqueda");
		}

		return lstAlumno;
	}

	private boolean validarAlumnoExistente(EntidadAlumno instanciaAlumno) {
		Map<String, Object> mpPropiedadBusqueda = new HashMap<String, Object>();
		mpPropiedadBusqueda.put("identificacionAlumno", instanciaAlumno.getIdentificacionAlumno());
		EntidadAlumno instanciaAlumnoTmp = interfaceAlumnoDao.obtener(mpPropiedadBusqueda);

		return (Objects.isNull(instanciaAlumnoTmp) || instanciaAlumnoTmp.equals(instanciaAlumno)) ? Boolean.FALSE
				: Boolean.TRUE;
	}

	private Map<String, Object> obtenerPropiedadesBusqueda(EntidadAlumno instanciaAlumno) {
		Map<String, Object> mpPropiedadBusqueda = new HashMap<String, Object>();
		if (StringUtils.isNoneBlank(instanciaAlumno.getIdentificacionAlumno())) {
			mpPropiedadBusqueda.put("identificacionAlumno", instanciaAlumno.getIdentificacionAlumno());
		}
		if (StringUtils.isNoneBlank(instanciaAlumno.getNombreAlumno())) {
			mpPropiedadBusqueda.put("nombreAlumno", instanciaAlumno.getNombreAlumno());
		}
		if (StringUtils.isNoneBlank(instanciaAlumno.getApellidoAlumno())) {
			mpPropiedadBusqueda.put("apellidoAlumno", instanciaAlumno.getApellidoAlumno());
		}
		return mpPropiedadBusqueda;
	}

}
