package com.zapolsky.leterss;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
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
public class RssActivity extends Activity {
	ListView rrsLW;
	public String uri = null;
	ArrayList<RssParse> rrsParses = new ArrayList<RssParse>();
	public static RssParse rrsItem = null;
	ArrayAdapter<RssParse> adapterAllRss = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss);
		uri = MainActivity.feedItem.getUrl();
		//Android > 3.0 
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		
		//rrsParses = RssParse.getParse("");
		adapterAllRss = new ArrayAdapter<RssParse>(this, R.layout.item, R.id.itemTT,
				rrsParses);
		adapterAllRss.setNotifyOnChange(true);
		rrsLW = (ListView) findViewById(R.id.rssList);
		rrsLW.setAdapter(adapterAllRss);

		rrsLW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg, View view, int item,
					long lng) {
				rrsItem = rrsParses.get(item);
				Intent intent = new Intent("com.zapolsky.leterss.showinfo");
				startActivity(intent);
			}
		});
		refresh(uri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.rssfeed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refresh(uri);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void refresh(String _url) {
		rrsParses.clear();
		rrsParses = RssParse.getParse(_url);
		adapterAllRss.clear();
		adapterAllRss.addAll(rrsParses);
		adapterAllRss.notifyDataSetChanged();
	}
}
