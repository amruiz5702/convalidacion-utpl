package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "tbl_usuario", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "nombre_usuario" }, name = "UK_NOMBRE_USUARIO") })
public class EntidadUsuario extends Entidad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "nombre_usuario")
	private String nombreUsuario;

	@Column(name = "clave_usuario")
	private String claveUsuario;

	@Type(type = "true_false")
	@Column(name = "activo_usuario", nullable = false, columnDefinition = "ENUM('T','F') DEFAULT 'T'", insertable = false)
	private boolean activoUsuario;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_usuario_rol", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"), foreignKey = @ForeignKey(name = "FK_ROL_USUARIO"), inverseForeignKey = @ForeignKey(name = "FK_USUARIO_ROL"))
	private List<EntidadRol> lstRoles = new ArrayList<EntidadRol>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_docente", foreignKey = @ForeignKey(name = "FK_DOCENTE_USUARIO"), nullable = false)
	private EntidadDocente docenteUsuario;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public List<EntidadRol> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<EntidadRol> lstRoles) {
		this.lstRoles = lstRoles;
	}

	public boolean isActivoUsuario() {
		return activoUsuario;
	}

	public void setActivoUsuario(boolean activoUsuario) {
		this.activoUsuario = activoUsuario;
	}

	public EntidadDocente getDocenteUsuario() {
		return docenteUsuario;
	}

	public void setDocenteUsuario(EntidadDocente docenteUsuario) {
		this.docenteUsuario = docenteUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		EntidadUsuario other = (EntidadUsuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

}
