package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityBrewMethodsBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider.PdfProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewMethodDetailFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.BrewMethodListFragment;

public class BrewMethodsActivity extends AppCompatActivity {

	private ActivityBrewMethodsBinding mBinding;

	public static final String IS_EDIT = "is_edit";

	public static final int ADD_BREW_METHOD_ACTIVITY_REQUEST_CODE = 1;

	public static final int EDIT_BREW_METHOD_ACTIVITY_REQUEST_CODE = 2;

	private static final int EXPORT_BREW_METHOD_ACTIVITY_REQUEST_CODE = 3;

	public static final String BREW_METHOD = "brew_method";

	private long exportedBrewMethodId;

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

	public void startBrewMethodAddView(View view) {
		Intent intent = new Intent(this, BrewMethodFormActivity.class);
		intent.putExtra(IS_EDIT, false);
		startActivityForResult(intent, ADD_BREW_METHOD_ACTIVITY_REQUEST_CODE);
	}

	public void startBrewMethodEditView(BrewMethod brewMethod) {
		Intent intent = new Intent(this, BrewMethodFormActivity.class);
		intent.putExtra(IS_EDIT, true);
		intent.putExtra(BREW_METHOD, brewMethod);
		startActivityForResult(intent, EDIT_BREW_METHOD_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ADD_BREW_METHOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_add_brew_method, Toast.LENGTH_SHORT).show();
		} else if (requestCode == EDIT_BREW_METHOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), R.string.success_edit_brew_method, Toast.LENGTH_SHORT).show();
		} else if (requestCode == EXPORT_BREW_METHOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(PdfProvider.CONTENT_TYPE, PdfProvider.BREW_METHOD);
			contentValues.put(PdfProvider.CONTENT_ID, exportedBrewMethodId);
			contentValues.put(PdfProvider.URI_KEY, data.getDataString());
			getContentResolver().insert(PdfProvider.CONTENT_URI, contentValues);
			Toast.makeText(getApplicationContext(), R.string.success_export_pdf, Toast.LENGTH_SHORT).show();
		}
	}

    public void exportDataToPdf(long brewMethodId, String filename) {
		exportedBrewMethodId = brewMethodId;
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");
		intent.putExtra(Intent.EXTRA_TITLE, filename);
		startActivityForResult(intent, EXPORT_BREW_METHOD_ACTIVITY_REQUEST_CODE);
    }
}
