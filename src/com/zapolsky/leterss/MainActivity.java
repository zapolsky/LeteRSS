package com.zapolsky.leterss;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
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
	public static DBmanipulation dbman;
	ArrayAdapter<RssFeed> adapterRss;
	public static RssFeed feedItem;
	DialogFragment dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feed = new ArrayList<RssFeed>();
		feedItem = new RssFeed();
		dbman = new DBmanipulation(this);
		 feedItem.setTitle("Добавте ленту.");
		 feedItem.setUrl("http://football.ua/rss2.ashx");
		 feed.add(feedItem);
		
		
		dialog = new AddFeedDialog();
		
		// Android > 3.0 
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		feed = dbman.dbRead();
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
			dialog.show(getFragmentManager(), "dialog");
			feed.clear();
			dbman.dbRead();
			adapterRss.clear();
			adapterRss.addAll(feed);
			adapterRss.notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
