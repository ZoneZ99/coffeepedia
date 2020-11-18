package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.io.OutputStream;

public class PdfUtil {

	public void getDocument(PdfExportable pdfExportable, OutputStream outputStream) {
		try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream))) {
			Document document = null;
			document = new Document(pdfDocument);
			Table table = new Table(pdfExportable.getColumnNames().size());
			pdfExportable.getColumnNames().forEach(table::addCell);
			pdfExportable.getRepresentation().forEach(table::addCell);
			document.add(table);
			document.flush();
			document.close();
		}
	}
}
