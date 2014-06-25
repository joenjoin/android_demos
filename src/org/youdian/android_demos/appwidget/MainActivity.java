package org.youdian.android_demos.appwidget;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_textview);
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 手动更新AppWidget
				RemoteViews rv = new RemoteViews(MainActivity.this
						.getPackageName(), R.layout.appwidget_simple);
				rv.setTextViewText(R.id.tv, "new text");
				// 更新listview的内容
				int[] widgetIds = AppWidgetManager.getInstance(
						MainActivity.this).getAppWidgetIds(
						new ComponentName(MainActivity.this,
								MyWidgetProvider.class));
				AppWidgetManager.getInstance(MainActivity.this)
						.notifyAppWidgetViewDataChanged(widgetIds, R.id.list);
				// 更新textview的内容
				// AppWidgetManager.getInstance(MainActivity.this).updateAppWidget(new
				// ComponentName(MainActivity.this, MyWidgetProvider.class),
				// rv);
			}
		});
	}

}
