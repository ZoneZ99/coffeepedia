package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewMethodRepository;

public class BrewMethodFormViewModel extends AndroidViewModel {

	private final MutableLiveData<BrewMethod> mObservableBrewMethod;

	private final BrewMethodRepository mBrewMethodRepository;

	public BrewMethodFormViewModel(@NonNull Application application) {
		super(application);
		mObservableBrewMethod = new MutableLiveData<>();
		mBrewMethodRepository = ((CoffeePediaApplication) application).getBrewMethodRepository();
	}

	public LiveData<BrewMethod> getBrewMethod() {
		return mObservableBrewMethod;
	}

	public void setBrewMethod(BrewMethod brewMethod) {
		mObservableBrewMethod.setValue(brewMethod);
	}

	public void insertBrewMethod(BrewMethod brewMethod) {
		mBrewMethodRepository.insertBrewMethod(brewMethod);
	}

	public void updateBrewMethod(BrewMethod brewMethod) {
		mBrewMethodRepository.updateBrewMethod(brewMethod);
	}
}
