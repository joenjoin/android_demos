package org.youdian.android_demos.scroll;

import org.youdian.android_demos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerLayout extends LinearLayout {
	private static final int SCROLL_LENGTH = 100;
	private static final String TAG = "ScrollerView";
	Scroller mScroller;
	private float mDownX;
	private float mDownY;
	private float mLastX;
	private float mLastY;
	private int mTouchSlop;
	private boolean startMoving = false;
	ImageView iv;

	public ScrollerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public ScrollerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		iv = (ImageView) findViewById(R.id.iv);
		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mScroller.startScroll(mScroller.getFinalX(),
						mScroller.getCurrY(), -30, 0, 5000);
				invalidate();
			}
		});
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {
			Log.d(TAG, "scrolling");
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

}
