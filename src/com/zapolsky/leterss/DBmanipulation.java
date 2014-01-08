package com.zapolsky.leterss;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBmanipulation {
	DBHelper dbHelper;
	Context context;

	public DBmanipulation(Context _context) {
		context = _context;
	}

	public void dbwrite(String _title, String _url) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		String title = _title;
		String url = _url;
		cv.put("title", title);
		cv.put("url", url);
		db.insert("rssfeedteble", null, cv);
		db.close();

	}

	public ArrayList<RssFeed> dbRead() {
		ArrayList<RssFeed> rss = new ArrayList<RssFeed>();
		RssFeed feedItem = new RssFeed();
		dbHelper = new DBHelper(context, "LeteRSSDB", null, 1);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("rssfeedteble", null, null, null, null, null,
				null);
		if (cursor != null) {

			if (cursor.moveToFirst()) {
				int titleCol = cursor.getColumnIndex("title");
				int urlCol = cursor.getColumnIndex("url");
				String title;
				String url;
				do {
					title = cursor.getString(titleCol);
					url = cursor.getString(urlCol);
					feedItem.setTitle(title);
					feedItem.setUrl(url);
					rss.add(feedItem);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return rss;
	}
}
