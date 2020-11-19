package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewRecipeRepository;

public class BrewRecipeViewModel extends AndroidViewModel {

	private final LiveData<BrewRecipe> mObservableBrewRecipe;

	private final BrewRecipeRepository mBrewRecipeRepository;

	public BrewRecipeViewModel(
		@NonNull Application application,
		BrewRecipeRepository brewRecipeRepository,
		final long brewRecipeId
	) {
		super(application);
		mBrewRecipeRepository = brewRecipeRepository;
		mObservableBrewRecipe = brewRecipeRepository.getBrewRecipeById(brewRecipeId);
	}

	public LiveData<BrewRecipe> getBrewRecipe() {
		return mObservableBrewRecipe;
	}

	public void deleteBrewRecipe(BrewRecipe brewRecipe) {
		mBrewRecipeRepository.deleteBrewRecipe(brewRecipe);
	}

	public static class Factory extends ViewModelProvider.NewInstanceFactory {

		@NonNull
		private final Application mApplication;

		private final long mBrewRecipeId;

		private final BrewRecipeRepository mBrewRecipeRepository;

		public Factory(@NonNull Application application, long brewRecipeId) {
			mApplication = application;
			mBrewRecipeId = brewRecipeId;
			mBrewRecipeRepository = ((CoffeePediaApplication) application).getBrewRecipeRepository();
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			return (T) new BrewRecipeViewModel(mApplication, mBrewRecipeRepository, mBrewRecipeId);
		}
	}
}
