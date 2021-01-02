package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;

public class CoffeeBeanFormViewModel extends AndroidViewModel {

	private final MutableLiveData<CoffeeBean> mObservableCoffeeBean;

	private final CoffeeBeanRepository mCoffeeBeanRepository;

	public CoffeeBeanFormViewModel(@NonNull Application application) {
		super(application);
		mObservableCoffeeBean = new MutableLiveData<>();
		mCoffeeBeanRepository = ((CoffeePediaApplication) application).getCoffeeBeanRepository();
	}

	public LiveData<CoffeeBean> getCoffeeBean() {
		return mObservableCoffeeBean;
	}

	public void setCoffeeBean(CoffeeBean coffeeBean) {
		mObservableCoffeeBean.setValue(coffeeBean);
	}

	public void insertCoffeeBean(CoffeeBean coffeeBean) {
		mCoffeeBeanRepository.insertCoffeeBean(coffeeBean);
	}

	public void updateCoffeeBean(CoffeeBean coffeeBean) {
		mCoffeeBeanRepository.updateCoffeeBean(coffeeBean);
	}
}
