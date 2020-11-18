package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeBeansBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider.PdfProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.CoffeeBeanDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.CoffeeBeanListFragment;

public class CoffeeBeansActivity extends AppCompatActivity {

	private ActivityCoffeeBeansBinding mBinding;

	public static final String IS_EDIT = "is_edit";

	public static final int ADD_COFFEE_BEAN_ACTIVITY_REQUEST_CODE = 1;

	public static final int EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE = 2;

	private static final int EXPORT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE = 3 ;

	public static final String COFFEE_BEAN = "coffee_bean";

	private long exportedCoffeeBeanId;

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
		Intent intent = new Intent(this, CoffeeBeanFormActivity.class);
		intent.putExtra(IS_EDIT, false);
		startActivityForResult(intent, ADD_COFFEE_BEAN_ACTIVITY_REQUEST_CODE);
	}

	public void startCoffeeBeanEditView(CoffeeBean coffeeBean) {
		Intent intent = new Intent(this, CoffeeBeanFormActivity.class);
		intent.putExtra(IS_EDIT, true);
		intent.putExtra(COFFEE_BEAN, coffeeBean);
		startActivityForResult(intent, EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ADD_COFFEE_BEAN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_add_coffee_bean, Toast.LENGTH_SHORT).show();
		} else if (requestCode == EDIT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_edit_coffee_bean, Toast.LENGTH_SHORT).show();
		} else if (requestCode == EXPORT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			if (data != null) {
				ContentValues contentValues = new ContentValues();
				contentValues.put(PdfProvider.CONTENT_TYPE, PdfProvider.COFFEE_BEAN);
				contentValues.put(PdfProvider.CONTENT_ID, exportedCoffeeBeanId);
				contentValues.put(PdfProvider.URI_KEY, data.getDataString());
				getContentResolver().insert(PdfProvider.CONTENT_URI, contentValues);
				Toast.makeText(getApplicationContext(), R.string.success_export_pdf, Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void exportDataToPdf(long coffeeBeanId, String filename) {
		exportedCoffeeBeanId = coffeeBeanId;
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");
		intent.putExtra(Intent.EXTRA_TITLE, filename);
		startActivityForResult(intent, EXPORT_COFFEE_BEAN_ACTIVITY_REQUEST_CODE);
	}
}
