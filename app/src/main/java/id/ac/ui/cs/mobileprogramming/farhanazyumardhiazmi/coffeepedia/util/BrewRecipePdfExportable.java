package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrewRecipePdfExportable implements PdfExportable {

	List<BrewRecipe> brewRecipes;

	public native String[] getColumnNamesNative();

	static {
		System.loadLibrary("get-column-names");
	}

	public BrewRecipePdfExportable(List<BrewRecipe> brewRecipes) {
		this.brewRecipes = brewRecipes;
	}

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
		return new ArrayList<>(Arrays.asList(getColumnNamesNative()));
	}
}
