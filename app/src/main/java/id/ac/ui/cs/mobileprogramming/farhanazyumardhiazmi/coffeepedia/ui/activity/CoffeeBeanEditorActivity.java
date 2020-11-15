package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
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
	}

	public void submitInput(View view) {
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

	private boolean isInputValid() {
		return !TextUtils.isEmpty(mBinding.inputCoffeeBeanName.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanAltitude.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanAroma.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanOrigin.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanProcess.getText())
			&& !TextUtils.isEmpty(mBinding.inputCoffeeBeanTasteNote.getText());
	}
}
