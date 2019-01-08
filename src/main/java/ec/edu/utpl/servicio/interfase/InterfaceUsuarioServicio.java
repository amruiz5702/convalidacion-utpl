package ec.edu.utpl.servicio.interfase;

public interface InterfaceUsuarioServicio<T> extends InterfaceServicio<T> {
	
	T buscarPorUsuario(String username);
	
}
