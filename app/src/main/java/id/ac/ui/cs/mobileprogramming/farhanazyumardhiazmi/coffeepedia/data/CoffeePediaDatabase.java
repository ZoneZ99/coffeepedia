package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data;

import android.content.Context;
import androidx.annotation.NonNull;
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

import java.util.concurrent.ExecutorService;
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

	public abstract CoffeeBeanDao coffeeBeanDao();

	public abstract BrewMethodDao brewMethodDao();

	public abstract BrewRecipeDao brewRecipeDao();

	private static volatile CoffeePediaDatabase INSTANCE;

	private static final int NUMBER_OF_THREADS = 4;

	static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static CoffeePediaDatabase getInstance(final Context context) {
		if (INSTANCE == null) {
			synchronized (CoffeePediaDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(
						context.getApplicationContext(),
						CoffeePediaDatabase.class,
						"coffeepedia.db")
						.addCallback(sCoffeePediaDatabaseCallback)
						.build();
				}
			}
		}
		return INSTANCE;
	}

	private static RoomDatabase.Callback sCoffeePediaDatabaseCallback = new RoomDatabase.Callback() {
		@Override
		public void onCreate(@NonNull SupportSQLiteDatabase db) {
			super.onCreate(db);
		}
	};
}
