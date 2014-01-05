package com.zapolsky.leterss;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView rrsLW;
	public String uri = "http://football.ua/rss2.ashx";
	ArrayList<RssParse> rrsParses = new ArrayList<RssParse>();
	public static RssParse  rrsItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rrsParses = RssParse.getParse("");
		ArrayAdapter<RssParse> adapter = new ArrayAdapter<RssParse>(this, R.layout.item,R.id.itemTT, rrsParses);
		
		rrsLW = (ListView) findViewById(R.id.ListMainActivity);
		rrsLW.setAdapter(adapter);
		
		rrsLW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg, View view, int item,
					long lng) {
				rrsItem = rrsParses.get(item);
				Intent intent = new Intent("com.zapolsky.leterss.showinfo");
				startActivity(intent);
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
