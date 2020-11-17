package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data;

import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

	public static List<CoffeeBean> generateCoffeeBeans() {
		List<CoffeeBean> coffeeBeans = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			CoffeeBean coffeeBean = CoffeeBean
				.builder()
				.name(String.valueOf(i))
				.altitude("A")
				.aroma("A")
				.origin("A")
				.process("A")
				.tasteNote("A")
				.type("A")
				.build();
			coffeeBeans.add(coffeeBean);
		}
		return coffeeBeans;
	}

	public static List<BrewMethod> generateBrewMethods() {
		List<BrewMethod> brewMethods = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			BrewMethod brewMethod = BrewMethod
				.builder()
				.name(String.valueOf(i))
				.description("A")
				.build();
			brewMethods.add(brewMethod);
		}
		return brewMethods;
	}
}
