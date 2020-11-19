package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewRecipesBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider.PdfProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewRecipeDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewRecipeListFragment;

public class BrewRecipesActivity extends AppCompatActivity {

	private ActivityBrewRecipesBinding mBinding;

	private static final int EXPORT_BREW_RECIPE_ACTIVITY_REQUEST_CODE = 3;

	public static final String BREW_RECIPE = "brew_recipe";

	private long exportedBrewRecipeId;

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

	public void startBrewRecipeEditView(BrewRecipe brewRecipe) {
		Intent intent = new Intent(this, BrewRecipeFormActivity.class);
		intent.putExtra(BrewRecipeFormActivity.IS_EDIT, true);
		intent.putExtra(BREW_RECIPE, brewRecipe);
		startActivity(intent);
	}

	public void exportDataToPdf(long brewRecipeId, String filename) {
		exportedBrewRecipeId = brewRecipeId;
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");
		intent.putExtra(Intent.EXTRA_TITLE, filename);
		startActivityForResult(intent, EXPORT_BREW_RECIPE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == EXPORT_BREW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(PdfProvider.CONTENT_TYPE, PdfProvider.BREW_RECIPE);
			contentValues.put(PdfProvider.CONTENT_ID, exportedBrewRecipeId);
			contentValues.put(PdfProvider.URI_KEY, data.getDataString());
			getContentResolver().insert(PdfProvider.CONTENT_URI, contentValues);
			Toast.makeText(getApplicationContext(), R.string.success_export_pdf, Toast.LENGTH_SHORT).show();
		}
	}
}
