package org.youdian.android_demos.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity{
	TextView tv;
	private static final String HTTPS_URL="https://w.mail.qq.com/cgi-bin/loginpage?f=xhtml";
	private static final String HTTP_URL="http://www.baidu.com";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_textview);
		tv=(TextView)findViewById(R.id.tv);
		Log.d("HttpUtils", "request 1 start");
		new MyAsyncTask().execute(HTTP_URL+"?o=1");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("HttpUtils", "request 2 start");
		new MyAsyncTask().execute(HTTP_URL+"?o=2");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("HttpUtils", "request 3 start");
		new MyAsyncTask().execute(HTTP_URL+"?o=3");

	}

	class MyAsyncTask extends AsyncTask<String,Void,String>{
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			tv.setText(result);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			for(String url :params){
				InputStream in=HttpUtils.get(url);
				if(in!=null){
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuffer sb=new StringBuffer();
					String line=null;
					try {
						while((line=reader.readLine())!=null){
							sb.append(line);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return sb.toString();
				}
			}
			
			return null;
		}
		
	}
}
