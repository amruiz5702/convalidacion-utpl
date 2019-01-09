package ec.edu.utpl.controlador.beans;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class Controlador<T> {

	Class<T> clazz;
	T instanciaEntidad;

	public enum AccionPersistencia {
		CREAR, ELIMINAR, EDITAR
	}

	@SuppressWarnings("unchecked")
	public Controlador() {
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public T getInstanciaEntidad() {
		return instanciaEntidad;
	}

	public void setInstanciaEntidad(T instanciaEntidad) {
		this.instanciaEntidad = instanciaEntidad;
	}

	protected void redireccionar(String pagina) {
		try {

			FacesContext facesContexto = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContexto.getExternalContext();
			externalContext.redirect(pagina);
			facesContexto.responseComplete();

		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}
