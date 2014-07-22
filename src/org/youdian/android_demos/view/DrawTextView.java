package org.youdian.android_demos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Draw some text in the center of the view
 * @author changjian
 *
 */
public class DrawTextView extends View{
	Paint mTextPaint;
	String text="EEEEEEEEEEEEEEEEE";
	public static final String TAG="DrawTextView";
	public DrawTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTextPaint=new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(80);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		textInCenter(canvas);
	}
	/**
	 * draw the text in the center of the view
	 * 
	 */
	private void textInCenter(Canvas canvas) {	
		mTextPaint.setColor(Color.RED);
		canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, mTextPaint);
		canvas.drawLine(getWidth()/2, 0, getWidth()/2, getHeight(), mTextPaint);
		float textWidth=mTextPaint.measureText(text);
		FontMetrics metrics=mTextPaint.getFontMetrics();
		Log.d(TAG, "textWidth="+textWidth);
		float x=(getWidth()-textWidth)/2;
		float y=(getHeight()-(metrics.bottom+metrics.top))/2;	
		mTextPaint.setColor(Color.WHITE);
		canvas.drawText(text, x, y, mTextPaint);
	}
	

}
