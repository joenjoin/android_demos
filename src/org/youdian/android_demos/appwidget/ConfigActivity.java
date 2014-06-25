package org.youdian.android_demos.appwidget;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

public class ConfigActivity extends Activity {
	private int mAppWidgetId;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_textview);
		setResult(RESULT_CANCELED);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			mAppWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(this);
			RemoteViews rv = new RemoteViews(this.getPackageName(),
					R.layout.appwidget_simple);
			rv.setTextViewText(R.id.tv, "configed text");

			// add click action for the button
			Intent clickIntent = new Intent(this, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.button, pendingIntent);
			// add collection view support
			Intent intent1 = new Intent(this, MyRemoteViewsService.class);
			intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
			rv.setRemoteAdapter(mAppWidgetId, R.id.list, intent1);
			// rv.setEmptyView(R.id.list, R.id.tv);

			// add item click action for the listview
			Intent itemClickIntent = new Intent(this, MyWidgetProvider.class);
			itemClickIntent.setAction(MyWidgetProvider.ITEM_CLICKED_ACTION);
			itemClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					mAppWidgetId);
			itemClickIntent.setData(Uri.parse(itemClickIntent
					.toUri(Intent.URI_INTENT_SCHEME)));
			PendingIntent itemPendingIntent = PendingIntent.getBroadcast(this,
					0, itemClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setPendingIntentTemplate(R.id.list, itemPendingIntent);

			appWidgetManager.updateAppWidget(mAppWidgetId, rv);
			Intent resultIntent = new Intent();
			resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					mAppWidgetId);
			setResult(RESULT_OK, resultIntent);
		}
		finish();
	}

}
