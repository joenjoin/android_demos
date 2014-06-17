package org.youdian.android_demos.listview;

import org.youdian.android_demos.listview.SwipeHelper.Callback;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

/*
 * 滑动删除Item
 * 
 */
public class SwipeableListView extends ListView implements Callback {

	private SwipeHelper mSwipeHelper;
	private boolean mEnableSwipe = false;
	private OnItemSwipeListener mOnItemSwipeListener;

	public SwipeableListView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
		// TODO Auto-generated constructor stub
	}

	public SwipeableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		float densityScale = getResources().getDisplayMetrics().density;
		float pagingTouchSlop = ViewConfiguration.get(context)
				.getScaledPagingTouchSlop();
		mSwipeHelper = new SwipeHelper(context, SwipeHelper.X, this,
				densityScale, pagingTouchSlop);
		setItemsCanFocus(true);
	}

	public SwipeableListView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		float densityScale = getResources().getDisplayMetrics().density;
		mSwipeHelper.setDensityScale(densityScale);
		float pagingTouchSlop = ViewConfiguration.get(getContext())
				.getScaledPagingTouchSlop();
		mSwipeHelper.setPagingTouchSlop(pagingTouchSlop);
	}

	@Override
	public void requestLayout() {
		// TODO Auto-generated method stub
		checkRequestLayout(this);
		super.requestLayout();
	}

	public void checkRequestLayout(View v) {
		boolean inLayout = false;
		final View root = v.getRootView();
		if (root == null || v.isLayoutRequested()) {
			return;
		}
		final Error e = new Error();
		for (StackTraceElement ste : e.getStackTrace()) {
			if ("android.view.ViewGroup".equals(ste.getClassName())
					&& "layout".equals(ste.getMethodName())) {
				inLayout = true;
				break;
			}
		}
		if (inLayout && !v.isLayoutRequested()) {
			Log.d("SwipeableListView",
					"WARNING: in requestLayout during layout pass, view= " + v);
		}

	}

	public void enableSwipe(boolean enable) {
		mEnableSwipe = enable;
	}

	public boolean isSwipeEnabled() {
		return mEnableSwipe;
	}

	public void setOnItemSwipeListener(OnItemSwipeListener listener) {
		mOnItemSwipeListener = listener;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mEnableSwipe) {
			return mSwipeHelper.onInterceptTouchEvent(ev)
					|| super.onInterceptTouchEvent(ev);
		} else {
			return super.onInterceptTouchEvent(ev);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mEnableSwipe) {
			return mSwipeHelper.onTouchEvent(ev) || super.onTouchEvent(ev);
		} else {
			return super.onTouchEvent(ev);
		}

	}

	@Override
	public View getChildAtPosition(MotionEvent ev) {
		// TODO Auto-generated method stub
		final int count = getChildCount();
		int touchY = (int) ev.getY();
		int childIdx = 0;
		View slidingChild;
		for (; childIdx < count; childIdx++) {
			slidingChild = getChildAt(childIdx);
			if (slidingChild.getVisibility() == GONE) {
				continue;
			}
			if (touchY >= slidingChild.getTop()
					&& touchY <= slidingChild.getBottom()) {
				return slidingChild;
			}
		}
		return null;
	}

	@Override
	public View getChildContentView(View view) {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public void onScroll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canChildBeDismissed(View view) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onBeginDrag(View view) {
		// TODO Auto-generated method stub
		requestDisallowInterceptTouchEvent(true);
	}

	@Override
	public void onChildDismissed(final View view) {
		// TODO Auto-generated method stub
		if (view != null) {
			if (mOnItemSwipeListener != null) {
				mOnItemSwipeListener.onSwipe(view);
			}
		}
	}

	@Override
	public void onDragCancelled(View view) {
		// TODO Auto-generated method stub

	}

	/*
	 * private void redraw(View v) { int start = getFirstVisiblePosition();
	 * for(int i = start, j = getLastVisiblePosition(); i<=j ; i++) { if(v ==
	 * getItemAtPosition(i)) { View view=getChildAt(i-start);
	 * getAdapter().getView(i, view, this); } } }
	 */
	public interface OnItemSwipeListener {
		public void onSwipe(View view);
	}
}
