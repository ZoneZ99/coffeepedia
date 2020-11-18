package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;

import java.util.List;

@Dao
public interface CoffeeBeanDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	long insertCoffeeBean(CoffeeBean coffeeBean);

	@Update(onConflict = OnConflictStrategy.REPLACE)
	void updateCoffeeBean(CoffeeBean coffeeBean);

	@Delete
	void deleteCoffeeBean(CoffeeBean coffeeBean);

	@Query("SELECT * FROM coffee_beans")
	LiveData<List<CoffeeBean>> loadAllCoffeeBeans();

	@Query("SELECT * FROM coffee_beans")
	List<CoffeeBean> loadAllCoffeeBeansSync();

	@Query("SELECT * FROM coffee_beans WHERE coffeeBeanId = :coffeeBeanId")
	LiveData<CoffeeBean> getCoffeeBeanById(long coffeeBeanId);

	@Query("SELECT * FROM coffee_beans WHERE coffeeBeanId = :coffeeBeanId")
	CoffeeBean getCoffeeBeanByIdSync(long coffeeBeanId);
}
