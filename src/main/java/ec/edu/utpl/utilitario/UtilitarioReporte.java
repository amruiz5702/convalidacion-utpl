package ec.edu.utpl.utilitario;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class UtilitarioReporte {

	private static final String PATH_IMAGEN = File.separator + "resources" + File.separator + "skin" + File.separator + "images" + File.separator;
	private static final String STR_TITULO_CABECERA_REPORTE = "UNIVERSIDAD TÉCNICA PARTICULAR DE LOJA";
	private static final String STR_SUBTITULO_CABECERA_REPORTE = "Universidad Católica de Loja";
	public static final Text NEW_LINE = new Text("\n") ;

	public static String obtenerRutaImagen(String imagen) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + PATH_IMAGEN + imagen;
	}

	public static Image crearImagen(String imagen) throws IOException {
		return new Image(ImageDataFactory.create(imagen));
	}

	public static PdfFont crearFontHelvica() throws Exception {
		return PdfFontFactory.createFont(StandardFonts.HELVETICA);
	}
	
	public static Paragraph crearTituloReporte(String titulo) throws Exception {
		return new Paragraph(titulo)
				.setBold()
				.setFont(crearFontHelvica())
				.setFontSize(7)
				.setTextAlignment(TextAlignment.CENTER);
	}
	
	public static Text crearEstiloEtiqueta(String etiqueta) throws Exception {
		return new Text(etiqueta)
				.setBold()
				.setFont(crearFontHelvica())
				.setFontSize(7);
	}
	
	public static Paragraph crearEstiloEtiqueta(String [] etiquetas) throws Exception {
		Paragraph paragraphEtiquetas = new Paragraph();
		for (String etiqueta : etiquetas) {
			paragraphEtiquetas.add(crearEstiloEtiqueta(etiqueta));
			paragraphEtiquetas.add(NEW_LINE);
		}
		return paragraphEtiquetas.setFixedLeading(10);
	}
	
	public static Text crearEstiloTexto(String texto) throws Exception {
		return new Text(texto)
				.setFont(crearFontHelvica())
				.setFontSize(7);
	}
	
	public static Paragraph crearEstiloTexto(String [] textos) throws Exception {
		Paragraph paragraphTexto = new Paragraph();
		for (String texto : textos) {
			paragraphTexto.add(crearEstiloTexto(texto));
			paragraphTexto.add(NEW_LINE);
		}
		return paragraphTexto.setFixedLeading(10);
	}
	
	public static Paragraph crearCabeceraReporte() throws Exception {
		Text txtTituloUniveridad = new Text(STR_TITULO_CABECERA_REPORTE).setBold().setFont(crearFontHelvica()).setFontSize(9);
		Text txtSubTituloUniveridad = new Text(STR_SUBTITULO_CABECERA_REPORTE).setFont(crearFontHelvica()).setFontSize(7);
		
		return new Paragraph()
				.add(crearImagen(obtenerRutaImagen("logo-3.png")).scale(new Float(0.1), new Float(0.1)))
				.add(new Text("\n"))
				.add(txtTituloUniveridad)
				.add(new Text("\n"))
				.add(txtSubTituloUniveridad)
				.setTextAlignment(TextAlignment.CENTER);
	}
	
	public static UnitValue crearAncho(float procentaje) {
		return new UnitValue(UnitValue.PERCENT, procentaje);
	}
	
}
