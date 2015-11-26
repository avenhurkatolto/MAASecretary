package org.geryon.maasecretary.activities;

import org.geryon.maasecretary.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity_IsoDB extends Activity{
	Button soaps;
	Button beads;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_isodb);
		soaps = (Button) findViewById(R.id.button_isodb_soaps);
		beads = (Button) findViewById(R.id.button_isodb_beads);
		
		soaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ovenIntent = new Intent(getApplicationContext(), Activity_EISO.class);
				startActivity(ovenIntent);
			}
		});
		
		beads.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ovenIntent = new Intent(getApplicationContext(), Activity_AISO.class);
				startActivity(ovenIntent);
			}
		});
		
	}
}