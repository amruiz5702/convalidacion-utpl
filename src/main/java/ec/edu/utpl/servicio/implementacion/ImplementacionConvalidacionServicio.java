package ec.edu.utpl.servicio.implementacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edduarte.similarity.Similarity;

import ec.edu.utpl.modelo.dao.interfase.InterfaceConvalidacionDao;
import ec.edu.utpl.modelo.entidad.EntidadComponente;
import ec.edu.utpl.modelo.entidad.EntidadContenido;
import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;
import ec.edu.utpl.modelo.entidad.EntidadDetalleConvalidacion;
import ec.edu.utpl.servicio.interfase.InterfaceConvalidacionServicio;

@Service
public class ImplementacionConvalidacionServicio implements InterfaceConvalidacionServicio<EntidadConvalidacion> {

	private String PATH_FILE = "C:\\DOCUMENTS";

	@Autowired
	private InterfaceConvalidacionDao<EntidadConvalidacion> interfaceConvalidacionDao;

	@Override
	public void crear(EntidadConvalidacion entidad) {
		interfaceConvalidacionDao.crear(entidad);
	}

	@Override
	public void editar(EntidadConvalidacion entidad) {

	}

	@Override
	public void eliminar(EntidadConvalidacion entidad) {

	}

	@Override
	public List<EntidadConvalidacion> obtener() {
		return null;
	}

	@Override
	public EntidadConvalidacion obtener(Integer id) {
		return null;
	}

	@Override
	public List<EntidadConvalidacion> obtener(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		return null;
	}

	@Override
	public int contar(Map<String, Object> filters) {
		return 0;
	}

	@Override
	public EntidadConvalidacion generarNuevaInstancia() throws InstantiationException, IllegalAccessException {
		EntidadConvalidacion nuevaInstancia = null;
		nuevaInstancia = EntidadConvalidacion.class.newInstance();
		return nuevaInstancia;
	}

