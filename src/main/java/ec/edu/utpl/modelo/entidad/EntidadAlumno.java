package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tbl_alumno", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "identificacion_alumno" }, name = "UK_IDENTIFICACION_ALUMNO") })
public class EntidadAlumno extends Entidad implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alumno")
	private int idAlumno;

	@Column(name = "identificacion_alumno", nullable = false, length = 10)
	private String identificacionAlumno;

	@Column(name = "nombre_alumno", nullable = false, length = 80)
	private String nombreAlumno;

	@Column(name = "apellido_alumno", nullable = false, length = 80)
	private String apellidoAlumno;

	@Column(name = "direccion_alumno", nullable = false, columnDefinition = "text")
	private String direccionAlumno;

	@Column(name = "celular_alumno", nullable = false, length = 10)
	private String celularAlumno;

	@Column(name = "universidad_alumno", nullable = false, columnDefinition = "text")
	private String universidadAlumno;

	@Transient
	private String nombreCompletoAlumno;

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}

	public String getApellidoAlumno() {
		return apellidoAlumno;
	}

	public void setApellidoAlumno(String apellidoAlumno) {
		this.apellidoAlumno = apellidoAlumno;
	}

	public String getIdentificacionAlumno() {
		return identificacionAlumno;
	}

	public void setIdentificacionAlumno(String identificacionAlumno) {
		this.identificacionAlumno = identificacionAlumno;
	}

	public String getDireccionAlumno() {
		return direccionAlumno;
	}

	public void setDireccionAlumno(String direccionAlumno) {
		this.direccionAlumno = direccionAlumno;
	}

	public String getCelularAlumno() {
		return celularAlumno;
	}

	public void setCelularAlumno(String celularAlumno) {
		this.celularAlumno = celularAlumno;
	}

	public String getUniversidadAlumno() {
		return universidadAlumno;
	}

	public void setUniversidadAlumno(String universidadAlumno) {
		this.universidadAlumno = universidadAlumno;
	}

	public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
		this.nombreCompletoAlumno = nombreCompletoAlumno;
	}

	public String getNombreCompletoAlumno() {
		String separador;
		separador = ((nombreAlumno == null || nombreAlumno.equals(""))
				|| (apellidoAlumno == null || apellidoAlumno.equals(""))) ? "" : " ";
		apellidoAlumno = apellidoAlumno == null ? "" : apellidoAlumno;
		nombreAlumno = nombreAlumno == null ? "" : nombreAlumno;
		return new StringBuilder().append(apellidoAlumno).append(separador).append(nombreAlumno).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAlumno;
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
		EntidadAlumno other = (EntidadAlumno) obj;
		if (idAlumno != other.idAlumno)
			return false;
		return true;
	}

	public EntidadAlumno clone() {
		EntidadAlumno clone = null;
		try {
			clone = (EntidadAlumno) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
}