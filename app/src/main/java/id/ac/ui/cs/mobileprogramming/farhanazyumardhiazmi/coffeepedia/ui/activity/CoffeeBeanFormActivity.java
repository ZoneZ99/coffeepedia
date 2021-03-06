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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeBeanFormBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanFormViewModel;

public class CoffeeBeanFormActivity extends AppCompatActivity {

	private ActivityCoffeeBeanFormBinding mBinding;

	private CoffeeBeanFormViewModel mViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityCoffeeBeanFormBinding.inflate(getLayoutInflater());
		mViewModel = new ViewModelProvider(this).get(CoffeeBeanFormViewModel.class);
		View view = mBinding.getRoot();
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean(CoffeeBeansActivity.IS_EDIT)) {
				CoffeeBean coffeeBean = bundle.getParcelable(CoffeeBeansActivity.COFFEE_BEAN);
				if (coffeeBean != null) {
					mViewModel.setCoffeeBean(coffeeBean);
					mViewModel.getCoffeeBean().observe(this, bean -> {
						mBinding.inputCoffeeBeanName.setText(bean.getName());
						mBinding.inputCoffeeBeanAltitude.setText(bean.getAltitude());
						mBinding.inputCoffeeBeanAroma.setText(bean.getAroma());
						mBinding.inputCoffeeBeanOrigin.setText(bean.getOrigin());
						mBinding.inputCoffeeBeanType.setText(bean.getType());
						mBinding.inputCoffeeBeanProcess.setText(bean.getProcess());
						mBinding.inputCoffeeBeanTasteNote.setText(bean.getTasteNote());
					});
				}
				mBinding.titleCoffeeBeanForm.setText(R.string.edit_bean);
				mBinding.buttonCoffeeBeanSubmit.setText(R.string.edit);
				mBinding.buttonCoffeeBeanSubmit.setOnClickListener(new EditCoffeeBean());
			} else {
				mBinding.titleCoffeeBeanForm.setText(R.string.add_bean);
				mBinding.buttonCoffeeBeanSubmit.setText(R.string.add);
				mBinding.buttonCoffeeBeanSubmit.setOnClickListener(new AddCoffeeBean());
			}
		}
	}

	private class AddCoffeeBean implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				CoffeeBean coffeeBean = CoffeeBean
					.builder()
					.name(mBinding.inputCoffeeBeanName.getText().toString())
					.tasteNote(mBinding.inputCoffeeBeanTasteNote.getText().toString())
					.aroma(mBinding.inputCoffeeBeanAroma.getText().toString())
					.process(mBinding.inputCoffeeBeanProcess.getText().toString())
					.altitude(mBinding.inputCoffeeBeanAltitude.getText().toString())
					.origin(mBinding.inputCoffeeBeanOrigin.getText().toString())
					.type(mBinding.inputCoffeeBeanType.getText().toString())
					.build();
				mViewModel.insertCoffeeBean(coffeeBean);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), R.string.form_invalid_text, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class EditCoffeeBean implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				CoffeeBean coffeeBean = mViewModel.getCoffeeBean().getValue();
				coffeeBean.setName(mBinding.inputCoffeeBeanName.getText().toString());
				coffeeBean.setType(mBinding.inputCoffeeBeanType.getText().toString());
				coffeeBean.setOrigin(mBinding.inputCoffeeBeanOrigin.getText().toString());
				coffeeBean.setAltitude(mBinding.inputCoffeeBeanAltitude.getText().toString());
				coffeeBean.setProcess(mBinding.inputCoffeeBeanProcess.getText().toString());
				coffeeBean.setAroma(mBinding.inputCoffeeBeanAroma.getText().toString());
				coffeeBean.setTasteNote(mBinding.inputCoffeeBeanTasteNote.getText().toString());
				mViewModel.updateCoffeeBean(coffeeBean);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), R.string.form_invalid_text, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean isInputValid() {
		return !TextUtils.isEmpty(mBinding.inputCoffeeBeanName.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanAltitude.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanAroma.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanOrigin.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanProcess.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanTasteNote.getText());
	}
}
