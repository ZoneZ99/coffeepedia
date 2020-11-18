package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import android.util.Log;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public class PdfUtil {

	public void getDocument(String title, PdfExportable pdfExportable, OutputStream outputStream) {
		Document document = null;
		try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream))) {
			document = new Document(pdfDocument);
			int numberOfColumns = pdfExportable.getNumberOfColumns();
			Table table = new Table(numberOfColumns);

			pdfExportable.getColumnNames().forEach(table::addCell);
			pdfExportable.getRepresentation().forEach(row -> row.forEach(table::addCell));

			document.add(table);
			document.flush();
			document.close();
		}
	}
}
