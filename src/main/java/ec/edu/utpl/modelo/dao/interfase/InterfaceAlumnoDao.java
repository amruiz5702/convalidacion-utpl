package ec.edu.utpl.modelo.dao.interfase;

import java.util.List;
import java.util.Map;

public interface InterfaceAlumnoDao<EntidadUsuario> extends InterfaceDao<EntidadUsuario> {

	List<EntidadUsuario> buscarPorPropiedades(Map<String, Object> propiedades);

}
