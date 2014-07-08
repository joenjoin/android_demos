package org.youdian.android_demos.exception;

import android.app.Application;

/*
 * an application class that make us handle the exception manually.
 */
public class ExceptionHandledApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		/*
		 * 既可以捕获UI线程的异常，又可以捕获非UI线程的异常
		 * 
		 */
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext());
	}

}
