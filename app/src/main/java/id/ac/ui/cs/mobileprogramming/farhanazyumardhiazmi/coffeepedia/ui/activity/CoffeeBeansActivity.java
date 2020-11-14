package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeBeansBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.CoffeeBeanListFragment;

public class CoffeeBeansActivity extends AppCompatActivity {

	private ActivityCoffeeBeansBinding mBinding;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityCoffeeBeansBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		if (savedInstanceState == null) {
			CoffeeBeanListFragment coffeeBeanListFragment = CoffeeBeanListFragment.newInstance();
			getSupportFragmentManager()
				.beginTransaction()
				.add(mBinding.fragmentContainer.getId(), coffeeBeanListFragment)
				.commit();
		}
	}
}
