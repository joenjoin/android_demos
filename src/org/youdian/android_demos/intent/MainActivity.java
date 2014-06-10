package org.youdian.android_demos.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Person p=new Person(1,"xiaoming",1355);
		Intent i=new Intent();
		i.setClass(this, SecondActivity.class);
		i.putExtra("p", p);
		startActivity(i);
		finish();
	}
	
	

}
