package org.geryon.maasecretary.sqlite;

import java.util.ArrayList;

import org.geryon.maasecretary.model.AIsoModel;
import org.geryon.maasecretary.model.EIsoModel;
import org.geryon.maasecretary.model.OvenModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class MaaDAO {

	private SQLiteDatabase database;
	private SQLiteHelper helper;

	public MaaDAO(Context context){
		helper = new SQLiteHelper(context);
	}


	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public void writeDB_EISO(ArrayList<EIsoModel> inModel){
		open();
		database.delete(SQLiteHelper.TABLE_EISO, null, null);
		database.beginTransaction();

		try {
			for (int i = 0; i<inModel.size();i++){
				ContentValues values = new ContentValues();
				values.put(SQLiteHelper.KEY_ISO_NAME, inModel.get(i).getIsoname());
				values.put(SQLiteHelper.KEY_ISO_HERO, inModel.get(i).getHero());
				values.put(SQLiteHelper.KEY_ISO_LOCATION, inModel.get(i).getLocation());
				values.put(SQLiteHelper.KEY_ISO_EFFECT, inModel.get(i).getEffect());
				values.put(SQLiteHelper.KEY_ISO_DESCRIPTION, inModel.get(i).getDescription());
				values.put(SQLiteHelper.KEY_ISO_OBTAINED, inModel.get(i).getObtained());
				database.insert(SQLiteHelper.TABLE_EISO, null, values);
			}
			database.setTransactionSuccessful();

		} finally {
			database.endTransaction();
		}

		close();

	}

	public ArrayList<EIsoModel> readEIsos(){
		open();
		String[] columns = new String[]{SQLiteHelper.KEY_ISO_NAME, 
				SQLiteHelper.KEY_ISO_HERO,
				SQLiteHelper.KEY_ISO_LOCATION,
				SQLiteHelper.KEY_ISO_OBTAINED,
				SQLiteHelper.KEY_ISO_DESCRIPTION,
				SQLiteHelper.KEY_ISO_EFFECT
		};

		ArrayList<EIsoModel> outIsos = new ArrayList<EIsoModel>();
		Cursor mCursor = database.query(SQLiteHelper.TABLE_EISO, columns, null, null, null, null, null);
		if (mCursor.getCount()!=0){
			mCursor.moveToFirst();
		}

		int nameIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_NAME);
		int heroIndex =  mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_HERO);
		int locationIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_LOCATION);
		int obtainedIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_OBTAINED);
		int descIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_DESCRIPTION);
		int effectIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_EFFECT);

		while (!mCursor.isAfterLast()){
			outIsos.add(new EIsoModel(mCursor.getString(nameIndex), 
					mCursor.getString(heroIndex), 
					mCursor.getString(effectIndex),
					mCursor.getString(descIndex),
					mCursor.getString(locationIndex),
					mCursor.getInt(obtainedIndex)));

			mCursor.moveToNext();
		}
		close();
		return outIsos;
	}

	public void writeDB_AISO(ArrayList<AIsoModel> inModel){
		open();
		database.delete(SQLiteHelper.TABLE_AISO, null, null);
		database.beginTransaction();

		try {
			for (int i = 0; i<inModel.size();i++){
				ContentValues values = new ContentValues();
				values.put(SQLiteHelper.KEY_ISO_NAME, inModel.get(i).getIsoname());
				values.put(SQLiteHelper.KEY_ISO_HERO, inModel.get(i).getHero());
				values.put(SQLiteHelper.KEY_ISO_LOCATION, inModel.get(i).getLocation());
				values.put(SQLiteHelper.KEY_ISO_EFFECT, inModel.get(i).getEffect());
				values.put(SQLiteHelper.KEY_ISO_DESCRIPTION, inModel.get(i).getDescription());
				values.put(SQLiteHelper.KEY_ISO_OBTAINED, inModel.get(i).getObtained());
				values.put(SQLiteHelper.KEY_ISO_ACTION, inModel.get(i).getAction());
				database.insert(SQLiteHelper.TABLE_AISO, null, values);
			}
			database.setTransactionSuccessful();

		} finally {
			database.endTransaction();
		}

		close();

	}

	public ArrayList<AIsoModel> readAIsos(){
		open();
		String[] columns = new String[]{SQLiteHelper.KEY_ISO_NAME, 
				SQLiteHelper.KEY_ISO_HERO,
				SQLiteHelper.KEY_ISO_LOCATION,
				SQLiteHelper.KEY_ISO_OBTAINED,
				SQLiteHelper.KEY_ISO_ACTION,
				SQLiteHelper.KEY_ISO_DESCRIPTION,
				SQLiteHelper.KEY_ISO_EFFECT
		};

		ArrayList<AIsoModel> outIsos = new ArrayList<AIsoModel>();
		Cursor mCursor = database.query(SQLiteHelper.TABLE_AISO, columns, null, null, null, null, null);
		if (mCursor.getCount()!=0){
			mCursor.moveToFirst();
		}

		int nameIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_NAME);
		int heroIndex =  mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_HERO);
		int locationIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_LOCATION);
		int obtainedIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_OBTAINED);
		int descIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_DESCRIPTION);
		int effectIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_EFFECT);
		int actionIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_ISO_ACTION);

		while (!mCursor.isAfterLast()){
			outIsos.add(new AIsoModel(
					 mCursor.getString(nameIndex), 
					 mCursor.getString(heroIndex), 
					 mCursor.getString(effectIndex), 
					 mCursor.getString(descIndex), 
					 mCursor.getString(locationIndex), 
					 mCursor.getInt(obtainedIndex), 
					 mCursor.getString(actionIndex)));
			 mCursor.moveToNext();
		}
		close();
		return outIsos;
	}
	
	public void writeTrainTimes(ArrayList<OvenModel> inOven){
		open();
		database.delete(SQLiteHelper.TABLE_OVEN, null, null);
		database.beginTransaction();

		try {
			for (int i = 0; i<inOven.size();i++){
				ContentValues values = new ContentValues();
				values.put(SQLiteHelper.KEY_OVEN_LVL, String.valueOf(inOven.get(i).getLvl()));
				values.put(SQLiteHelper.KEY_OVEN_TIME, String.valueOf(inOven.get(i).getTime()));
				database.insert(SQLiteHelper.TABLE_OVEN, null, values);
			}
			database.setTransactionSuccessful();

		} finally {
			database.endTransaction();
		}
		
		
		close();
	}
	public ArrayList<OvenModel> readOven(){
		open();
		String[] columns = new String[]{SQLiteHelper.KEY_OVEN_LVL, 
				SQLiteHelper.KEY_OVEN_TIME,
				};

		ArrayList<OvenModel> outOven = new ArrayList<OvenModel>();
		Cursor mCursor = database.query(SQLiteHelper.TABLE_OVEN, columns, null, null, null, null, null);
		if (mCursor.getCount()!=0){
			mCursor.moveToFirst();
		}

		int lvlIndex = mCursor.getColumnIndex(SQLiteHelper.KEY_OVEN_LVL);
		int timeIndex =  mCursor.getColumnIndex(SQLiteHelper.KEY_OVEN_TIME);
		
		while (!mCursor.isAfterLast()){
			outOven.add(new OvenModel(
					 Integer.valueOf(mCursor.getString(lvlIndex)), 
					 Integer.valueOf(mCursor.getString(timeIndex))));
			 mCursor.moveToNext();
		}
		close();
		return outOven;
	}
	
}