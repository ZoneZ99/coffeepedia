package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao.BrewMethodDao;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao.BrewRecipeDao;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao.CoffeeBeanDao;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(
	entities = {
		CoffeeBean.class,
		BrewMethod.class,
		BrewRecipe.class
	},
	version = 1
)
public abstract class CoffeePediaDatabase extends RoomDatabase {

	public static final String DATABASE_NAME = "coffeepedia-db";

	public abstract CoffeeBeanDao coffeeBeanDao();

	public abstract BrewMethodDao brewMethodDao();

	public abstract BrewRecipeDao brewRecipeDao();

    private static CoffeePediaDatabase INSTANCE;

	private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

	public static synchronized CoffeePediaDatabase getInstance(final Context context) {
		if (INSTANCE == null) {
			INSTANCE = buildDatabase(context.getApplicationContext());
			INSTANCE.updateDatabaseCreated(context.getApplicationContext());
		}
		return INSTANCE;
	}

	private static CoffeePediaDatabase buildDatabase(final Context context) {
		return Room.databaseBuilder(context, CoffeePediaDatabase.class, DATABASE_NAME)
			.addCallback(new Callback() {
				@Override
				public void onCreate(@NonNull SupportSQLiteDatabase db) {
					super.onCreate(db);
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(() -> {
						CoffeePediaDatabase database = CoffeePediaDatabase.getInstance(context);
						List<CoffeeBean> coffeeBeansSeed = DataGenerator.generateCoffeeBeans();
						List<BrewMethod> brewMethodsSeed = DataGenerator.generateBrewMethods();
						seedData(database, coffeeBeansSeed, brewMethodsSeed);
						database.setDatabaseCreated();
					});
				}
			})
			.build();
	}

	private static void seedData(
		final CoffeePediaDatabase database,
		final List<CoffeeBean> coffeeBeans,
		final List<BrewMethod> brewMethods
	) {
		database.runInTransaction(() -> {
				coffeeBeans.forEach(coffeeBean -> database.coffeeBeanDao().insertCoffeeBean(coffeeBean));
				brewMethods.forEach(brewMethod -> database.brewMethodDao().insertBrewMethod(brewMethod));
			}
		);
	}

	private void updateDatabaseCreated(final Context context) {
		if (context.getDatabasePath(DATABASE_NAME).exists()) {
			setDatabaseCreated();
		}
	}

	private void setDatabaseCreated() {
		mIsDatabaseCreated.postValue(true);
	}

	public LiveData<Boolean> getDatabaseCreated() {
		return mIsDatabaseCreated;
	}
}
