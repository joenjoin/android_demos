package org.youdian.android_demos.appwidget;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {
	public static final String EXTRA_ITEM = "extra_item";
	public static final String ITEM_CLICKED_ACTION = "org.youdian.android_demos.appwidget.simpleitem";

	@SuppressLint("NewApi")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			RemoteViews rv = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_simple);

			// add click action for the button
			Intent clickIntent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setOnClickPendingIntent(R.id.button, pendingIntent);
			// add collection view support
			Intent intent = new Intent(context, MyRemoteViewsService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					appWidgetIds[i]);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
			rv.setRemoteAdapter(appWidgetIds[i], R.id.list, intent);
			// rv.setEmptyView(R.id.list, R.id.tv);

			// add item click action for the listview
			Intent itemClickIntent = new Intent(context, MyWidgetProvider.class);
			itemClickIntent.setAction(ITEM_CLICKED_ACTION);
			itemClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					appWidgetIds[i]);
			itemClickIntent.setData(Uri.parse(itemClickIntent
					.toUri(Intent.URI_INTENT_SCHEME)));
			PendingIntent itemPendingIntent = PendingIntent.getBroadcast(
					context, 0, itemClickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			rv.setPendingIntentTemplate(R.id.list, itemPendingIntent);
			appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(ITEM_CLICKED_ACTION)) {
			int appWidgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			int position = intent.getIntExtra(EXTRA_ITEM, 0);
			Toast.makeText(context, "you clicked " + position,
					Toast.LENGTH_SHORT).show();
		}
		super.onReceive(context, intent);
	}

}
