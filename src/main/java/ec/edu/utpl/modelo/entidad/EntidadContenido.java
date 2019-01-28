package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_contenido")
public class EntidadContenido extends Entidad implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contenido")
	private Integer idContenido;

	@Column(name = "descripcion_contenido", nullable = false, columnDefinition = "text")
	private String descripcionContenido;

	public Integer getIdContenido() {
		return idContenido;
	}

	public void setIdContenido(Integer idContenido) {
		this.idContenido = idContenido;
	}

	public String getDescripcionContenido() {
		return descripcionContenido;
	}

	public void setDescripcionContenido(String descripcionContenido) {
		this.descripcionContenido = descripcionContenido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idContenido == null) ? 0 : idContenido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadContenido other = (EntidadContenido) obj;
		if (idContenido == null) {
			if (other.idContenido != null)
				return false;
		} else if (!idContenido.equals(other.idContenido))
			return false;
		return true;
	}

}
