package org.youdian.android_demos.touch;

import org.youdian.android_demos.R;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class DragLayout extends LinearLayout {
	private static final String TAG = "DragLayout";
	ViewDragHelper mDragHelper;
	View mChild;

	public DragLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
	}

	class DragHelperCallback extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View view, int pointetId) {
			// TODO Auto-generated method stub
			log("tryCaptureView");
			return mChild == view;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// TODO Auto-generated method stub
			log("clampViewPositionHorizontal");
			// return super.clampViewPositionHorizontal(child, left, dx);
			return child.getLeft() + dx;
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			// TODO Auto-generated method stub
			log("clampViewPositionVertical");
			// return super.clampViewPositionVertical(child, top, dy);
			return child.getTop() + dy;
		}

		@Override
		public int getOrderedChildIndex(int index) {
			// TODO Auto-generated method stub
			log("getOrderedChildIndex");
			return super.getOrderedChildIndex(index);
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			// TODO Auto-generated method stub
			log("getViewHorizontalDragRange");
			return super.getViewHorizontalDragRange(child);
		}

		@Override
		public int getViewVerticalDragRange(View child) {
			// TODO Auto-generated method stub
			log("getViewVerticalDragRange");
			return super.getViewVerticalDragRange(child);
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			// TODO Auto-generated method stub
			log("onEdgeDragStarted");
			super.onEdgeDragStarted(edgeFlags, pointerId);
		}

		@Override
		public boolean onEdgeLock(int edgeFlags) {
			// TODO Auto-generated method stub
			log("onEdgeLock");
			return super.onEdgeLock(edgeFlags);
		}

		@Override
		public void onEdgeTouched(int edgeFlags, int pointerId) {
			// TODO Auto-generated method stub
			log("onEdgeTouched");
			super.onEdgeTouched(edgeFlags, pointerId);
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			// TODO Auto-generated method stub
			log("onViewCaptured");
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public void onViewDragStateChanged(int state) {
			// TODO Auto-generated method stub
			log("onViewDragStateChanged");
			super.onViewDragStateChanged(state);
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// TODO Auto-generated method stub
			log("onViewPositionChanged");
			// super.onViewPositionChanged(changedView, left, top, dx, dy);
			invalidate();
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// TODO Auto-generated method stub
			log("onViewReleased");
			// super.onViewReleased(releasedChild, xvel, yvel);
			mDragHelper.settleCapturedViewAt(0, 0);
			invalidate();
		}

	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(mDragHelper.continueSettling(true))
			ViewCompat.postInvalidateOnAnimation(this);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		mChild = findViewById(R.id.child1);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		final int action=MotionEventCompat.getActionMasked(ev);
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
	            mDragHelper.cancel();
	            return false;
	    }
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mDragHelper.processTouchEvent(event);
		return true;
	}

	private void log(String log) {
		Log.d(TAG, log);
	}

}
