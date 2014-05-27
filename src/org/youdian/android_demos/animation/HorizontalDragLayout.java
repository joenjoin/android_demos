package org.youdian.android_demos.animation;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/** Created by wuyexiong on 13-8-31. */
public class HorizontalDragLayout extends LinearLayout{

    private static final String TAG = "HorizontalDragLayout";

    private ViewDragHelper mDragHelper;
    private View mDragView;

    public HorizontalDragLayout(Context context) {
        super(context);
        init();
    }

    public HorizontalDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalDragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
    }

    private class DragHelperCallback extends ViewDragHelper.Callback
    {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {//杩斿洖true鍒欒〃绀哄惎鍔ㄦ嫋鎷�
            return child == mDragView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {//閲嶆柊璁＄畻褰撳墠瀛怴iew鐨刋鍧愭爣
            Log.d(TAG, "clampViewPositionHorizontal " + left + "," + dx);
            final int leftBound  = getPaddingLeft();
            final int rightBound = getWidth() - mDragView.getWidth() - leftBound;
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {//閲嶆柊璁＄畻褰撳墠瀛怴iew鐨刌鍧愭爣
            Log.d(TAG, "clampViewPositionVertical " + top + "," + dy);
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mDragView.getHeight() - topBound;
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            return newTop;
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {//瑙﹀彂瑙掓帴鏀跺埌浜嗚Е鎽镐簨浠�
            super.onEdgeTouched(edgeFlags, pointerId);
            Toast.makeText(getContext(), "edgeTouched:" + edgeFlags, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mDragView,pointerId);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean mINeedTouch = true;
        mDragHelper.processTouchEvent(event);
        return mINeedTouch;
    }
}
