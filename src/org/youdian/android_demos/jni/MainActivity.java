package org.youdian.android_demos.jni;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	public native String hello();
	
	static{
		
		System.loadLibrary("hello_jni");
	}

}
