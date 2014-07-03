package org.youdian.android_demos.application;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.MeasureSpec;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.view.View.OnClickListener;
public class FloatWindowBigView extends FrameLayout implements OnClickListener,
	OnLongClickListener{

	private static final int CHILD_COUNT = 7;
	public static final String TAG = "CircleLayout";
	ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
	
	private FloatWindowManager mFloatWindowManager;
	
	public FloatWindowBigView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FloatWindowBigView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		mFloatWindowManager=FloatWindowManager.getInstance();
		// TODO Auto-generated constructor stub
	}

	public FloatWindowBigView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
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
		b8 = (ImageButton) findViewById(R.id.b8);
		b9 = (ImageButton) findViewById(R.id.b9);
		b1.setTag("b1");
		b2.setTag("b2");
		b3.setTag("b3");
		b4.setTag("b4");
		b5.setTag("b5");
		b6.setTag("b6");
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		// b7.setOnClickListener(this);

		OnLongClickListener onLongClickListener = new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				v.startDrag(ClipData.newPlainText("id", v.getTag().toString()),
						new DragShadowBuilder(v), null, 0);
				return true;
			}
		};
		b1.setOnLongClickListener(onLongClickListener);
		b2.setOnLongClickListener(onLongClickListener);
		b3.setOnLongClickListener(onLongClickListener);
		b4.setOnLongClickListener(onLongClickListener);
		b5.setOnLongClickListener(onLongClickListener);
		b6.setOnLongClickListener(onLongClickListener);
		b7.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				final int action = event.getAction();
				switch (action) {
				case DragEvent.ACTION_DRAG_STARTED:
					if (event.getClipDescription().hasMimeType(
							ClipDescription.MIMETYPE_TEXT_PLAIN))
						return true;
					break;
				case DragEvent.ACTION_DROP:
					ClipData clipData = event.getClipData();
					String data = (String) clipData.getItemAt(0).getText();
					Toast.makeText(getContext(), "Dragged data is " + data,
							Toast.LENGTH_LONG).show();
					return true;
				case DragEvent.ACTION_DRAG_ENDED:
					boolean result = event.getResult();
					if (result)
						Toast.makeText(getContext(), "successful handled",
								Toast.LENGTH_LONG).show();
					break;
				}

				return false;
			}
		});
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
		// the center point of the circle
		Point point = new Point(width / 2, height / 2);
		int radius = 100;
		int smallRadius = getMeasuredWidth() / 4;
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
			case 7:
				x = point.x - childWidth / 2;
				y = point.y - smallRadius + 10; // 5 is a margin from the top
				break;
			case 8:
				x = point.x - childWidth / 2;
				y = point.y + smallRadius - childHeight - 15;
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

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Resources res = getContext().getResources();
		Drawable darkBg = res
				.getDrawable(R.drawable.shape_view_circlelayout_dark_circle_bg);
		darkBg.setBounds(0, 0, width, height);
		darkBg.draw(canvas);
		Drawable lightBg = res
				.getDrawable(R.drawable.shape_view_circlelayout_ring_bg);

		lightBg.setBounds(width / 4, height / 4, width / 4 * 3, height / 4 * 3);
		lightBg.draw(canvas);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, v.getId() + " clicked");
		switch (v.getId()) {
		case R.id.b1:
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

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			mFloatWindowManager.removeBigWindow(getContext());
			mFloatWindowManager.createSmallWindow(getContext());
		}
			
		return super.onKeyDown(keyCode, event);
	}
	
	
}
