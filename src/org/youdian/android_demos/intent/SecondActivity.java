package org.youdian.android_demos.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {
	private static final String TAG = "Parcelable";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Person p = intent.getParcelableExtra("p");
		Log.d(TAG, "id=" + p.getId());
		Log.d(TAG, "name=" + p.getName());
		Log.d(TAG, "phone=" + p.getPhone());
		finish();
	}

}
