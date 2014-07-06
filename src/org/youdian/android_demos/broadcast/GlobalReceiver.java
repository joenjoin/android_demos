package org.youdian.android_demos.broadcast;

import org.youdian.android_demos.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * 在Activity中调用sendBroadcast(intent)打开的BraodcastReceiver与Activity在同一线程中，因此不能再onReceiver中执行长时间的任务
 */
public class GlobalReceiver extends BroadcastReceiver {
	public static final String TAG = "broadcast";
	public static final String GLOBAL_ACTION = "org.youdian.android_demos.GLOBAL_ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (GLOBAL_ACTION.equals(action)) {
			Log.d(TAG, GLOBAL_ACTION + " received ");
		}
		/*
		 * try { Thread.sleep(1000*10); } catch (InterruptedException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		Log.d(TAG, "thread=" + Thread.currentThread());

		launchActivity(context);
	}

	/*
	 * 启动activity，需要设置flag Intent.FLAG_ACTIVITY_NEW_TASK
	 */
	private void launchActivity(Context context) {
		Intent mIntent = new Intent();
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.setClass(context, MainActivity.class);
		context.startActivity(mIntent);
	}

}
