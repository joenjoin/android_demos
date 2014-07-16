package org.youdian.android_demos.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView.SelectionBoundsAdjuster;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class HighlightLinearLayout extends LinearLayout implements SelectionBoundsAdjuster{
	public static final String TAG="HighlightLinearLayout";
	
	public HighlightLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HighlightLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void adjustListItemSelectionBounds(Rect bounds) {
		// TODO Auto-generated method stub
		Log.d(TAG, "original rect="+bounds);
	bounds.right-=200;
	}

}
