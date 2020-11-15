package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewMethodRepository;

import java.util.List;

public class BrewMethodListViewModel extends AndroidViewModel {

	private final BrewMethodRepository mRepository;

	private final LiveData<List<BrewMethod>> mBrewMethods;

	public BrewMethodListViewModel(@NonNull Application application) {
		super(application);
		mRepository = ((CoffeePediaApplication) application).getBrewMethodRepository();
		mBrewMethods = mRepository.getBrewMethods();
	}

	public LiveData<List<BrewMethod>> getBrewMethods() {
		return mBrewMethods;
	}
}
