package org.geryon.maasecretary.activities;

import java.util.ArrayList;
import java.util.List;

import org.geryon.maasecretary.R;
import org.geryon.maasecretary.model.AIsoModel;
import org.geryon.maasecretary.sqlite.MaaDAO;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_AISO extends Activity{
	MaaDAO madao;
	ListView listView;
	A_Adapter adapter;
	ArrayList<AIsoModel> items ;

	String TAG = "AISO_ACT"; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iso);
		madao = new MaaDAO(getApplicationContext());
		items = madao.readAIsos();
		listView = (ListView) findViewById(R.id.activity_iso_listview);
		adapter = new A_Adapter(getApplicationContext(), R.layout.row_aiso, items);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), items.get(position).getDescription(),Toast.LENGTH_LONG).show();
				return true;
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				items.get(position).toggleObtained();
			
				Log.w(String.valueOf(position),String.valueOf(items.get(position).getObtained()));
				ViewHolder vHolder = (ViewHolder) view.getTag();
				vHolder.GetCheckBox().setChecked(items.get(position).getObtainedBoolean());
			}
		});

	}
	@Override
	public void onBackPressed(){
		madao.writeDB_AISO(items);
		Toast.makeText(getApplicationContext(), "Backbutton", Toast.LENGTH_SHORT).show();
		this.finish();
	}

	private class ViewHolder {
		CheckBox isObtained;
		TextView isoName;
		TextView heroName;
		TextView skillName;
		TextView effect;
		TextView isoLocation;
	
		public CheckBox GetCheckBox(){
			return this.isObtained;
		}
		
		

	}
	private class A_Adapter extends ArrayAdapter<AIsoModel>{
		Context context;

		public A_Adapter(Context context, int resourceId,
				List<AIsoModel> items) {
			super(context, resourceId, items);
			this.context = context;
		}

		/*private view holder class*/


		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			AIsoModel rowItem = getItem(position);

			/*LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);*/
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_aiso, null);
				holder = new ViewHolder();
				holder.isObtained = (CheckBox) convertView.findViewById(R.id.row_aiso_checker);
				holder.isoName = (TextView) convertView.findViewById(R.id.row_aiso_isoname);
				holder.heroName = (TextView) convertView.findViewById(R.id.row_aiso_heroname);
				holder.skillName = (TextView) convertView.findViewById(R.id.row_aiso_skill);
				holder.effect = (TextView) convertView.findViewById(R.id.row_aiso_effect);
				holder.isoLocation = (TextView) convertView.findViewById(R.id.row_aiso_isolocation);
				
				convertView.setTag(holder);
				
			} else{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.isObtained.setChecked(rowItem.getObtainedBoolean());
			holder.isoName.setText(rowItem.getIsoname());
			holder.heroName.setText(rowItem.getHero());
			holder.skillName.setText(rowItem.getAction());
			holder.effect.setText(rowItem.getEffect());
			holder.isoLocation.setText(rowItem.getLocation());


			return convertView;
		} 
	}
}
