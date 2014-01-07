package com.zapolsky.leterss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public DBHelper(Context context, String nameDB, CursorFactory factory,
			int version) {
		super(context, nameDB, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table rssfeedteble ("
				+ "id integer primary key autoincrement,"
				+ "title text,"
				+" url text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
