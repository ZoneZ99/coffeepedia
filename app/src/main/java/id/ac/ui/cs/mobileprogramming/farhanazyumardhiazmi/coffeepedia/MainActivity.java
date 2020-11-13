package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}

	public void callCoffeeBeansActivity(View view) {
		Toast.makeText(getApplicationContext(), "Bean", Toast.LENGTH_LONG).show();
	}

	public void callCoffeeReviewActivity(View view) {
		Toast.makeText(getApplicationContext(), "Review", Toast.LENGTH_LONG).show();
	}

	public void callBrewMethodsActivity(View view) {
		Toast.makeText(getApplicationContext(), "Method", Toast.LENGTH_LONG).show();
	}

	public void callBrewRecipesActivity(View view) {
		Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_LONG).show();
	}
}