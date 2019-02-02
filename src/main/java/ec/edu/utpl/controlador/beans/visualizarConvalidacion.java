package ec.edu.utpl.controlador.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.MalformedURLException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.web.context.annotation.SessionScope;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;

@Named
@SessionScope
public class visualizarConvalidacion extends Controlador<EntidadConvalidacion> implements Serializable {

	private static final long serialVersionUID = 1L;

	private StreamedContent file;

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public void cmmdBtnDescargarDocumento(ActionEvent ae) throws FileNotFoundException, MalformedURLException {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "skin"
				+ File.separator + "images" + File.separator + "logo-3.png";

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter;
		pdfWriter = new PdfWriter(outStream);
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);

		PageSize pagesize = PageSize.A4;
		Document document = new Document(pdfDoc, pagesize);

		/*Table table = new Table(new float[] { 1, 9 });
		Image img = new Image(ImageDataFactory.create(logo));
		table.addCell(img.setAutoScale(true));
		table.addCell("A light bulb icon");
		document.add(table);*/
		Image img = new Image(ImageDataFactory.create(logo));
		Paragraph p = new Paragraph();
		p.add(img.scale(new Float(0.2), new Float(0.2))).setTextAlignment(TextAlignment.CENTER);
		p.add("Text in the middle").setTextAlignment(TextAlignment.CENTER);
		document.add(p);
		document.close();

		setFile(new DefaultStreamedContent(new ByteArrayInputStream(outStream.toByteArray()), "application/pdf"));
	}
}
