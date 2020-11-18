package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import java.util.List;

public interface PdfExportable {

	List<List<String>> getRepresentation();

	int getNumberOfColumns();

	List<String> getColumnNames();
}
