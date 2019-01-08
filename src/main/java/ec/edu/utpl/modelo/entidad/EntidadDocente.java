package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_docente")
public class EntidadDocente extends Entidad implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_docente")
	private int idDocente;

	@Column(name = "nombre_docente", nullable = false, length = 80)
	private String nombreDocente;

	@Column(name = "apellido_docente", nullable = false, length = 80)
	private String apellidoDocente;

	@Column(name = "identificacion_docente", nullable = false, length = 15)
	private String identificacionDocente;
	
	@Transient
	private String nombreCompletoDocente;

	public int getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getApellidoDocente() {
		return apellidoDocente;
	}

	public void setApellidoDocente(String apellidoDocente) {
		this.apellidoDocente = apellidoDocente;
	}

	public String getIdentificacionDocente() {
		return identificacionDocente;
	}

	public void setIdentificacionDocente(String identificacionDocente) {
		this.identificacionDocente = identificacionDocente;
	}

	public String getNombreCompletoDocente() {
		String separador;
		separador = ((nombreDocente == null || nombreDocente.equals(""))
				|| (apellidoDocente == null || apellidoDocente.equals(""))) ? "" : " ";
		apellidoDocente = apellidoDocente == null ? "" : apellidoDocente;
		nombreDocente = nombreDocente == null ? "" : nombreDocente;
		return new StringBuilder().append(apellidoDocente).append(separador).append(nombreDocente).toString();
	}
}