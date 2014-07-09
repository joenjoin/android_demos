package org.youdian.android_demos.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	private void checkNetworkInfo(){
		ConnectivityManager conn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobileState=conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifiState=conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
	}
	

}
