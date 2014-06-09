package org.youdian.android_demos.webview;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		String url="http://www.baidu.com";
		setContentView(R.layout.activity_webview_main);
		web=(WebView)findViewById(R.id.wv);
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
			
			
		});
		web.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				MainActivity.this.setProgress(newProgress*1000);
			}
			
		});
	web.loadUrl(url);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if((keyCode==KeyEvent.KEYCODE_BACK)&&web.canGoBack()){
			web.goBack();
			return true;
		}
		return false;
	}
	
	

}
