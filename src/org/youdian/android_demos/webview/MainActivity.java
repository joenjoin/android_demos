package org.youdian.android_demos.webview;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG="WebView";
	WebView web;
	private Handler mHandler=new Handler();
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		String url = "http://www.baidu.com";
		setContentView(R.layout.activity_webview_main);
		web = (WebView) findViewById(R.id.wv);
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

		});
		web.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				MainActivity.this.setProgress(newProgress * 1000);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				Log.d(TAG, "message="+message);
				//return true;
				return false;
			}
			

		});
		web.loadUrl("file:///android_asset/demo.html");
		/*
		web.addJavascriptInterface(new Object() {       
            public void clickOnAndroid() {       
                mHandler.post(new Runnable() {       
                    public void run() {       
                        web.loadUrl("javascript:alert('hello world')");       
                    }       
                });       
            }       
        }, "demo");  */
		web.addJavascriptInterface(new JsInterface(), "toast");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private class JsInterface{
		public void showToast(){
			System.out.println("hello Toast");
			Toast.makeText(MainActivity.this, "toast", Toast.LENGTH_LONG).show();
		}
		
		public void printText(String message){
			System.out.println("Message ="+message);
		}
		//调用js函数
		public void invokeJs(){
			web.loadUrl("javascript:sayHello()");
		}
	}

}
