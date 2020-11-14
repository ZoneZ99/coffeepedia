package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia;

import android.app.Application;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;

public class CoffeePediaApplication extends Application {

	private ApplicationExecutors mAppExecutors;

	@Override
	public void onCreate() {
		super.onCreate();
		mAppExecutors = new ApplicationExecutors();
	}

	public CoffeePediaDatabase getDatabase() {
		return CoffeePediaDatabase.getInstance(this, mAppExecutors);
	}

	public CoffeeBeanRepository getCoffeeBeanRepository() {
		return CoffeeBeanRepository.getInstance(getDatabase());
	}
}
