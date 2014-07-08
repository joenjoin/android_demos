package org.youdian.android_demos.exception;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	String s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		if(s.equals("s")){
			
		}
		*/
		throwExceptionInAnotherThread();
	}

	private void throwExceptionInAnotherThread(){
		new Thread(){
			public void run(){
				if(s.equals("s")){
					
				}
			}
		}.start();
	}
}
