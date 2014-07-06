package org.youdian.android_demos.application;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindowSmallView extends LinearLayout {

	public static int viewWidth;

	public static int viewHeight;

	private static int statusBarHeight;

	private WindowManager windowManager;

	private WindowManager.LayoutParams mParams;

	private float xInScreen;

	private float yInScreen;

	private float xDownInScreen;

	private float yDownInScreen;

	private float screenWidth;
	private float screenHeight;

	/**
	 * 记录手指按下时在小悬浮窗的View上的横坐标的值
	 */
	private float xInView;

	/**
	 * 记录手指按下时在小悬浮窗的View上的纵坐标的值
	 */
	private float yInView;

	private FloatWindowManager floatWindowManager;

	public FloatWindowSmallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		floatWindowManager = FloatWindowManager.getInstance();
		// TODO Auto-generated constructor stub
	}

	public FloatWindowSmallView(Context context) {
		this(context, null);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		Log.d(FloatWindowManager.TAG, "onFinishInflate");
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.d(FloatWindowManager.TAG, "onDraw");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(FloatWindowManager.TAG, "onMeasure");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		Log.d(FloatWindowManager.TAG, "onLayout");
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		boolean shouldIntercept = false;
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			shouldIntercept = false;
			break;
		case MotionEvent.ACTION_MOVE:
			shouldIntercept = true;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			shouldIntercept = false;
			break;
		}
		return shouldIntercept;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("FloatWindow", "event in small window=" + event.getAction());
		xInScreen = event.getRawX();
		yInScreen = event.getRawY() - getStatusBarHeight();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();

			break;
		case MotionEvent.ACTION_MOVE:
			updateViewPosition(false);
			break;
		case MotionEvent.ACTION_UP: // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
									// //
			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
				openBigWindow();
			} else {
				updateViewPosition(true);
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
		viewWidth = mParams.width;
		viewHeight = mParams.height;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager manager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition(boolean isViewReleased) {
		if (isViewReleased) {
			int currX = mParams.x;
			int centerX = currX + viewWidth / 2;
			if (centerX < screenWidth / 2)
				mParams.x = 1;
			else
				mParams.x = (int) (screenWidth - viewWidth) - 1;
		} else {
			mParams.x = (int) (xInScreen - xInView);
			mParams.y = (int) (yInScreen - yInView);
		}

		windowManager.updateViewLayout(this, mParams);
	}

	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗。
	 */
	private void openBigWindow() {
		floatWindowManager.createBigWindow(getContext());
		floatWindowManager.removeSmallWindow(getContext());

	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d("FloatWindow", "statusbarHeight=" + statusBarHeight);
		return statusBarHeight;
	}
}
