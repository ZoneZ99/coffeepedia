package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.List;

public class CoffeeBeanWithBrewMethods {

	@Embedded public CoffeeBean coffeeBean;
	@Relation(
		parentColumn = "coffeeBeanId",
		entityColumn = "relatedCoffeeBeanId"
	)
	public List<BrewMethod> brewMethods;
}