	@Override
	public List<String[]> leerArchivoExcel(String nombre, InputStream archivoExcel) throws IOException {
		List<String[]> lstMateriasAprobadas = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(archivoExcel);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() > 2) {
				String[] columnas = new String[row.getLastCellNum()];
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						columnas[cell.getColumnIndex()] = cell.getRichStringCellValue().getString();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							columnas[cell.getColumnIndex()] = cell.getDateCellValue() + "";
						} else {
							columnas[cell.getColumnIndex()] = (int) cell.getNumericCellValue() + "";
						}
						break;
					case BOOLEAN:
						columnas[cell.getColumnIndex()] = cell.getBooleanCellValue() + "";
						break;
					case FORMULA:
						columnas[cell.getColumnIndex()] = cell.getCellFormula() + "";
						break;
					case BLANK:
						break;
					case ERROR:
						break;
					case _NONE:
						break;
					default:
						break;
					}
				}
				lstMateriasAprobadas.add(columnas);
			}

		}
		if (workbook != null) {
			workbook.close();
		}

		return lstMateriasAprobadas;
	}

	public List<EntidadDetalleConvalidacion> validarSimilitudMaterias(String nombreAlgoritmo, Integer umbralReferencia,
			List<String[]> lstMateriaAprobadas, List<EntidadComponente> lstComponente) {
		double umbralReal = 0, umbralMayor = 0;
		List<EntidadDetalleConvalidacion> lstDetalleConvalidacion = crearDetalleConvalidacion(lstMateriaAprobadas);
		int index = 0;
		EntidadComponente entidadComponente = null;
		for (EntidadDetalleConvalidacion entidadDetalleConvalidacion : lstDetalleConvalidacion) {
			for (EntidadComponente entidadTemp : lstComponente) {

				switch (nombreAlgoritmo) {
				case "JACCARD":
					umbralReal = Similarity.jaccard().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
							entidadTemp.getNombreComponente().toLowerCase());
					break;
				case "MINHASH":
					umbralReal = Similarity.minhash().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
							entidadTemp.getNombreComponente().toLowerCase());
					break;
				case "LSH":
					umbralReal = Similarity.lsh().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
							entidadTemp.getNombreComponente().toLowerCase());
					break;
				}

				System.out.println("Revisando " + entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase()
						+ " ========>  " + entidadTemp.getNombreComponente() + " ===========> " + umbralReal);

				if (index == 0) {
					umbralMayor = umbralReal;
					entidadComponente = entidadTemp;
				}

				if (umbralReal > umbralMayor) {
					umbralMayor = umbralReal;
					entidadComponente = entidadTemp;
				}
				index++;
			}
			if (umbralMayor > ((double) umbralReferencia / 100)) {
				entidadDetalleConvalidacion.setUmbralConvalidacion(umbralMayor);
				entidadDetalleConvalidacion.setComponente(entidadComponente);
				System.out.println(" Se convalida " + entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase()
						+ " con " + entidadComponente.getNombreComponente() + ", por un % de " + umbralMayor);
			}
			index = 0;
			umbralMayor = 0;
			entidadComponente = null;
		}

		Collections.sort(lstDetalleConvalidacion, new Comparator<EntidadDetalleConvalidacion>() {
			@Override
			public int compare(EntidadDetalleConvalidacion o1, EntidadDetalleConvalidacion o2) {
				return o2.getUmbralDetalleConvalidacion().compareTo(o1.getUmbralDetalleConvalidacion());
			}
		});
		return lstDetalleConvalidacion;
	}

	public EntidadDetalleConvalidacion validarSimilitudMateriaContenido(String nombreAlgoritmo,
			Integer umbralReferencia, EntidadDetalleConvalidacion entidadDetalleConvalidacion,
			EntidadComponente entidadComponente) {

		double umbralReal = 0, umbralMayor = 0;
		int index = 0;
		EntidadContenido entidadContenido = null;
		for (EntidadContenido entidadContenidoTemp : entidadComponente.getLstContenido()) {
			switch (nombreAlgoritmo) {
			case "JACCARD":
				umbralReal = Similarity.jaccard().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
						entidadContenidoTemp.getDescripcionContenido().toLowerCase());
				break;
			case "MINHASH":
				umbralReal = Similarity.minhash().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
						entidadContenidoTemp.getDescripcionContenido().toLowerCase());
				break;
			case "LSH":
				umbralReal = Similarity.lsh().of(entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase(),
						entidadContenidoTemp.getDescripcionContenido().toLowerCase());
				break;
			}
			System.out.println("Revisando " + entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase()
					+ " ========>  " + entidadComponente.getNombreComponente() + " ========>  "
					+ entidadContenidoTemp.getDescripcionContenido() + " ===========> " + umbralReal);

			if (index == 0) {
				umbralMayor = umbralReal;
				entidadContenido = entidadContenidoTemp;
			}

			if (umbralReal > umbralMayor) {
				umbralMayor = umbralReal;
				entidadContenido = entidadContenidoTemp;
			}
			index++;
		}
		if (umbralMayor > ((double) umbralReferencia / 100)) {
			entidadDetalleConvalidacion.setUmbralConvalidacion(umbralMayor);
			entidadDetalleConvalidacion.setComponente(entidadComponente);
			entidadDetalleConvalidacion.setDescripcionDetalleConvalidacion(
					"Se convalida por similitud de contenido " + entidadContenido.getDescripcionContenido());
			System.out.println(" Se convalida " + entidadDetalleConvalidacion.getMateriaAprobada().toLowerCase()
					+ " con " + entidadComponente.getNombreComponente() + ", por un % de " + umbralMayor);
		} else {
			entidadDetalleConvalidacion.setUmbralConvalidacion(0.0);
			entidadDetalleConvalidacion.setComponente(null);
		}
		return entidadDetalleConvalidacion;
	}

	private List<EntidadDetalleConvalidacion> crearDetalleConvalidacion(List<String[]> lstMateriaAprobadas) {
		List<EntidadDetalleConvalidacion> lstDetalleConvalidacion = new ArrayList<>();
		for (String[] materiaAprobada : lstMateriaAprobadas) {
			EntidadDetalleConvalidacion entidadDetalleConvalidacion = new EntidadDetalleConvalidacion();
			entidadDetalleConvalidacion.setMateriaAprobada(materiaAprobada[1]);
			lstDetalleConvalidacion.add(entidadDetalleConvalidacion);
		}
		return lstDetalleConvalidacion;
	}

	public String guardarArchivoEnServidor(InputStream in, String fileName) throws IOException {

		OutputStream out = new FileOutputStream(new File(PATH_FILE + File.separator + fileName));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = in.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
		return PATH_FILE + File.separator + fileName;
	}

}
