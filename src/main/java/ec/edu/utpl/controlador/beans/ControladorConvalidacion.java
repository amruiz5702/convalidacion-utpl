package ec.edu.utpl.controlador.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import ec.edu.utpl.modelo.entidad.EntidadAlumno;
import ec.edu.utpl.modelo.entidad.EntidadComponente;
import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;
import ec.edu.utpl.modelo.entidad.EntidadDetalleConvalidacion;
import ec.edu.utpl.servicio.interfase.InterfaceAlumnoServicio;
import ec.edu.utpl.servicio.interfase.InterfaceComponenteServicio;
import ec.edu.utpl.servicio.interfase.InterfaceConvalidacionServicio;
import ec.edu.utpl.spring.scope.SpringScopeView;
import ec.edu.utpl.utilitario.UtilitarioMensajeFace;

@Named
@SpringScopeView
public class ControladorConvalidacion extends Controlador<EntidadConvalidacion> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private InterfaceConvalidacionServicio<EntidadConvalidacion> interfaceConvalidacionServicio;
	@Autowired
	private InterfaceAlumnoServicio<EntidadAlumno> interfaceAlumnoServicio;
	@Autowired
	private InterfaceComponenteServicio<EntidadComponente> interfaceComponenteServicio;

	private List<String[]> lstMateriaAprobada;

	private UploadedFile upldMateriaAprobada;

	private EntidadDetalleConvalidacion entidadDetalleConvalidacion;

	private List<EntidadComponente> lstComponente;

	private EntidadComponente entidadComponente;

	public List<String[]> getLstMateriaAprobada() {
		return lstMateriaAprobada;
	}

	public void setLstMateriaAprobada(List<String[]> lstMateriaAprobada) {
		this.lstMateriaAprobada = lstMateriaAprobada;
	}

	public UploadedFile getUpldMateriaAprobada() {
		return upldMateriaAprobada;
	}

	public void setUpldMateriaAprobada(UploadedFile upldMateriaAprobada) {
		this.upldMateriaAprobada = upldMateriaAprobada;
	}

	public EntidadDetalleConvalidacion getEntidadDetalleConvalidacion() {
		return entidadDetalleConvalidacion;
	}

	public void setEntidadDetalleConvalidacion(EntidadDetalleConvalidacion entidadDetalleConvalidacion) {
		this.entidadDetalleConvalidacion = entidadDetalleConvalidacion;
	}

	public List<EntidadComponente> getLstComponente() {
		return lstComponente;
	}

	public void setLstComponente(List<EntidadComponente> lstComponente) {
		this.lstComponente = lstComponente;
	}

	public EntidadComponente getEntidadComponente() {
		return entidadComponente;
	}

	public void setEntidadComponente(EntidadComponente entidadComponente) {
		this.entidadComponente = entidadComponente;
	}

	public void cmmdBtnBuscarAlumnoPorCedula(ActionEvent ae) throws InstantiationException, IllegalAccessException {
		EntidadAlumno entidadAlumno = interfaceAlumnoServicio
				.buscarPorCedula(instanciaEntidad.getEntidadAlumno().getIdentificacionAlumno());
		if (Objects.nonNull(entidadAlumno)) {
			this.instanciaEntidad.setEntidadAlumno(entidadAlumno);
		} else {
			UtilitarioMensajeFace.agregarMensaje("alumnoNoEncontrado", null, FacesMessage.SEVERITY_ERROR);
		}

	}

	public void cmmdBtnRevisarConvalidacion(ActionEvent ae) {

		this.instanciaEntidad.setLstDetalleConvalidacion(this.interfaceConvalidacionServicio.validarSimilitudMaterias(
				this.instanciaEntidad.getMedidaSimilitudConvalidacion(),
				this.instanciaEntidad.getPorcentajeUmbralConvalidacion(), this.lstMateriaAprobada,
				this.getLstComponente()));
	}

	public void cmmdBtnRevisarConvalidacionContenido(ActionEvent ae) {

		this.interfaceConvalidacionServicio.validarSimilitudMateriaContenido(
				this.instanciaEntidad.getMedidaSimilitudConvalidacion(),
				this.instanciaEntidad.getPorcentajeUmbralConvalidacion(), this.entidadDetalleConvalidacion,
				this.entidadComponente);

		if (this.entidadDetalleConvalidacion.getComponente() == null) {
			UtilitarioMensajeFace.agregarMensaje("materiaNoConvalidadaContenido", null, FacesMessage.SEVERITY_ERROR);
		} else {
			UtilitarioMensajeFace.agregarMensaje("materiaConvalidadaContenido", null, FacesMessage.SEVERITY_INFO);
		}

	}

	public void cmmdBtnPrepararConvalidacionContenido(EntidadDetalleConvalidacion detalleConvalidacion) {
		this.entidadDetalleConvalidacion = detalleConvalidacion;
		this.setEntidadComponente(null);
	}

	public void evtSubirArchivo(FileUploadEvent event) throws IOException {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		this.upldMateriaAprobada = event.getFile();
		this.lstMateriaAprobada = interfaceConvalidacionServicio.leerArchivoExcel(event.getFile().getFileName(),
				event.getFile().getInputstream());
	}

	public void evtPrepararInstaciaConvalidacion() throws InstantiationException, IllegalAccessException {
		prepararInstanciaConvalidacion();
	}

	public void evtObtenerComponentes() {
		this.setLstComponente(this.interfaceComponenteServicio.obtener());
	}

	private void prepararInstanciaConvalidacion() throws InstantiationException, IllegalAccessException {
		this.setInstanciaEntidad(interfaceConvalidacionServicio.generarNuevaInstancia());
	}
}
