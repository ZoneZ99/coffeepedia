package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewRecipesBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewRecipeDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewRecipeListFragment;

public class BrewRecipesActivity extends AppCompatActivity {

	private ActivityBrewRecipesBinding mBinding;

	public static final String IS_EDIT = "is_edit";

	public static final int ADD_BREW_RECIPE_ACTIVITY_REQUEST_CODE = 1;

	public static final int EDIT_BREW_RECIPE_ACTIVITY_REQUEST_CODE = 2;

	public static final String BREW_RECIPE = "brew_recipe";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityBrewRecipesBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		if (savedInstanceState == null) {
			showBrewRecipeListFragment();
		}
	}

	public void showBrewRecipeListFragment() {
		BrewRecipeListFragment brewRecipeListFragment = BrewRecipeListFragment.newInstance();
		getSupportFragmentManager()
			.beginTransaction()
			.add(mBinding.fragmentContainer.getId(), brewRecipeListFragment, BrewRecipeListFragment.TAG)
			.commit();
	}

	public void showBrewRecipeDetailFragment(BrewRecipe brewRecipe) {
		BrewRecipeDetailFragment brewRecipeDetailFragment = BrewRecipeDetailFragment
			.forBrewRecipe(brewRecipe.getBrewRecipeId());

		getSupportFragmentManager()
			.beginTransaction()
			.addToBackStack("brewRecipe")
			.replace(mBinding.fragmentContainer.getId(), brewRecipeDetailFragment, null)
			.commit();
	}
}
