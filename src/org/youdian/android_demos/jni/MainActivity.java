package org.youdian.android_demos.jni;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_textview);
		tv=(TextView)findViewById(R.id.tv);
		String text=hello();
		tv.setText(text);
	}
	public native String hello();
	
	static{
		
		System.loadLibrary("hellojni");
	}

}
