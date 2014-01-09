package com.zapolsky.leterss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LeteRSSDB";
    public static final String TABLE_NAME = "rssfeedtable";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String URL = "url";
    
    
	public DBHelper(Context context, String nameDB, CursorFactory factory,
			int version) {
		super(context, nameDB, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+ TABLE_NAME +" ("
				+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TITLE + " TEXT,"
				+ URL + " TEXT" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
