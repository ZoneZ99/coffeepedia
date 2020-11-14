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

	private MediatorLiveData<List<CoffeeBean>> mObservableCoffeeBeans;

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

	public LiveData<CoffeeBean> getCoffeeBeanById(final int coffeeBeanId) {
		return mDatabase.coffeeBeanDao().getCoffeeBeanById(coffeeBeanId);
	}

	private void loadAllCoffeeBeans() {
		try {
			LoadAllAsync loadAllAsync = new LoadAllAsync(this);
			loadAllAsync.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				});
		}
	}
}
