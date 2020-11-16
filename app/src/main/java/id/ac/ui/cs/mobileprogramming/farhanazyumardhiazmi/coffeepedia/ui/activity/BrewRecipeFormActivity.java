package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewRecipeFormBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewMethodDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewRecipeFormViewModel;

public class BrewRecipeFormActivity extends AppCompatActivity {

	public static final String IS_EDIT = "is_edit";

	private ActivityBrewRecipeFormBinding mBinding;

	private BrewRecipeFormViewModel mViewModel;

	private long mRelatedBrewMethodId;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityBrewRecipeFormBinding.inflate(getLayoutInflater());
		mViewModel = new ViewModelProvider(this).get(BrewRecipeFormViewModel.class);
		View view = mBinding.getRoot();
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mRelatedBrewMethodId = bundle.getLong(BrewMethodDetailFragment.BREW_METHOD_ID);
			if (bundle.getBoolean(BrewRecipeFormActivity.IS_EDIT)) {

			} else {
				mBinding.titleBrewRecipeForm.setText(R.string.add_brew_recipe);
				mBinding.buttonBrewRecipeSubmit.setText(R.string.add);
				mBinding.buttonBrewRecipeSubmit.setOnClickListener(new AddBrewRecipe());
			}
		}
	}

	private class AddBrewRecipe implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				BrewRecipe brewRecipe = BrewRecipe
					.builder()
					.relatedBrewMethodId(mRelatedBrewMethodId)
					.name(mBinding.inputBrewRecipeName.getText().toString())
					.roastLevel(mBinding.inputBrewRecipeRoastLevel.getText().toString())
					.grindLevel(mBinding.inputBrewRecipeGrindLevel.getText().toString())
					.waterTemperature(mBinding.inputBrewRecipeWaterTemperature.getText().toString())
					.brewTime(mBinding.inputBrewRecipeBrewTime.getText().toString())
					.brewSteps(mBinding.inputBrewRecipeBrewSteps.getText().toString())
					.build();
				mViewModel.insertBrewRecipe(brewRecipe);

				Intent intent = new Intent(BrewRecipeFormActivity.this, BrewRecipesActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), R.string.form_invalid_text, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean isInputValid() {
		return !TextUtils.isEmpty(mBinding.inputBrewRecipeName.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewRecipeRoastLevel.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewRecipeGrindLevel.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewRecipeWaterTemperature.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewRecipeBrewTime.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewRecipeBrewSteps.getText());
	}
}
