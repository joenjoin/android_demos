package org.youdian.android_demos.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FloatWindowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// FloatWindowManager fwm=new FloatWindowManager();
		// fwm.createSmallWindow(getApplicationContext());
		Log.d("FloatWindow", "startService");
		Intent intent = new Intent(FloatWindowActivity.this,
				FloatWindowService.class);
		startService(intent);
		finish();
	}

}
