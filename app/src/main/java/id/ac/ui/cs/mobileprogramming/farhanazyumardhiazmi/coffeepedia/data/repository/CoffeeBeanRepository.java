package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao.CoffeeBeanDao;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.List;

public class CoffeeBeanRepository {

	private static CoffeeBeanRepository sInstance;

	private final CoffeePediaDatabase mDatabase;

	private CoffeeBeanDao mCoffeeBeanDao;

	private MediatorLiveData<List<CoffeeBean>> mObservableCoffeeBeans;

	private CoffeeBeanRepository(final CoffeePediaDatabase database) {
		mDatabase = database;
		mObservableCoffeeBeans = new MediatorLiveData<>();
		mObservableCoffeeBeans.addSource(
			mDatabase.coffeeBeanDao().loadAllCoffeeBeans(),
			coffeeBeans -> {
				if (mDatabase.getDatabaseCreated().getValue() != null) {
					mObservableCoffeeBeans.postValue(coffeeBeans);
				}
			});
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
}
