package ec.edu.utpl.modelo.dao.interfase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

public interface InterfaceDao<T> {

	public void crear(T entidad);

	public void editar(T entidad);

	public T obtener(Integer id);

	public T obtener(Map<String, Object> propiedades);
	
	public List<T> obtener(HashMap<String, Object> propiedades);

	public List<T> obtener(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

	public int contar(Map<String, Object> propiedades);
}
