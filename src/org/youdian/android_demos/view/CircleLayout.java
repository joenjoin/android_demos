package org.youdian.android_demos.view;

import org.youdian.android_demos.R;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CircleLayout extends FrameLayout implements OnClickListener {
	private static final int CHILD_COUNT = 7;
	public static final String TAG = "CircleLayout";
	ImageButton b1, b2, b3, b4, b5, b6, b7;

	public CircleLayout(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CircleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CircleLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		b1 = (ImageButton) findViewById(R.id.b1);
		b2 = (ImageButton) findViewById(R.id.b2);
		b3 = (ImageButton) findViewById(R.id.b3);
		b4 = (ImageButton) findViewById(R.id.b4);
		b5 = (ImageButton) findViewById(R.id.b5);
		b6 = (ImageButton) findViewById(R.id.b6);
		b7 = (ImageButton) findViewById(R.id.b7);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
	}

	/*
	 * MeasureSpec.EXACTLY = 1073741824 [0x40000000] MeasureSpec.AT_MOST =
	 * -2147483648 [0x80000000] MeasureSpec.UNSPECIFIED = 0 [0x0]
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int childCount = getChildCount();
		int maxWidth = 0;
		int maxHeight = 0;
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			LayoutParams params = (LayoutParams) childView.getLayoutParams();
			int childHeight = params.height;
			int childWidth = params.width;
			int topMargin = params.topMargin;
			int bottomMargin = params.bottomMargin;
			maxHeight += childHeight + topMargin + bottomMargin;
			// Log.d(TAG, "childWidth in onMeasure=" + childWidth);
			// Log.d(TAG, "childHeight in onMeasure=" + childHeight);
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,
					MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
					childHeight, MeasureSpec.EXACTLY);
			childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
		maxWidth = maxHeight;

		switch (widthMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.UNSPECIFIED:
			widthSize = maxWidth;
			widthMode = MeasureSpec.EXACTLY;
			break;
		case MeasureSpec.EXACTLY:
			break;

		}
		switch (heightMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.UNSPECIFIED:
			heightSize = maxHeight;
			heightMode = MeasureSpec.EXACTLY;
			break;
		case MeasureSpec.EXACTLY:
			break;

		}

		int finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
				widthMode);
		int finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
				heightMode);
		setMeasuredDimension(finalWidthMeasureSpec, finalHeightMeasureSpec);
		// Log.d(TAG, "widthMode="+widthMode+",widthSize="+widthSize);
		// Log.d(TAG, "heightMode="+heightMode+",heightSize="+heightSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onLayout");
		Log.d(TAG, l + ", " + t + ", " + r + ", " + b);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Point point = new Point(width / 2, height / 2);
		int radius = 75;
		// int totalHeight = 0;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();
			MarginLayoutParams params = (MarginLayoutParams) childView
					.getLayoutParams();
			int topMargin = params.topMargin;
			int leftMargin = params.leftMargin;
			int bottomMargin = params.bottomMargin;
			int rightMargin = params.rightMargin;
			// the left ,top position of childView
			int x = 0;
			int y = 0;
			switch (i) {
			case 0:
				x = point.x - radius / 2 - childWidth / 2;
				y = point.y - radius - childHeight / 2;
				break;
			case 1:
				x = point.x + radius / 2 - childWidth / 2;
				y = point.y - radius - childHeight / 2;
				break;
			case 2:
				x = point.x + radius - childWidth / 2;
				y = point.y - childHeight / 2;
				break;
			case 3:
				x = point.x + radius / 2 - childWidth / 2;
				y = point.y + radius - childHeight / 2;
				break;
			case 4:
				x = point.x - radius / 2 - childWidth / 2;
				y = point.y + radius - childHeight / 2;
				break;
			case 5:
				x = point.x - radius - childWidth / 2;
				y = point.y - childHeight / 2;
				break;
			case 6:
				x = point.x - childWidth / 2;
				y = point.y - childHeight / 2;
				break;
			default:
				throw new RuntimeException(
						"the child count must be less than 7");

			}
			Log.d(TAG, "margin=" + leftMargin + ", " + topMargin + ", "
					+ rightMargin + ", " + bottomMargin);
			// Log.d(TAG, "childWidth in onLayout=" + childWidth);
			// Log.d(TAG, "childHeight in onLayout=" + childHeight);
			childView.layout(x, y, x + childWidth, y + childHeight);
			// totalHeight += childHeight+topMargin+bottomMargin;
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, v.getId() + " clicked");
		switch (v.getId()) {
		case R.id.b1:
			b1.startDrag(ClipData.newPlainText("label", "text"), new DragShadowBuilder(b1), null, 0);
			break;
		case R.id.b2:
			break;
		case R.id.b3:
			break;
		case R.id.b4:
			break;
		case R.id.b5:
			break;
		case R.id.b6:
			break;
		}
	}

}
