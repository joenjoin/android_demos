package org.youdian.android_demos.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Details {
	public static final String AUTHORITY="org.youdian.detailedlist.provider";
	public static final String ITEM_TABLE_NAME="items";
	public static final String LIST_TABLE_NAME="lists";
	public static final String[] ITEM_PROJECTIONS={
		Details.Item._ID,
		Details.Item.ITEM_NAME,
		Details.Item.ITEM_COUNT,
		Details.Item.ITEM_STATUS,
		Details.Item.ADDED_TIME,
	};
	
	public static final String[] LIST_PROJECTIONS={
		Details.List._ID,
		Details.List.LIST_NAME,
		Details.List.CREATED_TIME,
	};
	public static final class Item implements BaseColumns{
		public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+ITEM_TABLE_NAME);
		public static final int STATUS_REMOVED=0;
		public static final int STATUS_ACTIVE=1;
		public static final String _ID="_id";
		public static final String ITEM_NAME="item";
		public static final String ITEM_COUNT="count";
		public static final String ITEM_STATUS="status";
		public static final String ADDED_TIME="added_time";
		public static final String LIST_ID="list_id";
		
	}
	
	public static final class List implements BaseColumns{
		public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+LIST_TABLE_NAME);
		public static final String _ID="_id";
		public static final String LIST_NAME="name";
		public static final String CREATED_TIME="created_time";
	}

}
