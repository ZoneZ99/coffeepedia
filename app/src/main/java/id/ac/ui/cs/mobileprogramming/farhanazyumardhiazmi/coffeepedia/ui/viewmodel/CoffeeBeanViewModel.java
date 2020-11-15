package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;

public class CoffeeBeanViewModel extends AndroidViewModel {

	private final LiveData<CoffeeBean> mObservableCoffeeBean;

	private final long mCoffeeBeanId;

	public CoffeeBeanViewModel(
		@NonNull Application application,
		CoffeeBeanRepository coffeeBeanRepository,
		final long coffeeBeanId
	) {
		super(application);
		mCoffeeBeanId = coffeeBeanId;
		mObservableCoffeeBean = coffeeBeanRepository.getCoffeeBeanById(mCoffeeBeanId);
	}

	public LiveData<CoffeeBean> getCoffeeBean() {
		return mObservableCoffeeBean;
	}

	public static class Factory extends ViewModelProvider.NewInstanceFactory {

		@NonNull
		private final Application mApplication;

		private final long mCoffeeBeanId;

		private final CoffeeBeanRepository mCoffeeBeanRepository;

		public Factory(@NonNull Application application, long coffeeBeanId) {
			mApplication = application;
			mCoffeeBeanId = coffeeBeanId;
			mCoffeeBeanRepository = ((CoffeePediaApplication) application).getCoffeeBeanRepository();
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			return (T) new CoffeeBeanViewModel(mApplication, mCoffeeBeanRepository, mCoffeeBeanId);
		}
	}
}
