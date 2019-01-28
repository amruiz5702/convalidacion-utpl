package ec.edu.utpl.modelo.entidad;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_componente")
public class EntidadComponente extends Entidad implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_componente")
	private Integer idComponente;

	@Column(name = "nombre_componente", nullable = false, columnDefinition = "text")
	private String nombreComponente;

	@Column(name = "grupo_componente", nullable = false, length = 80)
	private String grupoComponente;

	@Column(name = "nivel_componente", nullable = false, length = 2)
	private Integer nivelComponente;

	@Column(name = "numero_credito_componente", nullable = false, length = 2)
	private Integer numeroCreditoComponente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_componente", foreignKey = @ForeignKey(name = "FK_CONTENIDO_COMPONENTE"), nullable = false)
	private List<EntidadContenido> lstContenido;

	public Integer getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}

	public String getNombreComponente() {
		return nombreComponente;
	}

	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	public String getGrupoComponente() {
		return grupoComponente;
	}

	public void setGrupoComponente(String grupoComponente) {
		this.grupoComponente = grupoComponente;
	}

	public Integer getNivelComponente() {
		return nivelComponente;
	}

	public void setNivelComponente(Integer nivelComponente) {
		this.nivelComponente = nivelComponente;
	}

	public Integer getNumeroCreditoComponente() {
		return numeroCreditoComponente;
	}

	public void setNumeroCreditoComponente(Integer numeroCreditoComponente) {
		this.numeroCreditoComponente = numeroCreditoComponente;
	}

	public List<EntidadContenido> getLstContenido() {
		return lstContenido;
	}

	public void setLstContenido(List<EntidadContenido> lstContenido) {
		this.lstContenido = lstContenido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComponente == null) ? 0 : idComponente.hashCode());
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
		EntidadComponente other = (EntidadComponente) obj;
		if (idComponente == null) {
			if (other.idComponente != null)
				return false;
		} else if (!idComponente.equals(other.idComponente))
			return false;
		return true;
	}

}
