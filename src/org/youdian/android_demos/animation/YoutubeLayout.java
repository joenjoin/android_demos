package org.youdian.android_demos.animation;

import org.youdian.android_demos.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class YoutubeLayout extends ViewGroup{

    private static final String TAG = "YoutubeLayout";

    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    private View mHeaderView;
    private View mDescView;

    private float mInitialMotionX;
    private float mInitialMotionY;

    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    private ViewDragHelper mDragHelper;



    public YoutubeLayout(Context context) {
        super(context);
        init(context);
    }

    public YoutubeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YoutubeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        final float density = context.getResources().getDisplayMetrics().density;
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
    }

    public void maximize()
    {
        smoothSlideTo(0.0f);
    }

    public void minimize()
    {
        smoothSlideTo(1.0f);
    }

    private boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);

        if(mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y))
        {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFInishInflate");
        mHeaderView = findViewById(R.id.viewHeader);
        mDescView   = findViewById(R.id.viewDesc);
    }

    @SuppressLint("NewApi")
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, "onMeasure");
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0), resolveSizeAndState(maxHeight, heightMeasureSpec, 0));

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentViewHeight = getHeight();
        int dragViewHeight = mHeaderView.getMeasuredHeight();
        mDragRange = parentViewHeight - dragViewHeight;
//        Log.d(TAG, "onLayout:" + "changed:" + changed + ",l:" + l + ",t:" + t + ",r:" + r + ",b:" + b + ",mDragRange" + mDragRange);

        mHeaderView.layout(
                0,
                mTop,
                r,
                mTop + mHeaderView.getMeasuredHeight());

        mDescView.layout(
                0,
                mTop + mHeaderView.getMeasuredHeight(),
                r,
                mTop  + b);
    }

    private class DragHelperCallback extends ViewDragHelper.Callback
    {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @SuppressLint("NewApi")
		@Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//            Log.i(TAG, "onViewPositionChanged:" + "left:" + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy);
            mTop = top;
            mDragOffset = (float) top / mDragRange;

//            Log.i(TAG, "onViewPositionChanged:" + "mDragOffset:" + mDragOffset);

            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);

            mDescView.setAlpha(1 - mDragOffset);

            requestLayout();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
//            Log.i(TAG, "onViewReleased:" + "xvel:" + xvel + ",yvel:" + yvel);
            //yvel Fling浜х敓鐨勫�硷紝yvel > 0 鍒欐槸蹇�熷線涓婩ling || yvel < 0 鍒欐槸蹇�熷線涓奆ling

            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.4f)/* 鍚庨潰杩欎釜灏忔嫭鍙烽噷鍒ゆ柇澶勭悊鎷栧姩涔嬪悗鍋滀笅鏉ヤ絾鏄湭鏉炬墜鐨勬儏鍐� */) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
            invalidate();//important 涓嶅姞锛屽氨涓嶄細鍒锋柊View鐨勪綅缃� 灏肩帥銆傘�傘�傘�備綘璇曡瘯娉ㄩ噴鎺夌湅鐪嬨�傚紕姝绘垜浜嗭紝鐫¤銆�
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true))
        {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if(action != MotionEvent.ACTION_DOWN)
        {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP)
        {
            mDragHelper.cancel();
            return false;
        }

        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;

        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;

            case MotionEvent.ACTION_MOVE:
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mDragHelper.getTouchSlop();
                if(ady > slop && adx > ady)
                {
                    mDragHelper.cancel();
                    return false;
                }
                break;
        }

        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);

        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        //瑙︽懜鐐规槸鍚﹁惤鍦℉eaderView涓�
        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int)x, (int)y);

        switch(action & MotionEventCompat.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
            {
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final float slop = mDragHelper.getTouchSlop();

//                Log.i(TAG, "dx * dx + dy * dy = " + dx * dx + dy * dy);
//                Log.i(TAG, "slop * slop = " + slop * slop);
//                Log.i(TAG, "mDragOffset:" + mDragOffset);
                if(dx * dx + dy * dy < slop * slop && isHeaderViewUnder)
                {
                    if (mDragOffset == 0) {
                        minimize();
                    } else {
                        maximize();
                    }
                }
                break;
            }
        }

        return isHeaderViewUnder && isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }
}
