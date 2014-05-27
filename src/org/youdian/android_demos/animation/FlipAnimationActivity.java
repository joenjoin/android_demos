package org.youdian.android_demos.animation;

import org.youdian.android_demos.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/*
 * 翻转动画，两个View切换时显示翻转效果
 */
public class FlipAnimationActivity extends Activity {
	
	TextView view1,view2;
	private static final int VIEW_IN_DURATION=1000;
	private static final int VIEW_OUT_DURATION=5000;
	boolean b=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_flip);
		view1=(TextView)findViewById(R.id.view1);
		view2=(TextView)findViewById(R.id.view2);
	}
	
	public void flip(View view){
		if(b){
			startOpenAnimator(view1);
			startCloseAnimator(view2);
		}else{
			startOpenAnimator(view2);
			startCloseAnimator(view1);
		}
		b=!b;
	}
	
	@SuppressLint("NewApi")
	public Animator startCloseAnimator(View v){
		final View view=v;
		Animator anim=ObjectAnimator.ofFloat(v, View.SCALE_X, 1f,0f).setDuration(VIEW_OUT_DURATION);
		anim.addListener(new AnimatorListener(){

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				view.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		anim.start();
		return anim;
	}
	
	@SuppressLint("NewApi")
	public Animator startOpenAnimator(View v){
		final View view=v;
		
		Animator anim=ObjectAnimator.ofFloat(v, View.SCALE_X, 0f,1f).setDuration(VIEW_IN_DURATION);
		anim.setStartDelay(VIEW_OUT_DURATION);
		anim.addListener(new AnimatorListener(){

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				view.setVisibility(View.VISIBLE);
			}
			
		});
		anim.start();
		return anim;
	}
}
