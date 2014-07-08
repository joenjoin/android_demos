package org.youdian.android_demos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*
 * Service生命周期
 * Service生命周期可以从两种启动Service的模式开始讲起，分别是context.startService()和context.bindService()。
 *  (1).startService的启动模式下的生命周期：当我们首次使用startService启动一个服务时，系统会实例化一个 Service实例，
 *  依次调用其onCreate和onStartCommand方法，然后进入运行状态，
 *  此后，如果再使用startService启动 服务时，不再创建新的服务对象，系统会自动找到刚才创建的Service实例，
 *  调用其onStart方法；如果我们想要停掉一个服务，可使用 stopService方法，此时onDestroy方法会被调用，
 *  需要注意的是，不管前面使用了多个次startService，只需一次 stopService，即可停掉服务。
 *  (2).bindService启动模式下的生命周期：在这种模式下，当调用者首次使用bindService绑定一个服务时，
 *  系统会实例化一个 Service实例，并一次调用其onCreate方法和onBind方法，然后调用者就可以和服务进行交互了，
 *  此后，如果再次使用 bindService绑定服务，系统不会创建新的Service实例，也不会再调用onBind方法；
 *  如果我们需要解除与这个服务的绑定，可使用 unbindService方法，此时onUnbind方法和onDestroy方法会被调用。
 *  
 *  adb shell dumpsys activity|grep oom_adj   命令查看进程的优先级 ，值越低优先级越高
 */
public class LongRunningService extends Service {
	private static final String TAG = "LongRunningService";
	MyBinder binder;

	class MyBinder extends Binder {

		public void log() {
			Log.d(TAG, "long running service is running");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			log();
		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		if (binder == null)
			binder = new MyBinder();
		Log.d(TAG, "onBind");
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroy");
		super.onDestroy();

		Intent intent = new Intent();
		intent.setClass(this, LongRunningService.class);
		startService(intent);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStartCommand");
		Log.d(TAG, "" + Thread.currentThread().getId());
		Log.d(TAG, "startId=" + startId);
		Thread thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// super.run();
				MyBinder binder = new MyBinder();
				binder.log();
			}

		};
		thread.start();
		/*
		 * START_STICKY 当Service进程被异常终止后，如被进程管理器杀死，稍候会重新启动
		 */
		return START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

}
