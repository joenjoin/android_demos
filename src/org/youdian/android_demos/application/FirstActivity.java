package org.youdian.android_demos.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FirstActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("first activity");
		setContentView(tv);
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(FirstActivity.this, SecondActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		/*
		 * onCreate(null); onStart(); try { Thread.sleep(500); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		super.onRestart();
	}

}
