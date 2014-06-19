package org.youdian.android_demos.application;

import org.youdian.android_demos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class FloatWindowBigView extends FrameLayout {

	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;

	private FloatWindowManager floatWindowManager;

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
	}

	public FloatWindowBigView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(
				R.layout.application_floatwindow_big, this);
		View view = findViewById(R.id.bigView);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

	}
}
