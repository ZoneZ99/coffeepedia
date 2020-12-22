package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoffeeBeanPdfExportable implements PdfExportable {

	List<CoffeeBean> coffeeBeans;

	public native String[] getColumnNamesNative();

	static {
		System.loadLibrary("get-column-names");
	}

	public CoffeeBeanPdfExportable(List<CoffeeBean> coffeeBeans) {
		this.coffeeBeans = coffeeBeans;
	}

	@Override
	public List<String> getRepresentation() {
		List<String> representation = new ArrayList<>();
		for (CoffeeBean coffeeBean : coffeeBeans) {
			representation.add(String.valueOf(coffeeBean.getCoffeeBeanId()));
			representation.add(coffeeBean.getName());
			representation.add(coffeeBean.getType());
			representation.add(coffeeBean.getOrigin());
			representation.add(coffeeBean.getAltitude());
			representation.add(coffeeBean.getProcess());
			representation.add(coffeeBean.getAroma());
			representation.add(coffeeBean.getTasteNote());
		}
		return representation;
	}

	@Override
	public List<String> getColumnNames() {
		return new ArrayList<>(Arrays.asList(getColumnNamesNative()));
	}
}
