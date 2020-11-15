package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeBeansBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.CoffeeBeanDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.CoffeeBeanListFragment;

public class CoffeeBeansActivity extends AppCompatActivity {

	private ActivityCoffeeBeansBinding mBinding;

	public static final String ADD = "add";

	public static final int ADD_COFFEE_BEAN_ACTVITY_REQUEST_CODE = 1;

	public static final String EDIT = "edit";

	public static final int EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE = 2;

	public static final String COFFEE_BEAN = "coffee_bean";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityCoffeeBeansBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		if (savedInstanceState == null) {
			showCoffeeBeanListFragment();
		}
	}

	public void showCoffeeBeanListFragment() {
		CoffeeBeanListFragment coffeeBeanListFragment = CoffeeBeanListFragment.newInstance();
		getSupportFragmentManager()
			.beginTransaction()
			.add(mBinding.fragmentContainer.getId(), coffeeBeanListFragment, CoffeeBeanListFragment.TAG)
			.commit();
	}

	public void showCoffeeBeanDetailFragment(CoffeeBean coffeeBean) {

		CoffeeBeanDetailFragment coffeeBeanDetailFragment = CoffeeBeanDetailFragment
			.forCoffeeBean(coffeeBean.getCoffeeBeanId());

		getSupportFragmentManager()
			.beginTransaction()
			.addToBackStack("cofeeBean")
			.replace(mBinding.fragmentContainer.getId(), coffeeBeanDetailFragment, null)
			.commit();
	}

	public void startCoffeeBeanAddView(View view) {
		Intent intent = new Intent(this, CoffeeBeanEditorActivity.class);
		intent.putExtra(ADD, true);
		intent.putExtra(EDIT, false);
		startActivityForResult(intent, ADD_COFFEE_BEAN_ACTVITY_REQUEST_CODE);
	}

	public void startCoffeeBeanEditView(CoffeeBean coffeeBean) {
		Intent intent = new Intent(this, CoffeeBeanEditorActivity.class);
		intent.putExtra(ADD, false);
		intent.putExtra(EDIT, true);
		intent.putExtra(COFFEE_BEAN, coffeeBean);
		startActivityForResult(intent, EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ADD_COFFEE_BEAN_ACTVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_add_coffee_bean, Toast.LENGTH_LONG).show();
		} else if (requestCode == EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_edit_coffee_bean, Toast.LENGTH_LONG).show();
		}
	}
}
