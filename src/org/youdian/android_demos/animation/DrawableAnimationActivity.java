package org.youdian.android_demos.animation;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class DrawableAnimationActivity extends Activity {
	AnimationDrawable anim;
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_drawableanimation);
		iv = (ImageView) findViewById(R.id.iv);
		iv.setBackgroundResource(R.drawable.animation_drawableanimation);
		anim = (AnimationDrawable) iv.getBackground();
	}

	/*
	 * 如果要动画自动播放，应该在这里实现
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		anim.start();
	}

}
