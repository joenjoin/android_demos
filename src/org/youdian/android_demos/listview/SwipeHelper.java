package org.youdian.android_demos.listview;

import org.youdian.android_demos.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SwipeHelper {

	private static final String TAG = "SwipeHelper";
	private static final boolean BEBUG_INVALIDATE = false;
	private static final boolean CONSTRAIN_SWIPE = true;
	private static final boolean FADE_OUT_DURING_SWIPE = true;
	private static final boolean DISMISS_IF_SWIPED_FAR_ENOUGH = true;
	private static final boolean LOG_SWIPE_DISMISS_VELOCITY = false;

	public static final int X = 0;
	public static final int Y = 1;

	private static LinearInterpolator sLinearInterceptor = new LinearInterpolator();

	private static int SWIPE_ESCAPE_VELOCITY = -1;
	private static int DEFAULT_ESCAPE_ANIMATIION_DURATION;
	private static int MAX_ESCAPE_ANIMATION_DURATION;
	private static int MAX_DISMISS_VELOCITY;
	private static int SNAP_ANIM_LEN;
	private static int DISMISS_ANIMATION_DURATION;
	private static int SWIPE_SCROLL_SLOP;
	private static float MIN_SWIPE;
	private static float MIN_VERT;
	private static float MIN_LOCK;

	public static float ALPHA_FADE_START = 0f;
	public static final float ALPHA_FADE_END = 0.7f;
	private static final float FACTOR = 1.2f;
	private static final int PROTECTION_PADDING = 50;

	private float mMinAlpha = 0.3f;

	private float mPagingTouchSlop;
	private final Callback mCallback;
	private final int mSwipeDirection;
	private final VelocityTracker mVelocityTracker;
	private float mInitialTouchPosX;
	private boolean mDragging;
	private View mCurrView;
	private View mCurrAnimView;
	private boolean mCanCurrViewBeDismissed;
	private float mDensitySCale;
	private float mLastY;
	private float mInitialTouchPosY;

	private float mStartAlpha;
	private boolean mProtected = false;

	public SwipeHelper(Context context, int swipeDirection, Callback callback,
			float densityScale, float pagingTouchSlop) {
		mCallback = callback;
		mSwipeDirection = swipeDirection;
		mVelocityTracker = VelocityTracker.obtain();
		mDensitySCale = densityScale;
		mPagingTouchSlop = pagingTouchSlop;
		if (SWIPE_ESCAPE_VELOCITY == -1) {
			Resources res = context.getResources();
			SWIPE_ESCAPE_VELOCITY = res
					.getInteger(R.integer.swipe_escape_velocity);
			DEFAULT_ESCAPE_ANIMATIION_DURATION = res
					.getInteger(R.integer.escape_animation_duration);
			MAX_ESCAPE_ANIMATION_DURATION = res
					.getInteger(R.integer.max_escape_animation_duration);
			MAX_DISMISS_VELOCITY = res
					.getInteger(R.integer.max_dismiss_velocity);
			SNAP_ANIM_LEN = res.getInteger(R.integer.snap_animation_duration);
			DISMISS_ANIMATION_DURATION = res
					.getInteger(R.integer.dismiss_animation_duration);
			SWIPE_SCROLL_SLOP = res.getInteger(R.integer.swipe_scroll_slop);

			MIN_SWIPE = res.getDimension(R.dimen.min_swipe);
			MIN_VERT = res.getDimension(R.dimen.min_vert);
			MIN_LOCK = res.getDimension(R.dimen.min_lock);
		}
	}

	public void setDensityScale(float densityScale) {
		mDensitySCale = densityScale;
	}

	public void setPagingTouchSlop(float pagingTouchSlop) {
		mPagingTouchSlop = pagingTouchSlop;
	}

	private float getVelocity(VelocityTracker vt) {
		return mSwipeDirection == X ? vt.getXVelocity() : vt.getYVelocity();
	}

	@SuppressLint("NewApi")
	private ObjectAnimator createTranslationAnimation(View v, float newPos) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(v,
				mSwipeDirection == X ? "translationX" : "translationY", newPos);
		return anim;

	}

	@SuppressLint("NewApi")
	private ObjectAnimator createDismissAnimation(View v, float newPos,
			int duration) {

		ObjectAnimator anim = createTranslationAnimation(v, newPos);
		anim.setInterpolator(sLinearInterceptor);
		anim.setDuration(duration);
		return anim;
	}

	private float getPerpendicularVelocity(VelocityTracker vt) {

		return mSwipeDirection == X ? vt.getYVelocity() : vt.getXVelocity();
	}

	@SuppressLint("NewApi")
	private void setTranslation(View v, float translate) {

		if (mSwipeDirection == X) {
			v.setTranslationX(translate);
		} else {
			v.setTranslationY(translate);
		}
	}

	private float getSize(View v) {

		return mSwipeDirection == X ? v.getMeasuredWidth() : v
				.getMeasuredHeight();
	}

	public void setMinAlpha(float minAlpha) {

		mMinAlpha = minAlpha;
	}

	@SuppressLint("NewApi")
	private float getAlphaForOffset(View view) {

		float viewSize = getSize(view);
		final float fadeSize = ALPHA_FADE_END * viewSize;
		float result = mStartAlpha;
		float pos = view.getTranslationX();
		if (pos >= viewSize * ALPHA_FADE_START) {
			result = mStartAlpha - (pos - viewSize * ALPHA_FADE_START)
					/ fadeSize;
		} else if (pos < viewSize * (mStartAlpha - ALPHA_FADE_START)) {

			result = mStartAlpha + (viewSize * ALPHA_FADE_START + pos)
					/ fadeSize;
		}

		return Math.max(mMinAlpha, result);
	}

	public static void invalidateGlobalRegion(View view) {

		invalidateGlobalRegion(view, new RectF(view.getLeft(), view.getTop(),
				view.getRight(), view.getBottom()));
	}

	@SuppressLint("NewApi")
	private static void invalidateGlobalRegion(View view, RectF childBounds) {
		// TODO Auto-generated method stub
		while (view.getParent() != null && view.getParent() instanceof View) {
			view = (View) view.getParent();
			view.getMatrix().mapRect(childBounds);
			view.invalidate((int) Math.floor(childBounds.left),
					(int) Math.floor(childBounds.top),
					(int) Math.ceil(childBounds.right),
					(int) Math.ceil(childBounds.bottom));

		}
	}

	@SuppressLint("NewApi")
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getY();
			mDragging = false;
			mCurrView = mCallback.getChildAtPosition(ev);
			mVelocityTracker.clear();
			if (mCurrView != null) {
				View button = mCurrView.findViewById(R.id.onoff);
				int left = button.getLeft();
				int top = button.getTop();
				View parent = (View) button.getParent();
				while (parent != null) {
					left += parent.getLeft();
					top += parent.getTop();
					if (parent.getParent() == parent.getRootView()) {
						parent = null;
					} else {
						parent = (View) parent.getParent();
					}
				}

				int bottom = top + button.getHeight() + PROTECTION_PADDING;
				top -= PROTECTION_PADDING;
				left -= PROTECTION_PADDING;

				float rawX = ev.getRawX();
				float rawY = ev.getRawY();

				if (rawX > left && rawY > top && rawY < bottom) {
					mProtected = true;
				} else {
					mProtected = false;
				}

				mCurrAnimView = mCallback.getChildContentView(mCurrView);
				mStartAlpha = mCurrAnimView.getAlpha();
				mCanCurrViewBeDismissed = mCallback
						.canChildBeDismissed(mCurrView);
				mVelocityTracker.addMovement(ev);
				mInitialTouchPosX = ev.getX();
				mInitialTouchPosY = ev.getY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mCurrView != null) {
				if (mLastY >= 0 && !mDragging) {
					float currY = ev.getY();
					float currX = ev.getX();
					float deltaY = Math.abs(currY - mInitialTouchPosY);
					float deltaX = Math.abs(currX - mInitialTouchPosX);
					if (deltaY > SWIPE_SCROLL_SLOP
							&& deltaY > (FACTOR * deltaX)) {

						mLastY = ev.getY();
						mCallback.onScroll();
						return false;
					}
				}
				mVelocityTracker.addMovement(ev);
				float pos = ev.getX();
				float delta = pos - mInitialTouchPosX;
				if (Math.abs(delta) > mPagingTouchSlop) {
					mCallback.onBeginDrag(mCallback
							.getChildContentView(mCurrView));
					mDragging = true;
					mInitialTouchPosX = ev.getX()
							- mCurrAnimView.getTranslationX();
					mInitialTouchPosY = ev.getY();
				}
			}
			mLastY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mDragging = false;
			mCurrView = null;
			mCurrAnimView = null;
			mLastY = -1;
			break;

		}
		Log.d(TAG, "mDragging=" + mDragging);
		return mDragging;
	}

	@SuppressLint("NewApi")
	public void dismissChild(final View view, float velocity) {

		final View animView = mCallback.getChildContentView(view);
		final boolean canAnimViewBeDismissed = mCallback
				.canChildBeDismissed(view);
		float newPos = determinePos(animView, velocity);
		int duration = determineDuration(animView, newPos, velocity);

		animView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		ObjectAnimator anim = createDismissAnimation(animView, newPos, duration);
		anim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				mCallback.onChildDismissed(mCurrView);
				animView.setLayerType(View.LAYER_TYPE_NONE, null);

			}

		});
		anim.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				if (FADE_OUT_DURING_SWIPE && canAnimViewBeDismissed) {
					animView.setAlpha(getAlphaForOffset(animView));
				}
				invalidateGlobalRegion(animView);
			}

		});
		anim.start();
	}

	@SuppressLint("NewApi")
	private int determineDuration(View animView, float newPos, float velocity) {
		// TODO Auto-generated method stub
		int duration = MAX_ESCAPE_ANIMATION_DURATION;
		if (velocity != 0) {
			duration = Math
					.min(duration, (int) (Math.abs(newPos
							- animView.getTranslationX()) * 1000f / Math
							.abs(velocity)));
		} else {
			duration = DEFAULT_ESCAPE_ANIMATIION_DURATION;
		}
		return duration;
	}

	@SuppressLint("NewApi")
	private float determinePos(View animView, float velocity) {
		// TODO Auto-generated method stub
		float newPos = 0;
		if (velocity < 0
				|| (velocity == 0 && animView.getTranslationX() < 0)
				|| (velocity == 0 && animView.getTranslationX() == 0 && mSwipeDirection == Y)) {

			newPos = -getSize(animView);
		} else {
			newPos = getSize(animView);
		}
		return newPos;
	}

	@SuppressLint("NewApi")
	public void snapChild(final View view, float velocity) {
		final View animView = mCallback.getChildContentView(view);
		final boolean canAnimViewBeDismissed = mCallback
				.canChildBeDismissed(view);
		ObjectAnimator anim = createTranslationAnimation(animView, 0);
		int duration = SNAP_ANIM_LEN;
		anim.setDuration(duration);
		anim.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				if (FADE_OUT_DURING_SWIPE && canAnimViewBeDismissed) {
					animView.setAlpha(getAlphaForOffset(animView));
				}
				invalidateGlobalRegion(animView);
			}

		});

		anim.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				animView.setAlpha(mStartAlpha);
				mCallback.onDragCancelled(mCurrView);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		anim.start();
	}

	@SuppressLint("NewApi")
	public boolean onTouchEvent(MotionEvent ev) {
		if (!mDragging || mProtected) {
			return false;
		}
		mVelocityTracker.addMovement(ev);
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_MOVE:
			if (mCurrView != null) {
				float deltaX = ev.getX() - mInitialTouchPosX;
				float deltaY = Math.abs(ev.getY() - mInitialTouchPosY);
				if (!mDragging && deltaY > MIN_VERT
						&& (Math.abs(deltaX)) < MIN_LOCK
						&& deltaY > (FACTOR * Math.abs(deltaX))) {
					mCallback.onScroll();
					return false;
				}
				float minDistance = MIN_SWIPE;
				if (Math.abs(deltaX) < minDistance) {
					return true;
				}

				if (CONSTRAIN_SWIPE
						&& !mCallback.canChildBeDismissed(mCurrView)) {
					float size = getSize(mCurrAnimView);
					float maxScrollDistance = 0.15f * size;
					if (Math.abs(deltaX) >= size) {
						deltaX = deltaX > 0 ? maxScrollDistance
								: -maxScrollDistance;
					} else {
						deltaX = maxScrollDistance
								* (float) Math.sin((deltaX / size)
										* (Math.PI / 2));
					}
				}
				Log.d(TAG, "translation changed");
				setTranslation(mCurrAnimView, deltaX);
				if (FADE_OUT_DURING_SWIPE && mCanCurrViewBeDismissed) {
					mCurrAnimView.setAlpha(getAlphaForOffset(mCurrAnimView));
				}
				invalidateGlobalRegion(mCallback.getChildContentView(mCurrView));
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (mCurrView != null) {
				float maxVelocity = MAX_DISMISS_VELOCITY * mDensitySCale;
				mVelocityTracker.computeCurrentVelocity(1000 /* px/sec */,
						maxVelocity);
				float escapeVelocity = SWIPE_ESCAPE_VELOCITY * mDensitySCale;
				float velocity = getVelocity(mVelocityTracker);
				float perpendicularVelocity = getPerpendicularVelocity(mVelocityTracker);
				float translation = Math.abs(mCurrAnimView.getTranslationX());
				float currAnimViewSize = getSize(mCurrAnimView);
				boolean childSwipedFarEnough = DISMISS_IF_SWIPED_FAR_ENOUGH
						&& translation > 0.4 * currAnimViewSize;
				boolean childSwipedFastEnough = (Math.abs(velocity) > escapeVelocity)
						&& (Math.abs(velocity) > Math
								.abs(perpendicularVelocity))
						&& (velocity > 0) == (mCurrAnimView.getTranslationX() > 0)
						&& translation > 0.05 * currAnimViewSize;
				boolean dismissChild = mCallback.canChildBeDismissed(mCurrView)
						&& (childSwipedFastEnough || childSwipedFarEnough);
				if (dismissChild) {
					dismissChild(mCurrView, childSwipedFastEnough ? velocity
							: 0f);
				} else {
					snapChild(mCurrView, velocity);
				}
			}
			break;
		}
		return true;
	}

	public interface Callback {
		View getChildAtPosition(MotionEvent ev);

		View getChildContentView(View view);

		void onScroll();

		boolean canChildBeDismissed(View view);

		void onBeginDrag(View view);

		void onChildDismissed(View view);

		void onDragCancelled(View view);
	}

}
