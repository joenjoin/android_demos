package org.youdian.android_demos.listview;

import java.util.ArrayList;

import org.youdian.android_demos.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity{
	
	ListView mList;
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
				mAdapter.setShowCheckBox(true);
				mAdapter.notifyDataSetChanged();
				return true;
			}
		});
		
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(mAdapter.isShowCheckBox()){
					System.out.println("added "+position);
					mAdapter.setChecked(position, true);
				}
			}
		});
	}

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
