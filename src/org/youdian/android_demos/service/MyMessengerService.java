package org.youdian.android_demos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/*
 * 用于跨进程通信
 */
public class MyMessengerService extends Service {

	static final int MSG_HELLO = 1;

	class IncomingHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {

			case MSG_HELLO:
				sayHello();
				break;
			default:
				super.handleMessage(msg);
			}

		}

	}

	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mMessenger.getBinder();
	}

	public void sayHello() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT)
				.show();
	}

}
