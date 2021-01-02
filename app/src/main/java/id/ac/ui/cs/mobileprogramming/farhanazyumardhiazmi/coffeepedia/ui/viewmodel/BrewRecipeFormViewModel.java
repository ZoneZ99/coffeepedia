package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewRecipeRepository;

public class BrewRecipeFormViewModel extends AndroidViewModel {

	private final MutableLiveData<BrewRecipe> mObservableBrewRecipe;

	private final BrewRecipeRepository mBrewRecipeRepository;

	public BrewRecipeFormViewModel(@NonNull Application application) {
		super(application);
		mObservableBrewRecipe = new MutableLiveData<>();
		mBrewRecipeRepository = ((CoffeePediaApplication) application).getBrewRecipeRepository();
	}

	public void insertBrewRecipe(BrewRecipe brewRecipe) {
		mBrewRecipeRepository.insertBrewRecipe(brewRecipe);
	}

	public void setBrewRecipe(BrewRecipe brewRecipe) {
		mObservableBrewRecipe.setValue(brewRecipe);
	}

	public LiveData<BrewRecipe> getBrewRecipe() {
		return mObservableBrewRecipe;
	}

	public void updateBrewRecipe(BrewRecipe brewRecipe) {
		mBrewRecipeRepository.updateBrewRecipe(brewRecipe);
	}
}
