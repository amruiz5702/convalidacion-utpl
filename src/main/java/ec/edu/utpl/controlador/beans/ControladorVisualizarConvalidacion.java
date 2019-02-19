package ec.edu.utpl.controlador.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.web.context.annotation.SessionScope;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;

import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;
import ec.edu.utpl.modelo.entidad.EntidadDetalleConvalidacion;

@Named
@SessionScope
public class ControladorVisualizarConvalidacion extends Controlador<EntidadConvalidacion> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String STR_TITLE_UNIVERSIDAD = "UNIVERSIDAD TÉCNICA PARTICULAR DE LOJA";
	private static final String STR_SUBTITLE_UNIVERSIDAD = "Universidad Católica de Loja";

	private StreamedContent reporteConvalidacion;


	public StreamedContent getReporteConvalidacion() {
		return reporteConvalidacion;
	}


	public void setReporteConvalidacion(StreamedContent reporteConvalidacion) {
		this.reporteConvalidacion = reporteConvalidacion;
	}


	public void cmmdBtnDescargarDocumento(EntidadConvalidacion convalidacion) throws IOException {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "skin"
				+ File.separator + "images" + File.separator + "logo-3.png";

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter;
		pdfWriter = new PdfWriter(outStream);
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);

		PageSize pagesize = PageSize.A4;
		Document document = new Document(pdfDoc, pagesize);

		PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		
		Image img = new Image(ImageDataFactory.create(logo));
		Text txtTituloUniveridad = new Text(STR_TITLE_UNIVERSIDAD).setBold().setFont(font).setFontSize(9);
		Text txtSubTituloUniveridad = new Text(STR_SUBTITLE_UNIVERSIDAD).setFont(font).setFontSize(7);
		Paragraph prphCabecera = new Paragraph()
				.add(img.scale(new Float(0.1), new Float(0.1)))
				.add(new Text("\n"))
				.add(txtTituloUniveridad)
				.add(new Text("\n"))
				.add(txtSubTituloUniveridad)
				.setTextAlignment(TextAlignment.CENTER);

		Text txtTituloReporte = new Text("TABLA DE EQUIVALENCIAS DE REVALIDACIÓN").setBold().setFont(font)
				.setFontSize(7);

		Paragraph prphTitulo = new Paragraph()
				.add(txtTituloReporte)
				.setBold()
				.setTextAlignment(TextAlignment.CENTER);

		

		Text lblIdentificacion = new Text("Identificacion: ").setBold().setFont(font).setFontSize(7);
		Text txtIdentificacion = new Text(convalidacion.getEntidadAlumno().getIdentificacionAlumno()).setFont(font).setFontSize(7);
		
		Text lblNombre = new Text("Nombres: ").setBold().setFont(font).setFontSize(7);
		Text txtNombre = new Text(convalidacion.getEntidadAlumno().getNombreCompletoAlumno()).setFont(font).setFontSize(7);
		
		Text lblDireccion = new Text("Direccion: ").setBold().setFont(font).setFontSize(7);
		Text txtDireccion = new Text(convalidacion.getEntidadAlumno().getDireccionAlumno()).setFont(font).setFontSize(7);
		
		Text lblUniveridad = new Text("Universidad: ").setBold().setFont(font).setFontSize(7);
		Text txtUniveridad = new Text(convalidacion.getEntidadAlumno().getUniversidadAlumno()).setFont(font).setFontSize(7);
		
		Text lblFecha= new Text("Fecha Convalidación: ").setBold().setFont(font).setFontSize(7);
		Text txtFecha = new Text(new SimpleDateFormat(" dd MMMMM yyyy ").format(convalidacion.getFechaRegistroConvalidacion())).setFont(font).setFontSize(7);

		Table table = new Table(2);
		table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
		
		Paragraph prphEtiquetaCabeceraTable = new Paragraph()
				.add(lblIdentificacion)
				.add(new Text("\n"))
				.add(lblNombre)
				.add(new Text("\n"))
				.add(lblDireccion)
				.add(new Text("\n"))
				.add(lblUniveridad)
				.add(new Text("\n"))
				.add(lblFecha)
				.setFixedLeading(10);
		
		Paragraph prphValorCabeceraTable = new Paragraph()
				.add(txtIdentificacion)
				.add(new Text("\n"))
				.add(txtNombre)
				.add(new Text("\n"))
				.add(txtDireccion)
				.add(new Text("\n"))
				.add(txtUniveridad)
				.add(new Text("\n"))
				.add(txtFecha)
				.setFixedLeading(10);
		
		table.addCell(new Cell().setWidth(new UnitValue(UnitValue.PERCENT, 20)).add(prphEtiquetaCabeceraTable).setBorder(Border.NO_BORDER));
		table.addCell(new Cell().setWidth(new UnitValue(UnitValue.PERCENT, 80)).add(prphValorCabeceraTable).setBorder(Border.NO_BORDER));		
		table.setNextRenderer(new TableBorderRenderer(table));
		
		Table tblConvalidacion = new Table(6);
		tblConvalidacion.setWidth(new UnitValue(UnitValue.PERCENT, 100));

		tblConvalidacion.addCell(new Cell().add(new Paragraph(convalidacion.getEntidadAlumno().getUniversidadAlumno().toUpperCase()).setBold().setFontSize(7).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 30)));
		tblConvalidacion.addCell(new Cell(1,5).add(new Paragraph("UNIVERSIDAD TÉCNICA PARTICULAR DE LOJA").setBold().setFontSize(7).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 70)));

		tblConvalidacion.addCell(new Cell().add(new Paragraph("MATERIA APROBADA").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 15)));
		tblConvalidacion.addCell(new Cell().add(new Paragraph("CÓDIGO").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 10)));
		tblConvalidacion.addCell(new Cell().add(new Paragraph("COMPONENTE ACADÉMICO").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 15)));
		tblConvalidacion.addCell(new Cell().add(new Paragraph("TIPO").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 15)));
		tblConvalidacion.addCell(new Cell().add(new Paragraph("CICLO").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 5)));
		tblConvalidacion.addCell(new Cell().add(new Paragraph("N° CRÉDITOS").setBold().setFontSize(6).setFont(font).setTextAlignment(TextAlignment.CENTER)).setWidth(new UnitValue(UnitValue.PERCENT, 5)));
	
		for (EntidadDetalleConvalidacion detalle : convalidacion.getLstDetalleConvalidacion()) {
			
			tblConvalidacion.addCell(new Cell().add(new Paragraph(detalle.getMateriaAprobada()).setFontSize(6).setFont(font)));
			if(Objects.isNull(detalle.getComponente())) {
				tblConvalidacion.addCell(new Cell().add(new Paragraph("")));
				tblConvalidacion.addCell(new Cell().add(new Paragraph("")));
				tblConvalidacion.addCell(new Cell().add(new Paragraph("")));
				tblConvalidacion.addCell(new Cell().add(new Paragraph("")));
				tblConvalidacion.addCell(new Cell().add(new Paragraph("")));
			}else {
				tblConvalidacion.addCell(new Cell().add(new Paragraph(" d").setFontSize(6).setFont(font)));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(detalle.getComponente().getNombreComponente()).setFontSize(6).setFont(font)));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(detalle.getComponente().getGrupoComponente())).setFontSize(6).setFont(font));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(detalle.getComponente().getNivelComponente().toString()).setFontSize(6).setFont(font)));
				tblConvalidacion.addCell(new Cell().add(new Paragraph(detalle.getComponente().getNumeroCreditoComponente().toString()).setFontSize(6).setFont(font)));
			}
			
			
		}
		Paragraph lblElaboradoPor = new Paragraph("\n\nElaborado Por: ").setBold().setFont(font).setFontSize(7);
		
		Text lblPointResponsable = new Text("_________________________________________________\n").setFont(font).setFontSize(7);
		Text lblNombreResponsable = new Text(convalidacion.getEntidadDocente().getNombreCompletoDocente() + "\n").setFont(font).setFontSize(7);
		Text lblTituloResponsable = new Text("Responsable de Convalidación").setBold().setFont(font).setFontSize(7);
		Paragraph prphFirma = new Paragraph()
				.add(new Text("\n"))
				.add(new Text("\n"))
				.add(new Text("\n"))
				.add(new Text("\n"))
				.add(new Text("\n"))
				.add(lblPointResponsable)
				.add(lblNombreResponsable)
				.add(lblTituloResponsable)
				.setFixedLeading(10)
				.setTextAlignment(TextAlignment.CENTER);
		
		document.add(prphCabecera);
		document.add(prphTitulo);
		document.add(table);
		document.add(new Paragraph("\n").setFixedLeading(10));
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
