package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeBeanEditorBinding;

public class CoffeeBeanEditorActivity extends AppCompatActivity {

	private ActivityCoffeeBeanEditorBinding mBinding;

	private CoffeeBeanRepository mRepository;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRepository = ((CoffeePediaApplication) getApplication()).getCoffeeBeanRepository();
		mBinding = ActivityCoffeeBeanEditorBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean(CoffeeBeansActivity.EDIT)) {
				CoffeeBean coffeeBean = bundle.getParcelable(CoffeeBeansActivity.COFFEE_BEAN);
				if (coffeeBean != null) {
					mBinding.inputCoffeeBeanName.setText(coffeeBean.getName());
					mBinding.inputCoffeeBeanAltitude.setText(coffeeBean.getAltitude());
					mBinding.inputCoffeeBeanAroma.setText(coffeeBean.getAroma());
					mBinding.inputCoffeeBeanOrigin.setText(coffeeBean.getOrigin());
					mBinding.inputCoffeeBeanType.setText(coffeeBean.getType());
					mBinding.inputCoffeeBeanProcess.setText(coffeeBean.getProcess());
					mBinding.inputCoffeeBeanTasteNote.setText(coffeeBean.getTasteNote());
				}
				mBinding.titleCoffeeBeanEditor.setText(getString(R.string.edit_bean));
				mBinding.buttonCoffeeBeanSubmit.setText(getString(R.string.edit));
				mBinding.buttonCoffeeBeanSubmit.setOnClickListener(new EditCoffeeBean(coffeeBean));
			} else {
				mBinding.titleCoffeeBeanEditor.setText(getString(R.string.add_bean));
				mBinding.buttonCoffeeBeanSubmit.setText(getString(R.string.add));
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
				mRepository.insertCoffeeBean(coffeeBean);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
			}
		}
	}

	private class EditCoffeeBean implements View.OnClickListener {

		private CoffeeBean mCoffeeBean;

		public EditCoffeeBean(CoffeeBean coffeeBean) {
			mCoffeeBean = coffeeBean;
		}

		@Override
		public void onClick(View v) {
			if (isInputValid()) {
				mCoffeeBean.setName(mBinding.inputCoffeeBeanName.getText().toString());
				mCoffeeBean.setType(mBinding.inputCoffeeBeanType.getText().toString());
				mCoffeeBean.setOrigin(mBinding.inputCoffeeBeanOrigin.getText().toString());
				mCoffeeBean.setAltitude(mBinding.inputCoffeeBeanAltitude.getText().toString());
				mCoffeeBean.setProcess(mBinding.inputCoffeeBeanProcess.getText().toString());
				mCoffeeBean.setAroma(mBinding.inputCoffeeBeanAroma.getText().toString());
				mCoffeeBean.setTasteNote(mBinding.inputCoffeeBeanTasteNote.getText().toString());
				mRepository.updateCoffeeBean(mCoffeeBean);

				Intent replyIntent = new Intent();
				setResult(RESULT_OK, replyIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
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
