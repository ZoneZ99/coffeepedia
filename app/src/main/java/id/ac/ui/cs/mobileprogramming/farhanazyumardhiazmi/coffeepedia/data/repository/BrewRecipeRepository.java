package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;

import java.util.List;

public class BrewRecipeRepository {

	private static BrewRecipeRepository sIntance;

	private final CoffeePediaDatabase mDatabase;

	private MediatorLiveData<List<BrewRecipe>> mObservableBrewRecipes;

	private BrewRecipeRepository(final CoffeePediaDatabase database) {
		mDatabase = database;
		mObservableBrewRecipes = new MediatorLiveData<>();
		loadAllBrewRecipes();
	}

	public static synchronized BrewRecipeRepository getInstance(final CoffeePediaDatabase database) {
		if (sIntance == null) {
			sIntance = new BrewRecipeRepository(database);
		}
		return sIntance;
	}

	public LiveData<List<BrewRecipe>> getBrewRecipes() {
		return mObservableBrewRecipes;
	}

	public LiveData<BrewRecipe> getBrewRecipeById(final long brewRecipeId) {
		return mDatabase.brewRecipeDao().getBrewRecipeById(brewRecipeId);
	}

	public void insertBrewRecipe(BrewRecipe brewRecipe) {
		new InsertAsync(this).execute(brewRecipe);
	}

	public void updateBrewRecipe(BrewRecipe brewRecipe) {
		new UpdateAsync(this).execute(brewRecipe);
	}

	public void deleteBrewRecipe(BrewRecipe brewRecipe) {
		new DeleteAsync(this).execute(brewRecipe);
	}

	private void loadAllBrewRecipes() {
		new LoadAllAsync(this).execute();
	}

	private static class LoadAllAsync extends AsyncTask<Void, Void, LiveData<List<BrewRecipe>>> {
		private final BrewRecipeRepository brewRecipeRepositoryAsync;

		LoadAllAsync(BrewRecipeRepository brewRecipeRepository) { brewRecipeRepositoryAsync = brewRecipeRepository; }

		@Override
		protected LiveData<List<BrewRecipe>> doInBackground(Void... voids) {
			return brewRecipeRepositoryAsync.mDatabase.brewRecipeDao().loadAllBrewRecipes();
		}

		@Override
		protected void onPostExecute(LiveData<List<BrewRecipe>> listLiveData) {
			super.onPostExecute(listLiveData);
			brewRecipeRepositoryAsync.mObservableBrewRecipes.addSource(
				listLiveData,
				brewRecipes -> {
					if (brewRecipeRepositoryAsync.mDatabase.getDatabaseCreated().getValue() != null) {
						brewRecipeRepositoryAsync.mObservableBrewRecipes.postValue(brewRecipes);
					}
				}
			);
		}
	}

	private static class InsertAsync extends AsyncTask<BrewRecipe, Void, Void> {

		private final BrewRecipeRepository brewRecipeRepositoryAsync;

		InsertAsync(BrewRecipeRepository brewRecipeRepository) { brewRecipeRepositoryAsync = brewRecipeRepository; }

		@Override
		protected Void doInBackground(BrewRecipe... brewRecipes) {
			brewRecipeRepositoryAsync.mDatabase.brewRecipeDao().insertBrewRecipe(brewRecipes[0]);
			return null;
		}
	}

	private static class UpdateAsync extends AsyncTask<BrewRecipe, Void, Void> {

		private final BrewRecipeRepository brewRecipeRepositoryAsync;

		UpdateAsync(BrewRecipeRepository brewRecipeRepository) { brewRecipeRepositoryAsync = brewRecipeRepository; }

		@Override
		protected Void doInBackground(BrewRecipe... brewRecipes) {
			brewRecipeRepositoryAsync.mDatabase.brewRecipeDao().updateBrewRecipe(brewRecipes[0]);
			return null;
		}
	}

	private static class DeleteAsync extends AsyncTask<BrewRecipe, Void, Void> {

		private final BrewRecipeRepository brewRecipeRepositoryAsync;

		DeleteAsync(BrewRecipeRepository brewRecipeRepository) { brewRecipeRepositoryAsync = brewRecipeRepository; }

		@Override
		protected Void doInBackground(BrewRecipe... brewRecipes) {
			brewRecipeRepositoryAsync.mDatabase.brewRecipeDao().deleteBrewRecipe(brewRecipes[0]);
			return null;
		}
	}
}
