package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment.ReminderTimePickerFragment;

public class MainActivity extends AppCompatActivity {

	private NotificationManager mNotificationManager;

	private static final String PRIMARY_CHANNEL_ID =
		"primary_notification_channel";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNotificationManager = (NotificationManager)
			getSystemService(NOTIFICATION_SERVICE);
		createNotificationChannel();
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

	public void startAnimationActivity(View view) {
		Intent intent = new Intent(this, AnimationActivity.class);
		startActivity(intent);
	}

	public void showTimePicker(View view) {
		DialogFragment timePickerFragment = new ReminderTimePickerFragment();
		timePickerFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public void createNotificationChannel() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notificationChannel = new NotificationChannel(
				PRIMARY_CHANNEL_ID,
				"Coffee notification",
				NotificationManager.IMPORTANCE_HIGH);
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.RED);
			notificationChannel.enableVibration(false);
			notificationChannel.setDescription("Notifies the user to sip a cup of coffee");

			mNotificationManager.createNotificationChannel(notificationChannel);
		}
	}
}
