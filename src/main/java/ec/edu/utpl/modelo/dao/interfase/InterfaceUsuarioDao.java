package ec.edu.utpl.modelo.dao.interfase;

import java.util.Map;

public interface InterfaceUsuarioDao<T> extends InterfaceDao<T> {

	T buscarPorUsuario(Map<String, Object> propiedades);

}
