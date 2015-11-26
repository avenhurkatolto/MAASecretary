package org.geryon.maasecretary.activities;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.geryon.maasecretary.R;
import org.geryon.maasecretary.model.OvenModel;
import org.geryon.maasecretary.sqlite.MaaDAO;
import org.geryon.maasecretary.utils.TimeConverter;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Activity_Oven extends Activity{
	String MY_PREFS = "MaaSecretary";
	Button button1, button2;
	TextView timer1, timer2, title1, title2;
	MaaDAO madao;
	SharedPreferences mSharedP;

	CountDownTimer countDown1, countDown2;
	AlertDialog dialog ;
	Calendar mCal;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("DateTime", "starting");

		Log.w("DateTime", "finished");


		setContentView(R.layout.activity_oven);
		button1 = (Button)findViewById(R.id.oven_button1);
		button2 = (Button)findViewById(R.id.oven_button2);
		timer1 = (TextView) findViewById(R.id.oven_textview_timer1);
		timer2 = (TextView) findViewById(R.id.oven_textview_timer2);
		title1 = (TextView) findViewById(R.id.oven_textview_title1);
		title2 = (TextView) findViewById(R.id.oven_textview_title2);
		madao = new MaaDAO(getApplicationContext());
		mSharedP = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showOvenDialog(1);
				//Toast.makeText(getApplicationContext(), "button1", Toast.LENGTH_SHORT).show();
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "button2", Toast.LENGTH_SHORT).show();
				showOvenDialog(2);

			}
		});
		refreshScreen();
	}

	protected void refreshScreen(){
		startTimer(1, getAlarm(1));
		startTimer(2, getAlarm(2));

	}

	protected long getAlarm(int ovenNumber){
		long mDateLong = mSharedP.getLong("Oven" + String.valueOf(ovenNumber), -1);
		Log.w("Getalarm" + String.valueOf(ovenNumber), String.valueOf(mDateLong));
		return mDateLong;
	}

	protected void deleteAlarm(int ovenNumber){
		Intent intent = new Intent(getBaseContext(), org.geryon.maasecretary.intentreceivers.AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), ovenNumber, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
		//(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + delay, pendingIntent);
		SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
		editor.remove("Oven" + String.valueOf(ovenNumber));
		//editor.putLong("Oven"+String.valueOf(ovenNumber), Calendar.getInstance().getTimeInMillis()+delay);
		editor.commit();
		refreshScreen();
	}

	protected void setAlarm(int ovenNumber, long delay){
		mCal = GregorianCalendar.getInstance(TimeZone.getDefault());
		Intent intent = new Intent(getBaseContext(), org.geryon.maasecretary.intentreceivers.AlarmReceiver.class);
		intent.putExtra("ovenNumber", ovenNumber);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), ovenNumber, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, mCal.getTimeInMillis() + delay, pendingIntent);
		//alarmManager.set(AlarmManager.RTC_WAKEUP, mCal.getTimeInMillis() + delay, pendingIntent);
		SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
		editor.putLong("Oven"+String.valueOf(ovenNumber), mCal.getTimeInMillis()+delay);
		editor.commit();
		refreshScreen();

	}

	protected void startTimer(final int ovenNumber, final long countTime){
		if (countTime != -1) {
			//Date cal = GregorianCalendar.getInstance().getTimeInMillis();
			
			setCountDown(ovenNumber, countTime);
			setButton(ovenNumber, false);
		} else {
			setButton(ovenNumber, true);
		}

	}

	protected void showOvenDialog(final int button) {

		AlertDialog.Builder alertadd = new AlertDialog.Builder(Activity_Oven.this);
		LayoutInflater factory = LayoutInflater.from(Activity_Oven.this);
		View view = factory.inflate(R.layout.dialog_oven, null);
		ListView dialog_list = (ListView)view.findViewById(R.id.listview_dialog_oven);
		final DialogAdapter dialogAdapter = new DialogAdapter(getApplicationContext(), R.layout.row_dialog_oven, madao.readOven());
		dialog_list.setAdapter(dialogAdapter);
		dialog_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*if (position == 3){
					Toast.makeText(getApplicationContext(), "Seriously? Just finish it now!", Toast.LENGTH_SHORT).show();
				}else{*/
				Toast.makeText(getApplicationContext(), "Notification set", Toast.LENGTH_SHORT).show();
				setAlarm(button, dialogAdapter.getItem(position).getTime()*1000*60);
				dialog.cancel();
				Log.w("notify set", String.valueOf(dialogAdapter.getItem(position).getTime()));
			}

			//}
		});
		alertadd.setTitle("Set the timer");
		alertadd.setView(view);
		alertadd.create();
		dialog = alertadd.show();

	}
	private void setCountDown(final int ovenNumber, final long countTime){
		Log.w("started", "countdown"+String.valueOf(ovenNumber));
		mCal =	GregorianCalendar.getInstance(TimeZone.getDefault());
		if (ovenNumber == 1){
			countDown1 = new CountDownTimer(countTime - mCal.getTimeInMillis(), 1000) {
				
				public void onTick(long millisUntilFinished) {
					/*CharSequence timespan = DateUtils.getRelativeTimeSpanString(
							countTime,
							GregorianCalendar.getInstance(TimeZone.getDefault()).getTimeInMillis(),
							DateUtils.SECOND_IN_MILLIS,
							DateUtils.FORMAT_ABBREV_TIME);*/
					String timespan = TimeConverter.TimeConvert(millisUntilFinished);
					timer1.setText(timespan);

				}

				public void onFinish() {
					timer1.setText("done!");
					setButton(ovenNumber, true);
					}
			}.start();

		} else if (ovenNumber == 2){
			
			countDown2 = new CountDownTimer(countTime - mCal.getTimeInMillis(), 1000) {

				public void onTick(long millisUntilFinished) {
					String timespan = TimeConverter.TimeConvert(millisUntilFinished);


					timer2.setText(timespan);

				}

				public void onFinish() {
					timer2.setText("done!");
					setButton(ovenNumber, true);
					}
			}.start();
		}


	}

	private void setButton(int buttonNumber, boolean toSTart){
		if (buttonNumber == 1 ){
			if (toSTart == false){
				button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteAlarm(1);
						countDown1.cancel();
						countDown1 = null;
						timer1.setText("No alarm in progress");
					}
				});
				button1.setText("Delete");
			} else{
				button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showOvenDialog(1);
					}
				});
				button1.setText("Start");
			}


		} else if (buttonNumber == 2 ){
			if (toSTart == false){
				button2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteAlarm(2);
						countDown2.cancel();
						timer2.setText("No alarm in progress");
					}
				});
				button2.setText("Delete");
			} else{
				button2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showOvenDialog(2);
					}
				});
				button2.setText("Start");
			}
		}


	}



	private class DialogAdapter extends ArrayAdapter<OvenModel>{
		Context context;

		public DialogAdapter(Context inContext, int resource,
				List<OvenModel> items) {
			super(inContext, resource, items);
			this.context = inContext;
		}

		private class ViewHolder{
			TextView text; 
		}
		private String formatTime(int inTime){
			if (inTime <= 60){
				return String.valueOf(inTime)+" minutes";
			} else {
				return String.valueOf(inTime/60)+" hours";
			}
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			OvenModel rowItem = getItem(position);

			/*LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);*/
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_oven, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.row_oven_text);
				convertView.setTag(holder);

			} else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.text.setText("Level: "+rowItem.getLvl()+" : "+formatTime(rowItem.getTime()));
			return convertView;
		} 		
	}
}