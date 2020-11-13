package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;

import java.util.List;

public class BrewMethodWithBrewRecipes {

	@Embedded public BrewMethod brewMethod;
	@Relation(
		parentColumn = "brewMethodId",
		entityColumn = "relatedBrewMethodId"
	)
	public List<BrewRecipe> brewRecipes;
}
