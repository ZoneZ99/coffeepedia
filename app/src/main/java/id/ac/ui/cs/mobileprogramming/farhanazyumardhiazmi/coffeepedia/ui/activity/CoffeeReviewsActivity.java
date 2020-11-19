package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.BuildConfig;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.ActivityCoffeeReviewsBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.service.CoffeeReviewScrapingService;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.service.util.CoffeeReview;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CoffeeReviewsActivity extends AppCompatActivity {

	private ActivityCoffeeReviewsBinding mBinding;

	private BroadcastReceiver mCoffeeReviewReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int resultCode = bundle.getInt(CoffeeReviewScrapingService.RESULT);
				if (resultCode == RESULT_OK) {
					ArrayList<CoffeeReview> reviews = bundle.getParcelableArrayList(
						CoffeeReviewScrapingService.SCRAPING_CONTENT
					);
					populateReviewList(reviews);
				} else if (resultCode == RESULT_CANCELED) {
					Toast.makeText(getApplicationContext(), R.string.failure_fetching_reviews, Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = ActivityCoffeeReviewsBinding.inflate(getLayoutInflater());
		View view = mBinding.getRoot();
		setContentView(view);

		ArrayList<String> loadingContent = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			loadingContent.add("Loading...");
		}
		mBinding.reviewList.setAdapter(
			new ArrayAdapter<>(
				this,
				android.R.layout.simple_list_item_1,
				loadingContent
			)
		);

		registerReceiver(mCoffeeReviewReceiver, new IntentFilter(BuildConfig.APPLICATION_ID + ".COFFEE_REVIEW_BROADCAST"));
		Intent intent = new Intent(this, CoffeeReviewScrapingService.class);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mCoffeeReviewReceiver);
	}

	private void populateReviewList(ArrayList<CoffeeReview> content) {
		mBinding.reviewList.setAdapter(
			new ArrayAdapter<>(
				this,
				android.R.layout.simple_list_item_1,
				content.stream().map(CoffeeReview::toString).collect(Collectors.toList())
			)
		);
	}
}
