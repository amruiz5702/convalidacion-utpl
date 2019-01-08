package ec.edu.utpl.servicio.implementacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.utpl.modelo.dao.interfase.InterfaceUsuarioDao;
import ec.edu.utpl.modelo.entidad.EntidadUsuario;
import ec.edu.utpl.servicio.interfase.InterfaceUsuarioServicio;

@Service
public class ImplementacionUsuarioServicio implements InterfaceUsuarioServicio<EntidadUsuario> {

	@Autowired
	InterfaceUsuarioDao<EntidadUsuario> interfaceUsuarioDao;

	public void crear(EntidadUsuario entity) {
		interfaceUsuarioDao.crear(entity);
	}

	public void editar(EntidadUsuario entity) {
		interfaceUsuarioDao.editar(entity);
	}

	public void eliminar(EntidadUsuario entity) {
		entity.setEstadoEntidad(Boolean.FALSE);
		interfaceUsuarioDao.editar(entity);
	}

	public EntidadUsuario obtener(Integer id) {
		return (EntidadUsuario) interfaceUsuarioDao.obtener(id);
	}

	public List<EntidadUsuario> obtener() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	public EntidadUsuario obtener(Map<String, Object> propiedades) {
		return interfaceUsuarioDao.obtener(propiedades);
	}

	public List<EntidadUsuario> obtener(HashMap<String, Object> propiedades) {
		return interfaceUsuarioDao.obtener(propiedades);
	}

	public List<EntidadUsuario> obtener(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		return interfaceUsuarioDao.obtener(first, pageSize, sortField, sortOrder, filters);
	}

	public int contar(Map<String, Object> filters) {
		return interfaceUsuarioDao.contar(filters);
	}

	public EntidadUsuario buscarPorUsuario(String username) {
		Map<String, Object> propiedades = new HashMap<String, Object>();
		propiedades.put("nombreUsuario", username);
		propiedades.put("activoUsuario", Boolean.TRUE);
		propiedades.put("estadoEntidad", Boolean.TRUE);
		return interfaceUsuarioDao.buscarPorUsuario(propiedades);
	}

	@Override
	public EntidadUsuario generarNuevaInstancia() throws InstantiationException, IllegalAccessException {
		return null;
	}

}
