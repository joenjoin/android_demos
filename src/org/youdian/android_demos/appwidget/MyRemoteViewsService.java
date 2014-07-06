package org.youdian.android_demos.appwidget;

import java.util.ArrayList;
import java.util.List;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/*
 * android 3.0以后支持collection view ，如ListView ,GridView ,StackView ,AdapterViewFlipper
 */
@SuppressLint("NewApi")
public class MyRemoteViewsService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new MyRemoteViewsFactory(this.getApplicationContext(), intent);
	}

	class MyRemoteViewsFactory implements RemoteViewsFactory {
		private List<String> items = new ArrayList<String>();
		private Context context;
		private int appWidgetId;

		public MyRemoteViewsFactory(Context applicationContext, Intent intent) {
			// TODO Auto-generated constructor stub
			context = applicationContext;
			appWidgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public RemoteViews getLoadingView() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RemoteViews getViewAt(int position) {
			// TODO Auto-generated method stub
			RemoteViews rv = new RemoteViews(context.getPackageName(),
					R.layout.item_appwidget_simple);
			rv.setTextViewText(R.id.tv, items.get(position));
			Bundle bundle = new Bundle();
			bundle.putInt(MyWidgetProvider.EXTRA_ITEM, position);
			Intent fillInIntent = new Intent();
			fillInIntent.putExtras(bundle);
			rv.setOnClickFillInIntent(R.id.tv, fillInIntent);
			return rv;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 20; i++) {
				items.add("item " + i);
			}
		}

		@Override
		public void onDataSetChanged() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 20; i++) {
				items.add("new item " + i);
			}
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			items.clear();
		}

	}
}
