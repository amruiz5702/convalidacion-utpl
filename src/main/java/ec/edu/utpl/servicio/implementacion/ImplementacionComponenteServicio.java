package ec.edu.utpl.servicio.implementacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.utpl.modelo.dao.interfase.InterfaceComponenteDao;
import ec.edu.utpl.modelo.entidad.EntidadComponente;
import ec.edu.utpl.servicio.interfase.InterfaceComponenteServicio;

@Service
public class ImplementacionComponenteServicio implements InterfaceComponenteServicio<EntidadComponente> {

	@Autowired
	InterfaceComponenteDao<EntidadComponente> interfaceComponenteDao;

	@Override
	public void crear(EntidadComponente entidad) {
		interfaceComponenteDao.crear(entidad);
	}

	@Override
	public void editar(EntidadComponente entidad) {
		interfaceComponenteDao.editar(entidad);
	}

	@Override
	public void eliminar(EntidadComponente entidad) {
		entidad.setEstadoEntidad(Boolean.FALSE);
		interfaceComponenteDao.editar(entidad);
	}

	@Override
	public List<EntidadComponente> obtener() {		
		HashMap<String, Object> hshmpPropiedades = new HashMap<>();
		hshmpPropiedades.put("estadoEntidad", Boolean.TRUE);
		return interfaceComponenteDao.obtener(hshmpPropiedades);
	}

	@Override
	public EntidadComponente obtener(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadComponente> obtener(int first, int pageSize, String sortField, SortOrder sortOrder,
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
	public EntidadComponente generarNuevaInstancia() throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
