package org.youdian.android_demos.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ScaleableImageView extends ImageView {
	private static final String TAG="ScaleableImageView";
	private void log(String log){
		Log.d(TAG, log);
	}
	public ScaleableImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScaleableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ScaleableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	/*
	 * event.getActionMasked();适用于多点触摸情景
	 * event.getAction()适用于单点触摸情景
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action=event.getActionMasked();
		switch(action){
			case MotionEvent.ACTION_DOWN:
				log("Action_Down");
				break;
			/*
			 * 手指按着屏幕不动会一直触发Action_Move
			 */
			case MotionEvent.ACTION_MOVE:
				//log("Action_Move");
				break;
			case MotionEvent.ACTION_UP:
				log("Action_Up");
				break;
			case MotionEvent.ACTION_CANCEL:
				log("Action_Cancel");
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				log("Action_Pointer_Down");
				break;
			case MotionEvent.ACTION_POINTER_UP:
				log("Action_Pointer_Up");
				break;
				
		}
		return true;
	}
	

}
