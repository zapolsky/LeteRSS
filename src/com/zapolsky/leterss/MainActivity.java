package com.zapolsky.leterss;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	final int DIALOG_DELETE = 1;

	ListView rrsFeedLW;
	static ArrayList<RssFeed> feed = null;
	public static DBmanipulation dbman;
	public static ArrayAdapter<RssFeed> adapterRss;
	public static RssFeed feedItem;
	DialogFragment dialog;
	AlertDialog.Builder adb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feed = new ArrayList<RssFeed>();
		feedItem = new RssFeed();
		dbman = new DBmanipulation(this);

		// feedItem.setTitle("Добавте ленту.");
		// feedItem.setUrl("http://football.ua/rss2.ashx");
		// feed.add(feedItem);

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

		rrsFeedLW
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public boolean onItemLongClick(AdapterView<?> arg,
							View view, int item, long lng) {
						feedItem = feed.get(item);
						showDialog(DIALOG_DELETE);
						return false;
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

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static void adatperUpdate() {
		feed.clear();
		feed = dbman.dbRead();
		adapterRss.clear();
		adapterRss.addAll(feed);
		adapterRss.notifyDataSetChanged();
	}

	protected Dialog onCreateDialog(int id) {
		adb = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_DELETE:
			adb.setTitle(feedItem.getTitle());
			Resources res = getResources();
			String[] data = res.getStringArray(R.array.rssFeedDialog);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.select_dialog_item, data);
			adb.setAdapter(adapter, deleteDialogClickListener);
			break;

		default:
			break;
		}

		return adb.create();
	}
	protected void onPrepareDialog(int id, Dialog dialog) {
		
		switch (id) {
		case DIALOG_DELETE:
			dialog.setTitle(feedItem.getTitle());
			AlertDialog aDialog = (AlertDialog) dialog;
			ListAdapter lAdapter = aDialog.getListView().getAdapter();
			if (lAdapter instanceof BaseAdapter) {
				BaseAdapter bAdapter = (BaseAdapter) lAdapter;
				bAdapter.notifyDataSetChanged();
	        	}
			break;

		default:
			break;
		
		
	};}

	OnClickListener deleteDialogClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int id) {
			switch (id) {
			case 0:
				dbman.dbdelete(feedItem.getTitle());
				adatperUpdate();
				break;

			default:
				break;
			}
		}

	};
}
