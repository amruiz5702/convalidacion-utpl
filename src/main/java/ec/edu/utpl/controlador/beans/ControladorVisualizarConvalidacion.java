package ec.edu.utpl.controlador.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.web.context.annotation.SessionScope;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;

import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;
import ec.edu.utpl.modelo.entidad.EntidadDetalleConvalidacion;
import ec.edu.utpl.utilitario.UtilitarioReporte;

@Named
@SessionScope
public class ControladorVisualizarConvalidacion extends Controlador<EntidadConvalidacion> implements Serializable {

	private static final long serialVersionUID = 1L;


	private StreamedContent reporteConvalidacion;


	public StreamedContent getReporteConvalidacion() {
		return reporteConvalidacion;
	}


	public void setReporteConvalidacion(StreamedContent reporteConvalidacion) {
		this.reporteConvalidacion = reporteConvalidacion;
	}


	public void cmmdBtnDescargarDocumento(EntidadConvalidacion convalidacion) throws Exception {


		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = new PdfWriter(outStream);
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);

		PageSize pagesize = PageSize.A4;
		Document document = new Document(pdfDoc, pagesize);

		Table tblCabeceraReporte = new Table(2).setWidth(UtilitarioReporte.crearAncho(100));
		tblCabeceraReporte.addCell(
				new Cell()
				.setWidth(UtilitarioReporte.crearAncho(20))
				.add(UtilitarioReporte.crearEstiloEtiqueta(
						new String[]{
								"Identificacion:",
								"Nombres:",
								"Direccion:",
								"Universidad:",
								"Fecha Convalidación:"}
						))
				.setBorder(Border.NO_BORDER));		
		
		tblCabeceraReporte.addCell(
				new Cell()
				.setWidth(UtilitarioReporte.crearAncho(80))
				.add(UtilitarioReporte.crearEstiloTexto(
						new String[]{
								convalidacion.getEntidadAlumno().getIdentificacionAlumno(),
								convalidacion.getEntidadAlumno().getNombreCompletoAlumno(),
								convalidacion.getEntidadAlumno().getDireccionAlumno(),
								convalidacion.getEntidadAlumno().getUniversidadAlumno(),
								new SimpleDateFormat(" dd MMMMM yyyy ").format(convalidacion.getFechaRegistroConvalidacion())}
						))
				.setBorder(Border.NO_BORDER));		

		tblCabeceraReporte.setNextRenderer(new TableBorderRenderer(tblCabeceraReporte));
		
		Table tblConvalidacion = new Table(6);
		tblConvalidacion.setWidth(UtilitarioReporte.crearAncho(100));

		tblConvalidacion.addCell(new Cell()
					.setWidth(UtilitarioReporte.crearAncho(30))
					.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta(convalidacion
								.getEntidadAlumno()
								.getUniversidadAlumno()
								.toUpperCase()
							).setTextAlignment(TextAlignment.CENTER)))
				);
		
		tblConvalidacion.addCell(new Cell(1,5)
				.setWidth(UtilitarioReporte.crearAncho(0))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("UNIVERSIDAD TÉCNICA PARTICULAR DE LOJA"))
						.setTextAlignment(TextAlignment.CENTER)
						)						
			);

		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(15))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("MATERIA APROBADA")).setTextAlignment(TextAlignment.CENTER)));
		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(10))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("CÓDIGO")).setTextAlignment(TextAlignment.CENTER)));
		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(15))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("COMPONENTE ACADÉMICO")).setTextAlignment(TextAlignment.CENTER)));
		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(15))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("TIPO")).setTextAlignment(TextAlignment.CENTER)));
		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(5))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("CICLO")).setTextAlignment(TextAlignment.CENTER)));
		tblConvalidacion.addCell(new Cell()
				.setWidth(UtilitarioReporte.crearAncho(5))
				.add(new Paragraph(UtilitarioReporte.crearEstiloEtiqueta("N° CRÉDITOS")).setTextAlignment(TextAlignment.CENTER)));

		for (EntidadDetalleConvalidacion detalle : convalidacion.getLstDetalleConvalidacion()) {
			
			tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getMateriaAprobada().toUpperCase()))));
			if(Objects.isNull(detalle.getComponente())) {
				tblConvalidacion.addCell(new Cell());
				tblConvalidacion.addCell(new Cell());
				tblConvalidacion.addCell(new Cell());
				tblConvalidacion.addCell(new Cell());
				tblConvalidacion.addCell(new Cell());
			}else {
				tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getComponente().getCodigoComponente().toUpperCase()))));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getComponente().getNombreComponente().toUpperCase()))));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getComponente().getGrupoComponente().toUpperCase()))));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getComponente().getNivelComponente().toString().toUpperCase()))));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(UtilitarioReporte.crearEstiloTexto(detalle.getComponente().getNumeroCreditoComponente().toString().toUpperCase()))));
			}
			
			
		}
		Paragraph lblElaboradoPor = new Paragraph()
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.crearEstiloEtiqueta("Elaborado Por:"));
			
		Paragraph prphFirma = new Paragraph()
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.crearEstiloEtiqueta("_________________________________________________"))
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.crearEstiloTexto(convalidacion.getEntidadDocente().getNombreCompletoDocente() ))
				.add(UtilitarioReporte.NEW_LINE)
				.add(UtilitarioReporte.crearEstiloTexto("Responsable de Convalidación" ))
				.setFixedLeading(10)
				.setTextAlignment(TextAlignment.CENTER);
		
		document.add(UtilitarioReporte.crearCabeceraReporte());
		document.add(UtilitarioReporte.crearTituloReporte("TABLA DE EQUIVALENCIAS DE CONVALIDACIÓN"));
		document.add(tblCabeceraReporte);
		document.add(new Paragraph(UtilitarioReporte.NEW_LINE).setFixedLeading(10));
		document.add(tblConvalidacion);
		document.add(lblElaboradoPor);
		document.add(prphFirma);
		document.close();

		setReporteConvalidacion(new DefaultStreamedContent(new ByteArrayInputStream(outStream.toByteArray()), "application/pdf"));
	}
}

class TableBorderRenderer extends TableRenderer {
    public TableBorderRenderer(Table modelElement) {
        super(modelElement);
    }

    @Override
    protected void drawBorders(DrawContext drawContext) {
        Rectangle rect = getOccupiedAreaBBox();
        drawContext.getCanvas()
                .saveState()
                .rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight())
                .stroke()
                .restoreState()
                .setLineWidth(new Float(0.1));
    }
}
