package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.List;

public class CoffeeBeanRepository {

	private static CoffeeBeanRepository sInstance;

	private final CoffeePediaDatabase mDatabase;

	private final MediatorLiveData<List<CoffeeBean>> mObservableCoffeeBeans;

	private CoffeeBeanRepository(final CoffeePediaDatabase database) {
		mDatabase = database;
		mObservableCoffeeBeans = new MediatorLiveData<>();
		loadAllCoffeeBeans();
	}

	public static synchronized CoffeeBeanRepository getInstance(final CoffeePediaDatabase database) {
		if (sInstance == null) {
			sInstance = new CoffeeBeanRepository(database);
		}
		return sInstance;
	}

	public LiveData<List<CoffeeBean>> getCoffeeBeans() {
		return mObservableCoffeeBeans;
	}

	public LiveData<CoffeeBean> getCoffeeBeanById(final long coffeeBeanId) {
		return mDatabase.coffeeBeanDao().getCoffeeBeanById(coffeeBeanId);
	}

	public void insertCoffeeBean(CoffeeBean coffeeBean) {
		new InsertAsync(this).execute(coffeeBean);
	}

	public void updateCoffeeBean(CoffeeBean coffeeBean) {
		new UpdateAsync(this).execute(coffeeBean);
	}

	public void deleteCoffeeBean(CoffeeBean coffeeBean) {
		new DeleteAsync(this).execute(coffeeBean);
	}

	private void loadAllCoffeeBeans() {
		new LoadAllAsync(this).execute();
	}

	private static class LoadAllAsync extends AsyncTask<Void, Void, LiveData<List<CoffeeBean>>> {
		private final CoffeeBeanRepository coffeeBeanRepositoryAsync;

		LoadAllAsync(CoffeeBeanRepository coffeeBeanRepository) {
			coffeeBeanRepositoryAsync = coffeeBeanRepository;
		}

		@Override
		protected LiveData<List<CoffeeBean>> doInBackground(Void... voids) {
			return coffeeBeanRepositoryAsync.mDatabase.coffeeBeanDao().loadAllCoffeeBeans();
		}

		@Override
		protected void onPostExecute(LiveData<List<CoffeeBean>> listLiveData) {
			super.onPostExecute(listLiveData);
			coffeeBeanRepositoryAsync.mObservableCoffeeBeans.addSource(
				listLiveData,
				coffeeBeans -> {
					if (coffeeBeanRepositoryAsync.mDatabase.getDatabaseCreated().getValue() != null) {
						coffeeBeanRepositoryAsync.mObservableCoffeeBeans.postValue(coffeeBeans);
					}
				}
			);
		}
	}

	private static class InsertAsync extends AsyncTask<CoffeeBean, Void, Void> {

		private final CoffeeBeanRepository coffeeBeanRepositoryAsync;

		InsertAsync(CoffeeBeanRepository coffeeBeanRepository) {
			coffeeBeanRepositoryAsync = coffeeBeanRepository;
		}

		@Override
		protected Void doInBackground(CoffeeBean... coffeeBeans) {
			coffeeBeanRepositoryAsync.mDatabase.coffeeBeanDao().insertCoffeeBean(coffeeBeans[0]);
			return null;
		}
	}

	private static class UpdateAsync extends AsyncTask<CoffeeBean, Void, Void> {

		private final CoffeeBeanRepository coffeeBeanRepositoryAsync;

		UpdateAsync(CoffeeBeanRepository coffeeBeanRepository) {
			coffeeBeanRepositoryAsync = coffeeBeanRepository;
		}

		@Override
		protected Void doInBackground(CoffeeBean... coffeeBeans) {
			coffeeBeanRepositoryAsync.mDatabase.coffeeBeanDao().updateCoffeeBean(coffeeBeans[0]);
			return null;
		}
	}

	private static class DeleteAsync extends AsyncTask<CoffeeBean, Void, Void> {

		private final CoffeeBeanRepository coffeeBeanRepositoryAsync;

		DeleteAsync(CoffeeBeanRepository coffeeBeanRepository) {
			coffeeBeanRepositoryAsync = coffeeBeanRepository;
		}

		@Override
		protected Void doInBackground(CoffeeBean... coffeeBeans) {
			coffeeBeanRepositoryAsync.mDatabase.coffeeBeanDao().deleteCoffeeBean(coffeeBeans[0]);
			return null;
		}
	}
}
