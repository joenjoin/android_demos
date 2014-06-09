package org.youdian.android_demos.service;

import org.youdian.android_demos.R;
import org.youdian.android_demos.service.AIDLService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AIDLClientActivity extends Activity implements OnClickListener {
	Button bind,unbind;
	AIDLService mService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_main);
		bind=(Button)findViewById(R.id.bind);
		bind.setOnClickListener(this);
		unbind=(Button)findViewById(R.id.unbind);
		unbind.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bind:
			Intent intent=new Intent();
			intent.setClass(this, MyAIDLService.class);
			//bindService(intent, conn, Context.BIND_AUTO_CREATE);
			startService(intent);
			Log.d("AIDLClientActivity", Thread.currentThread().getId()+"");
			
			break;
		case R.id.unbind:
			try {
				mService.registerTestCall();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
	}
	
	ServiceConnection  conn=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService=AIDLService.Stub.asInterface(service);
		}
	};

	
}
