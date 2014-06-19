package org.youdian.android_demos.application;

import org.youdian.android_demos.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatWindowManager {
	private LayoutParams bigWindowParams;
	WindowManager mWindowManager;
	private View smallWindow;
	private View bigWindow;
	private LayoutParams smallWindowParams;

	public WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}

		return mWindowManager;
	}

	public void createSmallWindow(Context context) {
		WindowManager wm = getWindowManager(context);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		if (smallWindow == null) {
			smallWindow = LayoutInflater.from(context).inflate(
					R.layout.application_floatwindow_small, null);
			if (smallWindowParams == null) {
				smallWindowParams = new LayoutParams();
				smallWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
				smallWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
				// | LayoutParams.FLAG_NOT_FOCUSABLE;
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				// smallWindowParams.width = FloatWindowSmallView.viewWidth;
				// smallWindowParams.height = FloatWindowSmallView.viewHeight;
				smallWindowParams.width = 100;// WindowManager.LayoutParams.WRAP_CONTENT;
				smallWindowParams.height = 100;// WindowManager.LayoutParams.WRAP_CONTENT;
				smallWindowParams.x = 2;
				smallWindowParams.y = screenHeight / 2;
			}
			smallWindow.setLayoutParams(smallWindowParams);
			wm.addView(smallWindow, smallWindowParams);
		}

	}

	public void removeSmallWindow(Context context) {
		if (smallWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			smallWindow = null;
		}
	}

	public void createBigWindow(Context context) {
		WindowManager wm = getWindowManager(context);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		if (bigWindow == null) {
			bigWindow = LayoutInflater.from(context).inflate(
					R.layout.application_floatwindow_big, null);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				bigWindowParams.x = screenWidth / 2
						- FloatWindowBigView.viewWidth / 2;
				bigWindowParams.y = screenHeight / 2
						- FloatWindowBigView.viewHeight / 2;
				bigWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
				bigWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = 100;// FloatWindowBigView.viewWidth;
				bigWindowParams.height = 100;// FloatWindowBigView.viewHeight;
			}
			wm.addView(bigWindow, bigWindowParams);
		}
	}

	public void removeBigWindow(Context context) {
		if (bigWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(bigWindow);
			bigWindow = null;
		}
	}

	public boolean isWindowShowing() {
		return smallWindow != null || bigWindow != null;
	}

}
