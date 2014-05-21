package org.youdian.android_demos.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.youdian.android_demos.R;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainListViewAdapter extends BaseAdapter {
	private static final String TAG="MainListViewAdapter";
	ArrayList<String> mData;
	SparseBooleanArray mStatus;
	Context mContext;
	private boolean showCheckBox=false;
	public boolean isShowCheckBox() {
		return showCheckBox;
	}
	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
		
	}
	public MainListViewAdapter(){
	
	}
	public MainListViewAdapter(Context context,ArrayList<String> mData){
		this.mContext=context;
		this.mData=mData;
		mStatus=new SparseBooleanArray(30);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void setChecked(int position,boolean checked){
		mStatus.put(position, checked);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).
					inflate(R.layout.item_listview_main, parent, false);
			convertView.setTag(holder);
			holder.name=(TextView)convertView.findViewById(R.id.name);
			holder.btn=(Button)convertView.findViewById(R.id.button);
			holder.check=(CheckBox)convertView.findViewById(R.id.checkbox);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.name.setText(mData.get(position));
		holder.btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, " button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		if(showCheckBox){
			
			holder.check.setVisibility(View.VISIBLE);
			boolean checked=mStatus.get(position);
			Log.d(TAG, "position "+position+"  "+String.valueOf(checked));
			holder.check.setChecked(checked);
				
		}else{
			holder.check.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}
	class ViewHolder{
		TextView name;
		Button btn;
		CheckBox check;
	}
}
