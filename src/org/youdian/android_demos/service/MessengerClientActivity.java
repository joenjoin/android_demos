package org.youdian.android_demos.service;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MessengerClientActivity extends Activity implements
		OnClickListener {
	Button bind;
	boolean bounded = false;
	Messenger mService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_main);
		bind = (Button) findViewById(R.id.bind);
		bind.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		bindService(new Intent(this, MyMessengerService.class), conn,
				Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (bounded) {
			unbindService(conn);
			bounded = false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!bounded)
			return;
		Message msg = Message.obtain(null, MyMessengerService.MSG_HELLO);
		try {
			mService.send(msg);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mService = null;
			bounded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService = new Messenger(service);
			bounded = true;
		}
	};

}
