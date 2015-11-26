package org.geryon.maasecretary.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{

	public static final String LOG = "SQLitehelper";
	public static final int DATABASE_VERSION = 1;

	//Database name
	public static final String DB_NAME= "MAASecretaryDB.db";

	//Table names
	public static final String TABLE_EISO = "EISO";
	public static final String TABLE_AISO = "AISO";
	public static final String TABLE_OVEN = "Oven";
	
	//Common column names
	public static final String KEY_ID = "id";

	//TABLE columns
	public static final String KEY_ISO_NAME = "ISO_name";
	public static final String KEY_ISO_HERO = "Hero_name";
	public static final String KEY_ISO_LOCATION = "ISO_location";
	public static final String KEY_ISO_OBTAINED = "GotIt";
	public static final String KEY_ISO_EFFECT = "Effect";
	public static final String KEY_ISO_DESCRIPTION = "Description";
	public static final String KEY_ISO_ACTION = "Action";
	public static final String KEY_OVEN_LVL = "Level";
	public static final String KEY_OVEN_TIME = "Time";
	
	
	
	//Create TABLE(s)
	private static final String CREATE_TABLE_EISO = "CREATE TABLE "
			+ TABLE_EISO + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_ISO_NAME	+ " TEXT, " 
			+ KEY_ISO_HERO	+ " TEXT, "
			+ KEY_ISO_EFFECT + " TEXT, "
			+ KEY_ISO_DESCRIPTION + " TEXT, "
			+ KEY_ISO_LOCATION	+ " TEXT, "
			+ KEY_ISO_OBTAINED + " INTEGER"
			+ ")";


	private static final String CREATE_TABLE_AISO = "CREATE TABLE "
			+ TABLE_AISO + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_ISO_NAME	+ " TEXT, " 
			+ KEY_ISO_HERO	+ " TEXT, "
			+ KEY_ISO_ACTION + " TEXT, "
			+ KEY_ISO_EFFECT + " TEXT, "
			+ KEY_ISO_DESCRIPTION + " TEXT, "
			+ KEY_ISO_LOCATION	+ " TEXT, "
			+ KEY_ISO_OBTAINED + " INTEGER"
			+ ")";

	private static final String CREATE_TABLE_OVEN = "CREATE TABLE "
			+ TABLE_OVEN + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY, " 
			+ KEY_OVEN_LVL	+ " TEXT, " 
			+ KEY_OVEN_TIME	+ " TEXT "
			+ ")";
	
	public SQLiteHelper(Context context) {
		super(context,DB_NAME , null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_EISO);
		db.execSQL(CREATE_TABLE_AISO);
		db.execSQL(CREATE_TABLE_OVEN);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EISO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AISO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVEN);
		onCreate(db);
	}
}