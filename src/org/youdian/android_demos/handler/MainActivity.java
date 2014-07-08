package org.youdian.android_demos.handler;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
/*
 * Handler的用法
 * 使用静态内部类
 * 使用WeakReference引用外部类
 */
public class MainActivity extends Activity {
	MyHandler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mHandler=new MyHandler(this);
		mHandler.sendEmptyMessage(0);
	}
	
	private void toast(String msg){
		Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();	
	}
	static class MyHandler extends Handler{
		private WeakReference<MainActivity> mParent;
		
		public MyHandler(MainActivity activity){
			mParent=new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);

				MainActivity mActivity=mParent.get();
				mActivity.toast(String.valueOf(msg.what));
		}
		
	}
}
