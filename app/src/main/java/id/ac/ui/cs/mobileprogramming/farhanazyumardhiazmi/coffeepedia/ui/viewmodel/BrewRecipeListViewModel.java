package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewRecipeRepository;

import java.util.List;

public class BrewRecipeListViewModel extends AndroidViewModel {

	private final BrewRecipeRepository mRepository;

	private final LiveData<List<BrewRecipe>> mBrewRecipes;

	public BrewRecipeListViewModel(@NonNull Application application) {
		super(application);
		mRepository = ((CoffeePediaApplication) application).getBrewRecipeRepository();
		mBrewRecipes = mRepository.getBrewRecipes();
	}

	public LiveData<List<BrewRecipe>> getBrewRecipes() { return mBrewRecipes; }
}
