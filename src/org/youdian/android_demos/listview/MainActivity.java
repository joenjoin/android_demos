package org.youdian.android_demos.listview;

import java.util.ArrayList;
import java.util.HashSet;

import org.youdian.android_demos.R;
import org.youdian.android_demos.listview.MainListViewAdapter.ViewHolder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView mList;
	ActionMode mActionMode;
	MainListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_main);
		mList = (ListView) findViewById(R.id.list);
		ArrayList<String> mData = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			mData.add("item " + i);
		}
		mAdapter = new MainListViewAdapter(this, mData);
		mList.setAdapter(mAdapter);
		mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (!mAdapter.isShowCheckBox()) {
					mAdapter.setShowCheckBox(true);
					mAdapter.notifyDataSetChanged();
					ViewHolder holder = (ViewHolder) view.getTag();
					holder.check.toggle();
					// mAdapter.setChecked(position, holder.check.isChecked());
					mActionMode = MainActivity.this
							.startSupportActionMode(mCallBack);
				}

				return true;
			}
		});

		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mAdapter.isShowCheckBox()) {
					ViewHolder holder = (ViewHolder) view.getTag();
					holder.check.toggle();
					// mAdapter.setChecked(position, holder.check.isChecked());
				} else {
					Toast.makeText(MainActivity.this, "you clicked" + position,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		this.registerForContextMenu(mList);
	}

	private ActionMode.Callback mCallBack = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			mActionMode = null;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			MenuInflater inflater = MainActivity.this.getMenuInflater();
			inflater.inflate(R.menu.listview_multichoice, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			int id = item.getItemId();
			if (id == R.id.delete) {
				HashSet<Integer> mSelectedItems = mAdapter.getSelectedItems();
				StringBuilder builder = new StringBuilder("selected items: ");
				for (Integer i : mSelectedItems) {
					builder.append(i + " ");
				}
				System.out.println(builder.toString());
				mode.finish();
				mAdapter.setShowCheckBox(false);
				mAdapter.notifyDataSetChanged();
				return true;
			}
			return false;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

}
