package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewMethodsBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewMethodDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewMethodListFragment;

public class BrewMethodsActivity extends AppCompatActivity {

	private ActivityBrewMethodsBinding mBinding;

	public static final String ADD = "add";

	public static final int ADD_BREW_METHOD_ACTIVITY_REQUEST_CODE = 1;

	public static final String EDIT = "edit";

	public static final int EDIT_BREW_METHOD_ACTIVITY_REQUEST_CODE = 2;

	public static final String BREW_METHOD = "brew_method";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityBrewMethodsBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		if (savedInstanceState == null) {
			showBrewMethodListFragment();
		}
	}

	public void showBrewMethodListFragment() {
		BrewMethodListFragment brewMethodListFragment = BrewMethodListFragment.newInstance();
		getSupportFragmentManager()
			.beginTransaction()
			.add(mBinding.fragmentContainer.getId(), brewMethodListFragment, BrewMethodListFragment.TAG)
			.commit();
	}

	public void showBrewMethodDetailFragment(BrewMethod brewMethod) {
		BrewMethodDetailFragment brewMethodDetailFragment = BrewMethodDetailFragment
			.forBrewMethod(brewMethod.getBrewMethodId());

		getSupportFragmentManager()
			.beginTransaction()
			.addToBackStack("brewMethod")
			.replace(mBinding.fragmentContainer.getId(), brewMethodDetailFragment, null)
			.commit();
	}
}
