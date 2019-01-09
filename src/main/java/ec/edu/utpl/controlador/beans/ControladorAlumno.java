package ec.edu.utpl.controlador.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ec.edu.utpl.modelo.entidad.EntidadAlumno;
import ec.edu.utpl.servicio.interfase.InterfaceAlumnoServicio;
import ec.edu.utpl.spring.scope.SpringScopeView;
import ec.edu.utpl.utilitario.UtilitarioMensajeFace;

@Named
@SpringScopeView
public class ControladorAlumno extends Controlador<EntidadAlumno> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private InterfaceAlumnoServicio<EntidadAlumno> alumnoServicio;
	private boolean editando = Boolean.FALSE;

	private EntidadAlumno busquedaAlumno;
	private List<EntidadAlumno> lstAlumno;

	public EntidadAlumno getBusquedaAlumno() {
		return busquedaAlumno;
	}

	public void setBusquedaAlumno(EntidadAlumno busquedaAlumno) {
		this.busquedaAlumno = busquedaAlumno;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public List<EntidadAlumno> getLstAlumno() {
		return lstAlumno;
	}

	public void setLstAlumno(List<EntidadAlumno> lstAlumno) {
		this.lstAlumno = lstAlumno;
	}

	public void cmmdBtnBuscarAlumno(ActionEvent actionEvent) {
		lstAlumno = alumnoServicio.buscarPorPropiedades(busquedaAlumno);
		if (lstAlumno.isEmpty()) {
			UtilitarioMensajeFace.agregarMensaje("alumnoNoEncontrado", null, FacesMessage.SEVERITY_ERROR);
		}

	}

	public void cmmdBtnPrepararCreacionAlumno(ActionEvent actionEvent)
			throws InstantiationException, IllegalAccessException {
		setEditando(Boolean.FALSE);
		prepararInstanciaAlumno();
	}

	public void cmmdBtnPrepararEdicionAlumno(EntidadAlumno instanciaAlumno) {
		this.setInstanciaEntidad((instanciaAlumno).clone());
		setEditando(Boolean.TRUE);
	}

	public void cmmdBtnPrepararEliminarAlumno(EntidadAlumno instanciaAlumno) {
		this.setInstanciaEntidad(instanciaAlumno);
	}

	public void cmmdBtnGuardarAlumno(ActionEvent actionEvent) throws InstantiationException, IllegalAccessException {
		String claveMensaje;
		Object[] argumentos = new Object[] { this.getInstanciaEntidad().getIdentificacionAlumno() };

		if (!editando) {
			claveMensaje = "alumnoCreado";
			alumnoServicio.crear(this.getInstanciaEntidad());
			cmmdBtnPrepararCreacionAlumno(actionEvent);
			UtilitarioMensajeFace.agregarMensaje(claveMensaje, argumentos, FacesMessage.SEVERITY_INFO);
		} else {
			claveMensaje = "alumnoEditado";
			alumnoServicio.editar(this.getInstanciaEntidad());
			UtilitarioMensajeFace.agregarMensaje(claveMensaje, argumentos, FacesMessage.SEVERITY_INFO);
			cmmdBtnBuscarAlumno(actionEvent);
		}

	}

	public void cmmdBtnEliminarAlumno(ActionEvent actionEvent) {
		String claveMensaje;
		Object[] argumentos = new Object[] { this.getInstanciaEntidad().getIdentificacionAlumno() };

		claveMensaje = "alumnoEliminado";
		alumnoServicio.eliminar(this.getInstanciaEntidad());
		UtilitarioMensajeFace.agregarMensaje(claveMensaje, argumentos, FacesMessage.SEVERITY_INFO);
		eliminarRegistroLista(this.getInstanciaEntidad().getIdAlumno());

	}

	public void evtPrepararInstaciaBusquedaAlumno() throws InstantiationException, IllegalAccessException {
		prepararInstanciaBusquedaAlumno();
	}

	private void prepararInstanciaBusquedaAlumno() throws InstantiationException, IllegalAccessException {
		this.setBusquedaAlumno(alumnoServicio.generarNuevaInstancia());
	}

	private void prepararInstanciaAlumno() throws InstantiationException, IllegalAccessException {
		this.setInstanciaEntidad(alumnoServicio.generarNuevaInstancia());
	}

	private void eliminarRegistroLista(Integer idAlumno) {
		int index = 0;
		for (EntidadAlumno entidadAlumno : lstAlumno) {
			if (entidadAlumno.getIdAlumno() == idAlumno) {
				lstAlumno.remove(index);
				break;
			}
			index++;
		}
	}
}
