package com.zapolsky.leterss;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBmanipulation implements Cloneable {
	DBHelper dbHelper;
	Context context;
	public final static String TAG = "DATABASE_LOG";

	public DBmanipulation(Context _context) {
		context = _context;
		dbHelper = new DBHelper(context, DBHelper.DATABASE_NAME, null, 1);
	}

	public void dbwrite(String _title, String _url) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		String title = _title;
		String url = _url;
		cv.put(DBHelper.TITLE, title);
		cv.put(DBHelper.URL, url);
		long rowID = db.insert(DBHelper.TABLE_NAME, DBHelper.ID, cv);
		Log.w(TAG, "Insert rowID: " + rowID);
		db.close();

	}
	
	public void dbdelete(String title){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DBHelper.TABLE_NAME, DBHelper.TITLE + " = '" + title+"'" , null);
		db.close();
		
	}

	public ArrayList<RssFeed> dbRead() {
		ArrayList<RssFeed> rss = new ArrayList<RssFeed>();

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("rssfeedtable", null, null, null, null, null,
				null);
		if (cursor != null) {

			if (cursor.moveToFirst()) {
				int titleCol = cursor.getColumnIndex(DBHelper.TITLE);
				int urlCol = cursor.getColumnIndex(DBHelper.URL);
				int idCol = cursor.getColumnIndex(DBHelper.ID);
				String title;
				String url;
				do {
					RssFeed feedItem = new RssFeed();
					title = cursor.getString(titleCol);
					url = cursor.getString(urlCol);
					Log.w(TAG, cursor.getInt(idCol) + " title: " + title
							+ " url:" + url);
					feedItem.setTitle(title);
					feedItem.setUrl(url);
					rss.add(cursor.getPosition(), feedItem);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return rss;
	}
}
