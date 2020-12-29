package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityPermissionRationaleBinding;

public class PermissionRationaleActivity extends AppCompatActivity {

	private ActivityPermissionRationaleBinding mBinding;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityPermissionRationaleBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);
	}

	private final ActivityResultLauncher<String> requestPermissionLauncher =
		registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> finish());

	public void grantPermission(View view) {
		requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}

	public void cancelPermission(View view) {
		finish();
	}
}
