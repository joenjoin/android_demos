package org.youdian.android_demos.location;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

public class MainActivity extends Activity {
	LocationManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		List<String> providers=lm.getAllProviders();
		for(String provider:providers){
			System.out.println("provider="+provider);
		}
	}
	
	

}
