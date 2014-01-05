package com.zapolsky.leterss;

import java.net.URL;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityInfo extends Activity {
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		RssParse rss = MainActivity.rrsItem;

		TextView rssDate = (TextView) findViewById(R.id.rssDate);
		TextView rssTitle = (TextView) findViewById(R.id.rssTitle);
		TextView rssText = (TextView) findViewById(R.id.rssText);
		ImageView rssImg = (ImageView) findViewById(R.id.rssImage);
		TextView rssLink = (TextView) findViewById(R.id.rssLink);
		String uri = rss.getImgLink();
		Bitmap bitmap = null;
		try {
			URL url = new URL(uri);
			bitmap = BitmapFactory.decodeStream(url.openStream());
		} catch (Exception e) {
			Log.w("Exeption", e);
		}

		String title = rss.getTitleName();
		String text = rss.getDescText();
		String link = rss.getLink();
		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM - hh:mm");
		rssDate.setText(sdf.format(rss.getPublicationDate()));
		rssTitle.setText(title);
		rssImg.setImageBitmap(bitmap);
		rssText.setText(text);
		rssLink.setText(link);

	}
}
