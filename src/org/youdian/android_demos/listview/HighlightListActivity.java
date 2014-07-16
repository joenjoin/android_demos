package org.youdian.android_demos.listview;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/*
 * onItemSelectedListener对ListView不适用
 */
public class HighlightListActivity extends ListActivity implements OnItemClickListener {
	ListView mList;
	String [] mData=new String[20];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mList=getListView();
		initData();
		ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(this, R.layout.activity_listview_highlightlist,
				R.id.tv, mData);
		//*/
		mList.setAdapter(mAdapter);
		/*/
		mList.setOnItemClickListener(this);
		//*/
	}
	private void initData() {
		// TODO Auto-generated method stub
		for(int i=0;i<20;i++){
			mData[i]="item "+i;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		Log.d(HighlightLinearLayout.TAG,"item "+position+" clicked");
	}

	
	
}
