package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_convalidacion")
public class EntidadConvalidacion extends Entidad implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_convalidacion")
	private Integer idConvalidacion;

	@Column(name = "fecha_registro_convalidacion", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistroConvalidacion;

	@Column(name = "medida_similitud_convalidacion", nullable = false, length = 80)
	private String medidaSimilitudConvalidacion;

	@Column(name = "porcentaje_umbral_convalidacion", nullable = false, columnDefinition = "text")
	private Integer porcentajeUmbralConvalidacion = 50;

	@Column(name = "ruta_archivo_excel_convalidacion", nullable = false, length = 80)
	private String rutaArchivoExcelConvalidacion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_alumno", foreignKey = @ForeignKey(name = "FK_ALUMNO_CONVALIDACION"), nullable = false)
	private EntidadAlumno entidadAlumno;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_convalidacion", foreignKey = @ForeignKey(name = "FK_DETALLE_CONVALIDACION"), nullable = false)
	private List<EntidadDetalleConvalidacion> lstDetalleConvalidacion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_docente", foreignKey = @ForeignKey(name = "FK_DOCENTE_CONVALIDACION"), nullable = false)
	private EntidadDocente entidadDocente;

	public EntidadConvalidacion() {
		entidadAlumno = new EntidadAlumno();
	}

	public Integer getIdConvalidacion() {
		return idConvalidacion;
	}

	public void setIdConvalidacion(Integer idConvalidacion) {
		this.idConvalidacion = idConvalidacion;
	}

	public Date getFechaRegistroConvalidacion() {
		return fechaRegistroConvalidacion;
	}

	public void setFechaRegistroConvalidacion(Date fechaRegistroConvalidacion) {
		this.fechaRegistroConvalidacion = fechaRegistroConvalidacion;
	}

	public String getMedidaSimilitudConvalidacion() {
		return medidaSimilitudConvalidacion;
	}

	public void setMedidaSimilitudConvalidacion(String medidaSimilitudConvalidacion) {
		this.medidaSimilitudConvalidacion = medidaSimilitudConvalidacion;
	}

	public Integer getPorcentajeUmbralConvalidacion() {
		return porcentajeUmbralConvalidacion;
	}

	public void setPorcentajeUmbralConvalidacion(Integer porcentajeUmbralConvalidacion) {
		this.porcentajeUmbralConvalidacion = porcentajeUmbralConvalidacion;
	}

	public String getRutaArchivoExcelConvalidacion() {
		return rutaArchivoExcelConvalidacion;
	}

	public void setRutaArchivoExcelConvalidacion(String rutaArchivoExcelConvalidacion) {
		this.rutaArchivoExcelConvalidacion = rutaArchivoExcelConvalidacion;
	}

	public EntidadAlumno getEntidadAlumno() {
		return entidadAlumno;
	}

	public void setEntidadAlumno(EntidadAlumno entidadAlumno) {
		this.entidadAlumno = entidadAlumno;
	}

	public List<EntidadDetalleConvalidacion> getLstDetalleConvalidacion() {
		return lstDetalleConvalidacion;
	}

	public void setLstDetalleConvalidacion(List<EntidadDetalleConvalidacion> lstDetalleConvalidacion) {
		this.lstDetalleConvalidacion = lstDetalleConvalidacion;
	}

	public EntidadDocente getEntidadDocente() {
		return entidadDocente;
	}

	public void setEntidadDocente(EntidadDocente entidadDocente) {
		this.entidadDocente = entidadDocente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idConvalidacion;
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
		EntidadConvalidacion other = (EntidadConvalidacion) obj;
		if (idConvalidacion != other.idConvalidacion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntidadConvalidacion [idConvalidacion=" + idConvalidacion + "]";
	}

	public EntidadConvalidacion clone() {
		EntidadConvalidacion clone = null;
		try {
			clone = (EntidadConvalidacion) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}
