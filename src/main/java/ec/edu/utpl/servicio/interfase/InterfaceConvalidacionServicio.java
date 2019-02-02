package ec.edu.utpl.servicio.interfase;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ec.edu.utpl.modelo.entidad.EntidadComponente;
import ec.edu.utpl.modelo.entidad.EntidadDetalleConvalidacion;

public interface InterfaceConvalidacionServicio<T> extends InterfaceServicio<T> {

	public List<String[]> leerArchivoExcel(String name, InputStream archivoExcel) throws IOException;

	public List<EntidadDetalleConvalidacion> validarSimilitudMaterias(String nombreAlgoritmo, Integer umbral,
			List<String[]> lstMateriaAprobadas, List<EntidadComponente> lstComponente);

	public EntidadDetalleConvalidacion validarSimilitudMateriaContenido(String nombreAlgoritmo,
			Integer umbralReferencia, EntidadDetalleConvalidacion entidadDetalleConvalidacion,
			EntidadComponente entidadComponente);

	public String guardarArchivoEnServidor(InputStream in, String fileName)
			throws IOException;
}
