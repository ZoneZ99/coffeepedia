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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewMethodFormBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewMethodFormViewModel;

public class BrewMethodFormActivity extends AppCompatActivity {

	private ActivityBrewMethodFormBinding mBinding;

	private BrewMethodFormViewModel mViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityBrewMethodFormBinding.inflate(getLayoutInflater());
		mViewModel = new ViewModelProvider(this).get(BrewMethodFormViewModel.class);
		View view = mBinding.getRoot();
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean(BrewMethodsActivity.IS_EDIT)) {
				BrewMethod brewMethod = bundle.getParcelable(BrewMethodsActivity.BREW_METHOD);
				if (brewMethod != null) {
					mViewModel.setBrewMethod(brewMethod);
					mViewModel.getBrewMethod().observe(this, method -> {
						mBinding.inputBrewMethodName.setText(method.getName());
						mBinding.inputBrewMethodDescription.setText(method.getDescription());
					});
				}
				mBinding.titleBrewMethodForm.setText(R.string.edit_brew_method);
				mBinding.buttonBrewMethodSubmit.setText(R.string.edit);
				mBinding.buttonBrewMethodSubmit.setOnClickListener(new EditBrewMethod());
			} else {
				mBinding.titleBrewMethodForm.setText(R.string.add_brew_method);
				mBinding.buttonBrewMethodSubmit.setText(R.string.add);
				mBinding.buttonBrewMethodSubmit.setOnClickListener(new AddBrewMethod());
			}
		}
	}

	private class AddBrewMethod implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				BrewMethod brewMethod = BrewMethod
					.builder()
					.name(mBinding.inputBrewMethodName.getText().toString())
					.description(mBinding.inputBrewMethodDescription.getText().toString())
					.build();
				mViewModel.insertBrewMethod(brewMethod);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), R.string.form_invalid_text, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class EditBrewMethod implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				BrewMethod brewMethod = mViewModel.getBrewMethod().getValue();
				brewMethod.setName(mBinding.inputBrewMethodName.getText().toString());
				brewMethod.setDescription(mBinding.inputBrewMethodDescription.getText().toString());
				mViewModel.updateBrewMethod(brewMethod);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), R.string.form_invalid_text, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean isInputValid() {
		return !TextUtils.isEmpty(mBinding.inputBrewMethodName.getText())
			&& !TextUtils.isEmpty(mBinding.inputBrewMethodDescription.getText());
 	}
}
