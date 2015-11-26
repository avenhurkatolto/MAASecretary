package org.geryon.maasecretary.activities;

import java.util.ArrayList;
import java.util.List;

import org.geryon.maasecretary.R;
import org.geryon.maasecretary.model.EIsoModel;
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

public class Activity_EISO extends Activity{
	MaaDAO madao;
	ListView listView;
	E_Adapter adapter;
	ArrayList<EIsoModel> items;

	String TAG = "EISO_ACT"; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iso);
		madao = new MaaDAO(getApplicationContext());
		items = madao.readEIsos();
		listView = (ListView) findViewById(R.id.activity_iso_listview);
		adapter = new E_Adapter(getApplicationContext(), R.layout.row_eiso, items);
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
				//Toast.makeText(getApplicationContext(), items.get(position).getDescription(),Toast.LENGTH_LONG).show();
				Log.w("desc", items.get(position).getDescription());
				ViewHolder vHolder = (ViewHolder) view.getTag();
				vHolder.GetCheckBox().setChecked(items.get(position).getObtainedBoolean());
			}
		});

	}
	
	@Override
	  public void onBackPressed(){
		madao.writeDB_EISO(items);
		Toast.makeText(getApplicationContext(), "Backbutton", Toast.LENGTH_SHORT).show();
		this.finish();
		}
	
	/*private view holder class*/
	private class ViewHolder {
		CheckBox isObtained;
		TextView isoName;
		TextView heroName;
		TextView effect;
		TextView isoLocation;
		public CheckBox GetCheckBox(){
			return this.isObtained;
		}
	}
	private class E_Adapter extends ArrayAdapter<EIsoModel>{
		Context context;

		public E_Adapter(Context context, int resourceId,
				List<EIsoModel> items) {
			super(context, resourceId, items);
			this.context = context;
		}

		

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			EIsoModel rowItem = getItem(position);

			/*LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);*/
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_eiso, null);
				holder = new ViewHolder();
				holder.isObtained = (CheckBox) convertView.findViewById(R.id.row_eiso_checker);
				holder.isoName = (TextView) convertView.findViewById(R.id.row_eiso_isoname);
				holder.heroName = (TextView) convertView.findViewById(R.id.row_eiso_heroname);
				holder.effect = (TextView) convertView.findViewById(R.id.row_eiso_effect);
				holder.isoLocation = (TextView) convertView.findViewById(R.id.row_eiso_isolocation);

				convertView.setTag(holder);
			} else{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.isObtained.setChecked(rowItem.getObtainedBoolean());
			holder.isoName.setText(rowItem.getIsoname());
			holder.heroName.setText(rowItem.getHero());
			holder.effect.setText(rowItem.getEffect());
			holder.isoLocation.setText(rowItem.getLocation());


			return convertView;
		} 
	}
}