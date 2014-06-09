package org.youdian.android_demos.service;

import org.youdian.android_demos.service.AIDLService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyAIDLService extends Service {
	AIDLService.Stub mBinder;
	private static final String TAG="MyAidlService";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		if(mBinder==null)
			mBinder=new MyBinder();
		return mBinder;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int i=0;
		while(i++<10e5){
			Log.d(TAG, "onStartCommand()");
			Log.d(TAG, Thread.currentThread().getId()+"");
			
		}
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	class MyBinder extends AIDLService.Stub{

		@Override
		public void registerTestCall() throws RemoteException {
			// TODO Auto-generated method stub
			int i=0;
			while(i++<1000){
				Log.d(TAG, "registerTestCall()");
				Log.d(TAG, Thread.currentThread().getId()+"");
				
			}
			
		}

		@Override
		public void invokeCallback() throws RemoteException {
			// TODO Auto-generated method stub
			Log.d(TAG, "invokeCallback");	
		}
		
	}

}
