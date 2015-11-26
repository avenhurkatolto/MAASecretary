package org.geryon.maasecretary.activities;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.geryon.maasecretary.R;
import org.geryon.maasecretary.parser.IsoParser;
import org.geryon.maasecretary.parser.OvenParser;
import org.geryon.maasecretary.sqlite.MaaDAO;
import org.geryon.maasecretary.sqlite.SQLiteHelper;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
	private Button Button_oven;
	private Button Button_isoplanner;
	private String TAG = "main";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File file = new File(this.getDatabasePath(SQLiteHelper.DB_NAME).getPath());
		if (!file.exists()){
			Log.w(TAG, file.toString());
			try {
				InputStream eStream = getAssets().open("Empowered.htm");
				BufferedReader in = new BufferedReader(new InputStreamReader(eStream));
				String inputLine;
				StringBuilder eText = new StringBuilder("");
				while ((inputLine = in.readLine()) != null){ 
					eText.append(inputLine);
				}
				in.close();

				MaaDAO madao = new MaaDAO(this.getApplicationContext());
				madao.writeDB_EISO(IsoParser.parse_EISO(eText.toString()));
				
				eStream = getAssets().open("Augmented.htm");
				in = new BufferedReader(new InputStreamReader(eStream));
				eText = new StringBuilder("");
				while ((inputLine = in.readLine()) != null){ 
					eText.append(inputLine);
				}
				in.close();
				madao.writeDB_AISO(IsoParser.parse_AISO(eText.toString()));
				
				eStream = getAssets().open("TrainTimes.txt");
				in = new BufferedReader(new InputStreamReader(eStream));
				eText = new StringBuilder("");
				while ((inputLine = in.readLine()) != null){ 
					eText.append(inputLine);
				}
				in.close();
				madao.writeTrainTimes(OvenParser.OvenParse(eText.toString()));
				

			} catch (IOException e) {
				Log.w("Sumthin","happened");
				e.printStackTrace();
			}
		} else {
			
		}
		Button_oven = (Button) findViewById(R.id.button_main_oven);
		Button_isoplanner = (Button) findViewById(R.id.button_main_isoplanner);
		Button_oven.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ovenIntent = new Intent(getApplicationContext(), Activity_Oven.class);
				startActivity(ovenIntent);

			}
		});
		Button_isoplanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent isoplanner = new Intent(getApplicationContext(), Activity_IsoDB.class);
				startActivity(isoplanner);

			}
		});

		/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	}
}
