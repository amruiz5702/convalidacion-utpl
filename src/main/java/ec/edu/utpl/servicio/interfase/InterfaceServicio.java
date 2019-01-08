package ec.edu.utpl.servicio.interfase;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

public interface InterfaceServicio<T> {

	public void crear(T entidad);

	public void editar(T entidad);

	public void eliminar(T entidad);

	public List<T> obtener();	
	
	public T obtener(Integer id);

	public List<T> obtener(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

	public int contar(Map<String, Object> filters);

	public T generarNuevaInstancia() throws InstantiationException, IllegalAccessException;
}
