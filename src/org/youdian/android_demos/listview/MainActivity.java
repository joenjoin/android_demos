package org.youdian.android_demos.listview;

import java.util.ArrayList;

import org.youdian.android_demos.R;
import org.youdian.android_demos.listview.MainListViewAdapter.ViewHolder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
	
	ListView mList;
	ActionMode mActionMode;
	MainListViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_main);
		mList=(ListView)findViewById(R.id.list);
		ArrayList<String> mData=new ArrayList<String>();
		for(int i=0;i<30;i++){
			mData.add("item "+i);
		}
		mAdapter=new MainListViewAdapter(this, mData);
		mList.setAdapter(mAdapter);
		mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(!mAdapter.isShowCheckBox()){
					mAdapter.setShowCheckBox(true);
					mAdapter.notifyDataSetChanged();
					mActionMode=MainActivity.this.startSupportActionMode(mCallBack);
				}
				
				return true;
			}
		});
		
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(mAdapter.isShowCheckBox()){
					ViewHolder holder=(ViewHolder) view.getTag();
					holder.check.toggle();
					mAdapter.setChecked(position, holder.check.isChecked());
				}else{
					Toast.makeText(MainActivity.this, "you clicked"+position, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.registerForContextMenu(mList);
	}
	private ActionMode.Callback mCallBack=new ActionMode.Callback() {
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mdoe, MenuItem item) {
			// TODO Auto-generated method stub
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
