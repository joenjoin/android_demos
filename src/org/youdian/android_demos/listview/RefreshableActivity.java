package org.youdian.android_demos.listview;

import org.youdian.android_demos.R;
import org.youdian.android_demos.listview.RefreshableListView.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RefreshableActivity extends Activity implements OnRefreshListener {
	RefreshableListView mList;
	ArrayAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_refreshable);
		mList = (RefreshableListView) findViewById(R.id.list);
		String[] items = new String[20];
		for (int i = 0; i < 20; i++) {
			items[i] = "item " + i;
		}
		mAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, items);
		mList.setAdapter(mAdapter);
		mList.setonRefreshListener(this);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show();
		mList.onRefreshComplete();
	}

}
