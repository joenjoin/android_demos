package org.youdian.android_demos.application;

import org.youdian.android_demos.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatWindowManager {
	private LayoutParams bigWindowParams;
	static WindowManager mWindowManager;
	private FloatWindowSmallView smallWindow;
	private View bigWindow;
	private LayoutParams smallWindowParams;
	public static final String TAG = "FloatWindow";

	public WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Log.d(TAG, "windowManager is null");
		}

		return mWindowManager;
	}

	private static FloatWindowManager floatWindowManager;

	public static FloatWindowManager getInstance() {
		if (floatWindowManager == null)
			floatWindowManager = new FloatWindowManager();
		return floatWindowManager;
	}

	public void createSmallWindow(Context context) {
		WindowManager wm = getWindowManager(context);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		if (smallWindow == null) {
			smallWindow = (FloatWindowSmallView) LayoutInflater.from(context)
					.inflate(R.layout.view_application_floatwindow_small, null);
			Log.d(FloatWindowManager.TAG, "layout inflate");
			smallWindow.forceLayout();
			int width = smallWindow.getMeasuredWidth();
			Log.d("FloatWindow", "measured width=" + width);
			if (smallWindowParams == null) {
				smallWindowParams = new LayoutParams();
				smallWindowParams.type = LayoutParams.TYPE_PHONE;
				smallWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL// ;
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				smallWindowParams.width = 100;// smallWindow.getMeasuredWidth();//
												// WindowManager.LayoutParams.WRAP_CONTENT;
				smallWindowParams.height = 100;// smallWindow.getMeasuredHeight();//
												// WindowManager.LayoutParams.WRAP_CONTENT;
				smallWindowParams.x = 1;
				smallWindowParams.y = screenHeight / 2;
			}
			smallWindow.setParams(smallWindowParams);
			wm.addView(smallWindow, smallWindowParams);
		}

	}

	public void removeSmallWindow(Context context) {
		Log.d(TAG, "smallWindow=" + smallWindow);
		if (smallWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			smallWindow = null;
			Log.d(TAG, "removeSmallWindow");
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
					R.layout.view_application_floatwindow_big, null);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				bigWindowParams.x = screenWidth / 2 - 300 / 2;
				bigWindowParams.y = screenHeight / 2 - 300 / 2;
				bigWindowParams.type = LayoutParams.TYPE_PHONE;
				bigWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = 300;// FloatWindowBigView.viewWidth;
				bigWindowParams.height = 300;// FloatWindowBigView.viewHeight;
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
