package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_detalle_convalidacion")
public class EntidadDetalleConvalidacion extends Entidad implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle_convalidacion")
	private Integer idDetalleConvalidacion;

	@Column(name = "materia_aprobada_convalidacion", nullable = false, columnDefinition = "text")
	private String materiaAprobada;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_componente", foreignKey = @ForeignKey(name = "FK_COMPONENTE_DETALLE_CONVALIDACION"), nullable = true)
	private EntidadComponente componente;

	@Column(name = "umbral_detalle_convalidacion", nullable = false, length = 3)
	private Double umbralDetalleConvalidacion = 0.0;

	@Column(name = "descripcion_detalle_convalidacion", nullable = false, columnDefinition = "text")
	private String descripcionDetalleConvalidacion;

	public Integer getIdDetalleConvalidacion() {
		return idDetalleConvalidacion;
	}

	public void setIdDetalleConvalidacion(Integer idDetalleConvalidacion) {
		this.idDetalleConvalidacion = idDetalleConvalidacion;
	}

	public String getMateriaAprobada() {
		return materiaAprobada;
	}

	public void setMateriaAprobada(String materiaAprobada) {
		this.materiaAprobada = materiaAprobada;
	}

	public EntidadComponente getComponente() {
		return componente;
	}

	public void setComponente(EntidadComponente componente) {
		this.componente = componente;
	}

	public Double getUmbralDetalleConvalidacion() {
		return umbralDetalleConvalidacion;
	}

	public void setUmbralConvalidacion(Double umbralConvalidacion) {
		this.umbralDetalleConvalidacion = umbralConvalidacion;
	}

	public String getDescripcionDetalleConvalidacion() {
		return descripcionDetalleConvalidacion;
	}

	public void setDescripcionDetalleConvalidacion(String descripcionDetalleConvalidacion) {
		this.descripcionDetalleConvalidacion = descripcionDetalleConvalidacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleConvalidacion == null) ? 0 : idDetalleConvalidacion.hashCode());
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
		EntidadDetalleConvalidacion other = (EntidadDetalleConvalidacion) obj;
		if (idDetalleConvalidacion == null) {
			if (other.idDetalleConvalidacion != null)
				return false;
		} else if (!idDetalleConvalidacion.equals(other.idDetalleConvalidacion))
			return false;
		return true;
	}

}
