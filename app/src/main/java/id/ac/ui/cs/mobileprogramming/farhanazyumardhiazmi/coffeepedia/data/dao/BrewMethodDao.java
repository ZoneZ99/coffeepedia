package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.relation.BrewMethodWithBrewRecipes;

import java.util.List;

@Dao
public interface BrewMethodDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long insertBrewMethod(BrewMethod brewMethod);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateBrewMethod(BrewMethod brewMethod);

	@Delete
	void deleteBrewMethod(BrewMethod brewMethod);

	@Query("SELECT * FROM brew_methods")
	LiveData<List<BrewMethod>> loadAllBrewMethods();

	@Transaction
	@Query("SELECT * FROM brew_methods WHERE brewMethodId = :brewMethodId")
	LiveData<List<BrewMethodWithBrewRecipes>> getBrewMethodWithBrewRecipesByBrewMethodId(long brewMethodId);

	@Query("SELECT * FROM brew_methods WHERE brewMethodId = :brewMethodId")
	LiveData<BrewMethod> getBrewMethodById(long brewMethodId);
}
