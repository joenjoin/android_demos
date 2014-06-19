package org.youdian.android_demos.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FloatWindowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// FloatWindowManager fwm=new FloatWindowManager();
		// fwm.createSmallWindow(getApplicationContext());
		Intent intent = new Intent(FloatWindowActivity.this,
				FloatWindowService.class);
		startService(intent);
		finish();
	}

}
