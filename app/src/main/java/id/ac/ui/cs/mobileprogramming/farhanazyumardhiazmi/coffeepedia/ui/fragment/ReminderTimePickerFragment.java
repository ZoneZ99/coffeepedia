package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.receiver.AlarmReceiver;

import java.util.Calendar;

public class ReminderTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	private static final int NOTIFICATION_ID = 0;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return new TimePickerDialog(
			getActivity(),
			this,
			hour,
			minute,
			DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);

		Intent notifyIntent = new Intent(getActivity(), AlarmReceiver.class);
		final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(
			getActivity(),
			NOTIFICATION_ID,
			notifyIntent,
			PendingIntent.FLAG_UPDATE_CURRENT
		);

		final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		if (alarmManager != null) {
			alarmManager.setExactAndAllowWhileIdle(
				AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(),
				notifyPendingIntent
			);
			Toast.makeText(getActivity(), R.string.reminder_set, Toast.LENGTH_SHORT).show();
		}
 	}
}
