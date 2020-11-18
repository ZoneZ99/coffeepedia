package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;

import java.util.ArrayList;
import java.util.List;

public class BrewRecipePdfExportable implements PdfExportable {

    List<BrewRecipe> brewRecipes;

    public BrewRecipePdfExportable(List<BrewRecipe> brewRecipes) { this.brewRecipes = brewRecipes; }

    @Override
    public List<String> getRepresentation() {
        List<String> representation = new ArrayList<>();
        for (BrewRecipe brewRecipe : brewRecipes) {
            representation.add(String.valueOf(brewRecipe.getBrewRecipeId()));
            representation.add(brewRecipe.getName());
            representation.add(brewRecipe.getRoastLevel());
            representation.add(brewRecipe.getGrindLevel());
            representation.add(brewRecipe.getWaterTemperature());
            representation.add(brewRecipe.getBrewTime());
            representation.add(brewRecipe.getBrewSteps());
        }
        return representation;
    }

    @Override
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Id");
        columnNames.add("Name");
        columnNames.add("Roast Level");
        columnNames.add("Grind Level");
        columnNames.add("Water Temperature");
        columnNames.add("Brew Time");
        columnNames.add("Brew Steps");
        return columnNames;
    }
}
