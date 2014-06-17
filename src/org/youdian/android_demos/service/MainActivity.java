package org.youdian.android_demos.service;

import org.youdian.android_demos.R;
import org.youdian.android_demos.service.LongRunningService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button start, stop, bind, unbind;
	MyBinder mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_main);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		bind = (Button) findViewById(R.id.bind);
		unbind = (Button) findViewById(R.id.unbind);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		bind.setOnClickListener(this);
		unbind.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		Log.d("Activity", "" + Thread.currentThread().getId());
		Intent intent = new Intent(this, LongRunningService.class);
		switch (id) {
		case R.id.start:

			startService(intent);
			break;
		case R.id.stop:
			stopService(intent);
			break;
		case R.id.bind:
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
			break;
		case R.id.unbind:
			unbindService(conn);
			break;
		default:
			break;
		}
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService = (MyBinder) service;
			mService.log();

		}
	};

}
