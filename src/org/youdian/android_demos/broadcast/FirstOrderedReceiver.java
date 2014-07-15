package org.youdian.android_demos.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstOrderedReceiver extends BroadcastReceiver {
	public static final String TAG="FirstOrderedBroadcast";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "code="+getResultCode());
		Log.d(TAG, "data="+getResultData());
		Log.d(TAG, "bundle="+getResultExtras(true));
		Bundle bundle=new Bundle();
		bundle.putInt("id", 1);
		this.setResult(0, "hello", bundle);
		/*/
		abortBroadcast();
		//*/
	}

}
