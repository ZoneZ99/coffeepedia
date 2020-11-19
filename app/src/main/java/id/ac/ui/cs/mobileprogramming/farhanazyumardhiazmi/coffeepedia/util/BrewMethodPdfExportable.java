package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;

import java.util.ArrayList;
import java.util.List;

public class BrewMethodPdfExportable implements PdfExportable {

	List<BrewMethod> brewMethods;

	public BrewMethodPdfExportable(List<BrewMethod> brewMethods) {
		this.brewMethods = brewMethods;
	}

	@Override
	public List<String> getRepresentation() {
		List<String> representation = new ArrayList<>();
		for (BrewMethod brewMethod : brewMethods) {
			representation.add(String.valueOf(brewMethod.getBrewMethodId()));
			representation.add(String.valueOf(brewMethod.getName()));
			representation.add(String.valueOf(brewMethod.getDescription()));
		}
		return representation;
	}

	@Override
	public List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<>();
		columnNames.add("Id");
		columnNames.add("Name");
		columnNames.add("Description");
		return columnNames;
	}
}
