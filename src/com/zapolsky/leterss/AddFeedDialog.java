package com.zapolsky.leterss;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

//import android.support.v4.app.DialogFragment;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AddFeedDialog extends DialogFragment implements OnClickListener {
	EditText editTitle;
	EditText editURL;
	private String _title;
	private String _url;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.dialogTitle);
		View v = inflater.inflate(R.layout.dialog, null);
		v.findViewById(R.id.btnAdd).setOnClickListener(this);
		v.findViewById(R.id.btnCNCL).setOnClickListener(this);
		editTitle = (EditText) v.findViewById(R.id.dialogInputTitle);
		editURL = (EditText) v.findViewById(R.id.dialogInputUrl);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:
			_title = editTitle.getText().toString();
			_url = editURL.getText().toString();
			MainActivity.feedItem.setTitle(_title);
			MainActivity.feedItem.setUrl(_url);
			MainActivity.dbman.dbwrite(_title, _url);
			MainActivity.adatperUpdate();
			dismiss();
			break;
		case R.id.btnCNCL:
			dismiss();
		default:
			break;
		}

	}

	public String get_title() {
		return _title;
	}

	public String get_url() {
		return _url;
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);

	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);

	}

}
