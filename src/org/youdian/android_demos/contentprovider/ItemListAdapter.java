package org.youdian.android_demos.contentprovider;

import org.youdian.android_demos.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemListAdapter extends CursorAdapter {
	
	public ItemListAdapter(Context context, Cursor c) {
		this(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		// TODO Auto-generated constructor stub
	}
	
	public ItemListAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		// TODO Auto-generated method stub
		String name=c.getString(c.getColumnIndex(Details.Item.ITEM_NAME));
		int count=c.getInt(c.getColumnIndex(Details.Item.ITEM_COUNT));
		long id=c.getLong(c.getColumnIndex(Details.Item._ID));
		int status=c.getInt(c.getColumnIndex(Details.Item.ITEM_STATUS));
		TextView tv_name=(TextView)v.findViewById(R.id.name);
		TextView tv_count=(TextView)v.findViewById(R.id.count);
		//System.out.println("name="+name);
		//System.out.println("count="+count);
		tv_name.setText(name+"   id="+id+" status="+status);
		tv_count.setText(String.valueOf(count));
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=LayoutInflater.from(context);
		View v=inflater.inflate(R.layout.item_contentprovider_main, parent, false);
		return v;
	}

}
