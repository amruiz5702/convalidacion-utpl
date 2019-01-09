package ec.edu.utpl.servicio.interfase;

import java.util.List;

import ec.edu.utpl.modelo.entidad.EntidadAlumno;

public interface InterfaceAlumnoServicio<T> extends InterfaceServicio<T> {

	List<EntidadAlumno> buscarPorPropiedades(EntidadAlumno instanciaAlumno);

}
