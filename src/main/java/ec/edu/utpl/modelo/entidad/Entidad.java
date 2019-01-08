package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class Entidad implements Serializable {

	private static final long serialVersionUID = 1L;
	@Type(type = "true_false")
	@Column(name = "estado_entidad", nullable = false, columnDefinition = "ENUM('T','F') DEFAULT 'T'", insertable = false)
	private Boolean estadoEntidad;

	public Boolean getEstadoEntidad() {
		return estadoEntidad;
	}

	public void setEstadoEntidad(Boolean estadoEntidad) {
		this.estadoEntidad = estadoEntidad;
	}

}
