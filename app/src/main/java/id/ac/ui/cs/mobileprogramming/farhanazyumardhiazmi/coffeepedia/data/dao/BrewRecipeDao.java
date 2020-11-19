package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;

import java.util.List;

@Dao
public interface BrewRecipeDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertBrewRecipe(BrewRecipe brewRecipe);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateBrewRecipe(BrewRecipe brewRecipe);

	@Delete
	void deleteBrewRecipe(BrewRecipe brewRecipe);

	@Query("SELECT * FROM brew_recipes")
	LiveData<List<BrewRecipe>> loadAllBrewRecipes();

	@Query("SELECT * FROM brew_recipes WHERE brewRecipeId = :brewRecipeId")
	LiveData<BrewRecipe> getBrewRecipeById(long brewRecipeId);
}
