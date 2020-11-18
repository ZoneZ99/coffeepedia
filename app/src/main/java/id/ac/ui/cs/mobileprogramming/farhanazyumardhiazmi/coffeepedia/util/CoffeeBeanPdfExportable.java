package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.ArrayList;
import java.util.List;

public class CoffeeBeanPdfExportable implements PdfExportable {

    List<CoffeeBean> coffeeBeans;

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
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Id");
        columnNames.add("Name");
        columnNames.add("Type");
        columnNames.add("Origin");
        columnNames.add("Altitude");
        columnNames.add("Process");
        columnNames.add("Aroma");
        columnNames.add("Taste Note");
        return columnNames;
    }
}
