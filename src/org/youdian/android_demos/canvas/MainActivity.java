package org.youdian.android_demos.canvas;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	 private WaterRipplesView waterRipplesView;  
     
     
     
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	  
	          
	        waterRipplesView = new WaterRipplesView(this);  
	        setContentView(waterRipplesView);  
	    }  
}

