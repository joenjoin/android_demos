package org.youdian.android_demos.animation;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class YoutubeDragActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_youtubedrag);
		setupListView();
	}

	private void setupListView() {
		mListView = (ListView) findViewById(R.id.listView);
		SampleAdapter adapter = new SampleAdapter(this);
		for (int i = 0; i < 20; i++) {
			adapter.add(new SampleItem("Love Android",
					android.R.drawable.star_on));
		}
		mListView.setAdapter(adapter);
	}

	public static void launch(Context context) {
		Intent intent = new Intent(context, YoutubeDragActivity.class);
		context.startActivity(intent);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.item_animation_youtube, null);
			}
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
