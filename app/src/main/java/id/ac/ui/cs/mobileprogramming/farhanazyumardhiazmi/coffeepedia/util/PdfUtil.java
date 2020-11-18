package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfUtil {

	public void create(String title, List<PdfExportable> content, int numOfColumn, String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filePath));
		Document document = new Document(pdfDocument);

		Table table = new Table(numOfColumn);
	}

}
