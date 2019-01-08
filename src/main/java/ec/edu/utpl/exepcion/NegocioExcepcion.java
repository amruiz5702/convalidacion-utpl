package ec.edu.utpl.exepcion;

public class NegocioExcepcion extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegocioExcepcion(String mensaje){
		super(mensaje);
	}
	
}