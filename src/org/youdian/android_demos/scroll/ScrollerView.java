package org.youdian.android_demos.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerView extends LinearLayout {
	private static final int SCROLL_LENGTH=100;
	private static final String TAG="ScrollerView";
	Scroller mScroller;
	private float mDownX;
	private float mDownY;
	private float mLastX;
	private float mLastY;
	private int mTouchSlop;
	private boolean startMoving=false;
	public ScrollerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScroller=new Scroller(context);
		mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public ScrollerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action=event.getAction();
		switch(action){
		case MotionEvent.ACTION_MOVE:
			if(startMoving){
				int  dy=(int) (event.getY()-mLastY);
				mScroller.startScroll(10, 10, 0, dy);
				Log.d(TAG, "moving");
			}
			break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d(TAG, "intercept");
		int action=ev.getAction();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			mDownX=ev.getX();
			mDownY=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:			
			mLastX=ev.getX();
			mLastY=ev.getY();
			if(Math.abs(mLastY-mDownY)>mTouchSlop*3){
				startMoving=true;
				Log.d(TAG, "startScroll="+startMoving);
			}
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mDownX=0;
			mDownY=0;
			mLastX=0;
			mLastY=0;
			startMoving=false;
			break;
		}
		return startMoving;
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(mScroller.computeScrollOffset()){
			Log.d(TAG, "scrolling");
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
		}
	}
	
	
}
