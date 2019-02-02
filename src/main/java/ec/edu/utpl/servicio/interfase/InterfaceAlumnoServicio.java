package ec.edu.utpl.servicio.interfase;

import java.util.List;

import ec.edu.utpl.modelo.entidad.EntidadAlumno;

public interface InterfaceAlumnoServicio<T> extends InterfaceServicio<T> {

	List<EntidadAlumno> buscarPorPropiedades(String identificacionAlumno, String nombreAlumno, String apellidoAlumno);

	T buscarPorCedula(String identificacionAlumno) throws InstantiationException, IllegalAccessException;

}
