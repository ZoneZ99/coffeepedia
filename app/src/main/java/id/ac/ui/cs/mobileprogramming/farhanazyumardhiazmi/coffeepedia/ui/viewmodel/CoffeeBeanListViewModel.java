package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;

import java.util.List;

public class CoffeeBeanListViewModel extends AndroidViewModel {

	private final CoffeeBeanRepository mRepository;

	private final LiveData<List<CoffeeBean>> mCoffeeBeans;

	public CoffeeBeanListViewModel(@NonNull Application application) {
		super(application);
		mRepository = ((CoffeePediaApplication) application).getCoffeeBeanRepository();
		mCoffeeBeans = mRepository.getCoffeeBeans();
	}

	public LiveData<List<CoffeeBean>> getCoffeeBeans() {
		return mCoffeeBeans;
	}
}