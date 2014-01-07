package com.zapolsky.leterss;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	ListView rrsFeedLW;
	ArrayList<RssFeed> feed = null;
	DBHelper dbHelper;
	ArrayAdapter<RssFeed> adapterRss;
	public static RssFeed feedItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feed = new ArrayList<RssFeed>();
		feedItem = new RssFeed();
		feedItem.setTitle("Добавте ленту.");
		feedItem.setUrl("http://football.ua/rss2.ashx");
		feed.add(feedItem);
		// Android > 3.0
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		dbRead();
		
		adapterRss = new ArrayAdapter<RssFeed>(this, R.layout.item,
				R.id.itemTT, feed);
		adapterRss.setNotifyOnChange(true);
		rrsFeedLW = (ListView) findViewById(R.id.ListMainActivity);
		rrsFeedLW.setAdapter(adapterRss);

		rrsFeedLW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg, View view, int item,
					long lng) {
				feedItem = feed.get(item);
				Intent intent = new Intent("com.zapolsky.leterss.showrssfeed");
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.addFeed:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private void dbwrite(String _title,String _url){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues cv = new ContentValues();
		
		cv.put("title", _title);
		cv.put("url", _url);
		db.insert("rssfeedteble", null, cv);
		db.close();
	}
	
	private void dbRead(){
		dbHelper = new DBHelper(this, "LeteRSSDB", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("rssfeedteble", null, null, null, null, null,
				null);
		if (cursor != null) {
			//feed.clear();			
			
			if (cursor.moveToFirst()) {
				int titleCol = cursor.getColumnIndex("title");
				int urlCol = cursor.getColumnIndex("url");
				String title;
				String url;
				do {
					title = cursor.getString(titleCol);
					url = cursor.getString(urlCol);
					new RssFeed();
					feedItem.setTitle(title);
					feedItem.setUrl(url);
					feed.add(feedItem);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
	}
}
