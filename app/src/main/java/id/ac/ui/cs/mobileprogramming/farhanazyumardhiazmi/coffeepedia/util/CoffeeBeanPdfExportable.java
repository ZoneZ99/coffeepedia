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
    public List<List<String>> getRepresentation() {
        List<List<String>> representation = new ArrayList<>();
        for (CoffeeBean coffeeBean : coffeeBeans) {
            List<String> rowValues = new ArrayList<>();
            for (int i = 0; i < getNumberOfColumns(); i++) {
                rowValues.add(coffeeBean.getName());
                rowValues.add(coffeeBean.getType());
                rowValues.add(coffeeBean.getAltitude());
                rowValues.add(coffeeBean.getProcess());
                rowValues.add(coffeeBean.getAroma());
                rowValues.add(coffeeBean.getTasteNote());
            }
            representation.add(rowValues);
        }
        return representation;
    }

    @Override
    public int getNumberOfColumns() {
        return CoffeeBean.class.getDeclaredFields().length;
    }

    @Override
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
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
