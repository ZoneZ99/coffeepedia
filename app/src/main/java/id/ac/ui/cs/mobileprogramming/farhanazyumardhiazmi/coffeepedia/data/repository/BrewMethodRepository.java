package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.relation.BrewMethodWithBrewRecipes;

import java.util.List;

public class BrewMethodRepository {

	private static BrewMethodRepository sIntance;

	private final CoffeePediaDatabase mDatabase;

	private MediatorLiveData<List<BrewMethod>> mObservableBrewMethods;

	private BrewMethodRepository(final CoffeePediaDatabase database) {
		mDatabase = database;
		mObservableBrewMethods = new MediatorLiveData<>();
		loadAllBrewMethods();
	}

	public static synchronized BrewMethodRepository getInstance(final CoffeePediaDatabase database) {
		if (sIntance == null) {
			sIntance = new BrewMethodRepository(database);
		}
		return sIntance;
	}

	public LiveData<List<BrewMethod>> getBrewMethods() {
		return mObservableBrewMethods;
	}

	public LiveData<BrewMethod> getBrewMethodById(final long brewMethodId) {
		return mDatabase.brewMethodDao().getBrewMethodById(brewMethodId);
	}

	public LiveData<BrewMethodWithBrewRecipes> getBrewMethodWithBrewRecipesByBrewMethodId(final long brewMethodId) {
		return mDatabase.brewMethodDao().getBrewMethodWithBrewRecipesByBrewMethodId(brewMethodId);
	}

	public void insertBrewMethod(BrewMethod brewMethod) {
		new InsertAsync(this).execute(brewMethod);
	}

	public void updateBrewMethod(BrewMethod brewMethod) {
		new UpdateAsync(this).execute(brewMethod);
	}

	public void deleteBrewMethod(BrewMethod brewMethod) {
		new DeleteAsync(this).execute(brewMethod);
	}

	private void loadAllBrewMethods() {
		new LoadAllAsync(this).execute();
	}

	private static class LoadAllAsync extends AsyncTask<Void, Void, LiveData<List<BrewMethod>>> {
		private final BrewMethodRepository brewMethodRepositoryAsync;

		LoadAllAsync(BrewMethodRepository brewMethodRepository) {
			brewMethodRepositoryAsync = brewMethodRepository;
		}

		@Override
		protected LiveData<List<BrewMethod>> doInBackground(Void... voids) {
			return brewMethodRepositoryAsync.mDatabase.brewMethodDao().loadAllBrewMethods();
		}

		@Override
		protected void onPostExecute(LiveData<List<BrewMethod>> listLiveData) {
			super.onPostExecute(listLiveData);
			brewMethodRepositoryAsync.mObservableBrewMethods.addSource(
				listLiveData,
				brewMethods -> {
					if (brewMethodRepositoryAsync.mDatabase.getDatabaseCreated().getValue() != null) {
						brewMethodRepositoryAsync.mObservableBrewMethods.postValue(brewMethods);
					}
				}
			);
		}
	}

	private static class InsertAsync extends AsyncTask<BrewMethod, Void, Void> {

		private final BrewMethodRepository brewMethodRepositoryAsync;

		InsertAsync(BrewMethodRepository brewMethodRepository) {
			brewMethodRepositoryAsync = brewMethodRepository;
		}

		@Override
		protected Void doInBackground(BrewMethod... brewMethods) {
			brewMethodRepositoryAsync.mDatabase.brewMethodDao().insertBrewMethod(brewMethods[0]);
			return null;
		}
	}

	private static class UpdateAsync extends AsyncTask<BrewMethod, Void, Void> {

		private final BrewMethodRepository brewMethodRepositoryAsync;

		UpdateAsync(BrewMethodRepository brewMethodRepository) {
			brewMethodRepositoryAsync = brewMethodRepository;
		}

		@Override
		protected Void doInBackground(BrewMethod... brewMethods) {
			brewMethodRepositoryAsync.mDatabase.brewMethodDao().updateBrewMethod(brewMethods[0]);
			return null;
		}
	}

	private static class DeleteAsync extends AsyncTask<BrewMethod, Void, Void> {

		private final BrewMethodRepository brewMethodRepositoryAsync;

		DeleteAsync(BrewMethodRepository brewMethodRepository) {
			brewMethodRepositoryAsync = brewMethodRepository;
		}

		@Override
		protected Void doInBackground(BrewMethod... brewMethods) {
			brewMethodRepositoryAsync.mDatabase.brewMethodDao().deleteBrewMethod(brewMethods[0]);
			return null;
		}
	}
}
