package org.youdian.android_demos.application;

import java.lang.reflect.Field;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FloatWindowSmallView extends LinearLayout {

	/**
	 * 记录小悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录小悬浮窗的高度
	 */
	public static int viewHeight;

	/**
	 * 记录系统状态栏的高度
	 */
	private static int statusBarHeight;

	/**
	 * 用于更新小悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 * 小悬浮窗的参数
	 */
	private WindowManager.LayoutParams mParams;

	/**
	 * 记录当前手指位置在屏幕上的横坐标值
	 */
	private float xInScreen;

	/**
	 * 记录当前手指位置在屏幕上的纵坐标值
	 */
	private float yInScreen;

	/**
	 * 记录手指按下时在屏幕上的横坐标的值
	 */
	private float xDownInScreen;

	/**
	 * 记录手指按下时在屏幕上的纵坐标的值
	 */
	private float yDownInScreen;

	/**
	 * 记录手指按下时在小悬浮窗的View上的横坐标的值
	 */
	private float xInView;

	/**
	 * 记录手指按下时在小悬浮窗的View上的纵坐标的值
	 */
	private float yInView;

	private FloatWindowManager floatWindowManager;
	private ImageButton button;

	public FloatWindowSmallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		floatWindowManager = new FloatWindowManager();
		System.out.println("aaaa");
		// TODO Auto-generated constructor stub
	}

	public FloatWindowSmallView(Context context) {
		this(context, null);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		viewWidth = getWidth();
		viewHeight = getHeight();
		button = (ImageButton) this.findViewById(R.id.smallImage);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openBigWindow();
			}
		});
		Log.d("FloatWindow", "small window width=" + viewWidth);
		Log.d("FloatWindow", "small window height=" + viewHeight);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		boolean shouldIntercept=false;
		int action=ev.getAction();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			shouldIntercept=false;
			break;
		case MotionEvent.ACTION_MOVE:
			shouldIntercept=true;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			shouldIntercept=false;
			break;
		}
		return shouldIntercept;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		openBigWindow();
		Log.d("FloatWindow", "event in small window=" + event.getAction());

		 switch (event.getAction()) { 
		 case MotionEvent.ACTION_DOWN: // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度 
			 xInView = event.getX(); 
			 yInView =event.getY(); 
			 xDownInScreen = event.getRawX(); 
			 yDownInScreen =event.getRawY() - getStatusBarHeight(); 
			 xInScreen = event.getRawX();
			 yInScreen = event.getRawY() - getStatusBarHeight(); 
			 break; 
		 case MotionEvent.ACTION_MOVE: 
			 xInScreen = event.getRawX(); 
			 yInScreen =event.getRawY() - getStatusBarHeight(); // 手指移动的时候更新小悬浮窗的位置
			 updateViewPosition(); 
			 break; 
		  case MotionEvent.ACTION_UP: //如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。 //
			  if
			  (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
				  openBigWindow(); } 
			  break; 
			  default: 
				  break; 
		 } 
		return super.onTouchEvent(event);
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}

	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗。
	 */
	private void openBigWindow() {
		floatWindowManager.removeSmallWindow(getContext());
		floatWindowManager.createBigWindow(getContext());

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
