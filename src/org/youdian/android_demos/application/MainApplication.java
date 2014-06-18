package org.youdian.android_demos.application;

import android.app.Application;

public class MainApplication extends Application {
	/*
	 * 退出应用程序的一种方式，在所有activity的onRestart()方法中 实现 if(MainApplication.isExiting())
	 * this.finish();
	 */
	public static boolean isExiting = false;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public static boolean isExiting() {
		return isExiting;
	}

	public static void setExiting(boolean isExiting) {
		MainApplication.isExiting = isExiting;
	}

}
