package org.geryon.maasecretary.intentreceivers;


//
import android.R.drawable;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String MY_PREFS = "MaaSecretary";
		String LOGTAG = this.getClass().getName();
		Log.w("BroadcastReceiver","started");
		int ovenNumber = arg1.getIntExtra("ovenNumber", 0);
		Log.w("ovenNumber", String.valueOf(ovenNumber));
		//String s = arg1.getStringExtra("Info");
		long[] pattern = {300,300,500,500,700,700};
		SharedPreferences.Editor editor = arg0.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).edit();
		editor.remove("Oven" + String.valueOf(ovenNumber));
		editor.commit();
			
		Vibrator v;
		v=(Vibrator)arg0.getSystemService(Context.VIBRATOR_SERVICE);
		if (!v.hasVibrator()){
			Log.w(LOGTAG, "no vibrator");
		}else {

			v.vibrate(pattern, -1);
		}
		try {
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(arg0, notification);
			r.play();
			NotificationCompat.Builder mBuilder =
					new NotificationCompat.Builder(arg0)
			.setSmallIcon(drawable.ic_menu_compass)
			.setContentTitle("MAA Secretary")
			.setContentText("Your hero is baken!");
			
			NotificationManager mNotifyMgr = 
			        (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.notify(1, mBuilder.build());
			

		} catch (Exception e) {
			Toast.makeText(arg0, "There is some problem. \n I'm terribly sorry, dear sire.", Toast.LENGTH_SHORT).show();
		}
	}

}
