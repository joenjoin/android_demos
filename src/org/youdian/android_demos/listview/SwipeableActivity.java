package org.youdian.android_demos.listview;


import java.util.ArrayList;
import java.util.List;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.NoCopySpan.Concrete;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SwipeableActivity extends Activity{
	SwipeableListView mList;
	MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_swipeable);
		mAdapter=new MyAdapter(this);
		mList=(SwipeableListView)findViewById(R.id.list);
		mList.setAdapter(mAdapter);
		mList.enableSwipe(true);
		mList.setOnItemSwipeListener(new SwipeableListView.OnItemSwipeListener() {
			
			@Override
			public void onSwipe(View view) {
				// TODO Auto-generated method stub
				Log.d("SwipelableListView", "view swiped");
			}
		});
		mList.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	
	class MyAdapter extends BaseAdapter{
		
		List<String> items=new ArrayList<String>();
		Context context;
		public MyAdapter(Context context){
			this.context=context;
			items=initList(30);
		}
		private List<String> initList(int count) {
			// TODO Auto-generated method stub
			ArrayList<String> items=new ArrayList<String>();
			for(int i=0;i<count;i++){
				items.add("item "+i);
			}
			return items;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		public void deleteItem(int position){
			items.remove(position);
			this.notifyDataSetChanged();
			Log.d("SwipeableListView", "item "+position+" removed");
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder=null;
			if (convertView==null) {
				viewHolder=new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_swipeable, parent,false);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv = (TextView) convertView.findViewById(R.id.name);
			viewHolder.tv.setText(items.get(position));
			
			return convertView;
		}
		class ViewHolder {
			TextView tv;
		}
	}
	

}
