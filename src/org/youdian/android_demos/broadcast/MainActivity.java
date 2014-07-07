package org.youdian.android_demos.broadcast;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_button);
		Log.d(GlobalReceiver.TAG,
				"thread in activity=" + Thread.currentThread());
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.button1:
			trySendStickyBroadcast();
			break;
		case R.id.button2:
			break;
		}
	}

	/*
	 * 如果BroadcastReceiver声明了自定义权限，那么BroadcastReceiver所在应用需要声明<uses-permission/>和
	 * <permission/>，发送者需声明<uses-permission/>
	 */
	private void trySendGlobalBroadcast() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(GlobalReceiver.GLOBAL_ACTION);
		sendBroadcast(intent, "org.youdian.android_demos.GLOBAL_PERMISSION");
	}
	
	private void trySendStickyBroadcast(){
		Intent intent = new Intent(GlobalReceiver.GLOBAL_ACTION);
		sendStickyBroadcast(intent);
	}
}
