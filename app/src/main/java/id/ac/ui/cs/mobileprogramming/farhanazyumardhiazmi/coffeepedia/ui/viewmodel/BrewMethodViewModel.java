package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.relation.BrewMethodWithBrewRecipes;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewMethodRepository;

import java.util.List;

public class BrewMethodViewModel extends AndroidViewModel {

	private final LiveData<BrewMethodWithBrewRecipes> mObservableBrewMethod;

	private final BrewMethodRepository mBrewMethodRepository;

	public BrewMethodViewModel(
		@NonNull Application application,
		BrewMethodRepository brewMethodRepository,
		final long brewMethodId
	) {
		super(application);
		mBrewMethodRepository = brewMethodRepository;
		mObservableBrewMethod = brewMethodRepository.getBrewMethodWithBrewRecipesByBrewMethodId(brewMethodId);
	}

	public LiveData<BrewMethodWithBrewRecipes> getBrewMethodWithBrewRecipes() { return mObservableBrewMethod; }

	public void deleteBrewMethod(BrewMethod deletedBrewMethod) {
		mBrewMethodRepository.deleteBrewMethod(deletedBrewMethod);
	}

	public static class Factory extends ViewModelProvider.NewInstanceFactory {

		@NonNull
		private final Application mApplication;

		private final long mBrewMethodId;

		private final BrewMethodRepository mBrewMethodRepository;

		public Factory(@NonNull Application application, long brewMethodId) {
			mApplication = application;
			mBrewMethodId = brewMethodId;
			mBrewMethodRepository = ((CoffeePediaApplication) application).getBrewMethodRepository();
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			return (T) new BrewMethodViewModel(mApplication, mBrewMethodRepository, mBrewMethodId);
		}
	}
}
