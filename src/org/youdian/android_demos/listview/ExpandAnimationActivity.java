package org.youdian.android_demos.listview;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ExpandAnimationActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // To change body of overridden
											// methods use File | Settings |
											// File Templates.
		setContentView(R.layout.activity_listview_expand);

		ListView list = (ListView) findViewById(R.id.list);

		// Creating the list adapter and populating the list
		ArrayAdapter<String> listAdapter = new CustomListAdapter(this,
				R.layout.item_listview_expand);
		for (int i = 0; i < 20; i++)
			listAdapter.add("udini" + i);
		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				View toolbar = view.findViewById(R.id.toolbar);

				// Creating the expand animation for the item
				ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

				// Start the animation on the toolbar
				toolbar.startAnimation(expandAni);
			}
		});
	}

	/**
	 * A simple implementation of list adapter.
	 */
	class CustomListAdapter extends ArrayAdapter<String> {

		public CustomListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.item_listview_expand, null);
			}

			((TextView) convertView.findViewById(R.id.title))
					.setText(getItem(position));

			// Resets the toolbar to be closed
			View toolbar = convertView.findViewById(R.id.toolbar);
			((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
			toolbar.setVisibility(View.GONE);

			return convertView;
		}
	}
}
