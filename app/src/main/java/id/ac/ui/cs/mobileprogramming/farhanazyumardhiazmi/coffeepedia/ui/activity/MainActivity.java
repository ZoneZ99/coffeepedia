package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startCoffeeBeansActivity(View view) {
		Intent intent = new Intent(this, CoffeeBeansActivity.class);
		startActivity(intent);
	}

	public void startCoffeeReviewActivity(View view) {
		Intent intent = new Intent(this, CoffeeReviewsActivity.class);
		startActivity(intent);
	}

	public void startBrewMethodsActivity(View view) {
		Intent intent = new Intent(this, BrewMethodsActivity.class);
		startActivity(intent);
	}

	public void startBrewRecipesActivity(View view) {
		Intent intent = new Intent(this, BrewRecipesActivity.class);
		startActivity(intent);
	}
}