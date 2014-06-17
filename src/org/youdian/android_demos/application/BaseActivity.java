package org.youdian.android_demos.application;

import android.app.Activity;

public class BaseActivity extends Activity {

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if(MainApplication.isExiting())
			this.finish();
	}
	

}
