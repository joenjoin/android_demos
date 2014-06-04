package org.youdian.android_demos.contentprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class DatabaseAsyncService {

	private static final int SUCCESS=0x11;
	private static final int FAIL=0x12;
	public static final int TAG_ITEM_INSERT=0x21;
	public static final int TAG_ITEM_UPDATE=0x22;
	public static final int TAG_ITEM_QUERY=0x23;
	public static final int TAG_ITEM_DELETE=0x24;
	public static final int TAG_LIST_INSERTION=0x31;
	public static final int TAG_LIST_UPDATE=0x32;
	public static final int TAG_LIST_QUERY=0x33;
	public static final int TAG_LIST_DELETE=0x34;
	
	public static DatabaseCallback mCallback;
	private static int mTag=-1;
	Context context;
	static Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case SUCCESS:
				mCallback.onSuccess(mTag);
				break;
			case FAIL:
				mCallback.onFail(mTag);
			}
		}
		
	};
	
	public DatabaseAsyncService(Context context,DatabaseCallback callback){
		this.context=context;
		mCallback=callback;
	}
	
	void asyncInsert(final Uri uri,final ContentValues values,int tag){
		mTag=tag;
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Uri insertedUri=context.getContentResolver().insert(uri, values);
				Message msg=Message.obtain();
				//msg.what=INSERT_OPERATION;
				if(ContentUris.parseId(insertedUri)>0){	
					msg.what=SUCCESS;
				}else{
					msg.what=FAIL;
				}
				handler.sendMessage(msg);
			}
			
		}).start();
	}
	
	void asyncQuery(final Uri uri,final String[] projection, final String selection,
			final String[] selectionArgs, final String sortOrder,int tag){
			mTag=tag;
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Cursor cursor=context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
					Message msg=Message.obtain();
					if(cursor!=null){
						msg.what=SUCCESS;
					}else{
						msg.what=FAIL;
					}
					handler.sendMessage(msg);
				}
				
			}).start();
		
	}
	
	void asyncUpdate(final Uri uri,final  ContentValues values, final String selection,
			final String[] selectionArgs,int tag){
		mTag=tag;
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count=context.getContentResolver().update(uri, values, selection, selectionArgs);
				Message msg=Message.obtain();
				if(count>=0){
					msg.what=SUCCESS;
				}else{
					msg.what=FAIL;
				}
				handler.sendMessage(msg);
			}
			
		}).start();
	}
	
	void asyncDelete(final Uri uri, final String selection, final String[] selectionArgs,int tag){
		mTag=tag;
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count=context.getContentResolver().delete(uri, selection, selectionArgs);
				Message msg=Message.obtain();
				if(count>=0){
					msg.what=SUCCESS;
				}else{
					msg.what=FAIL;
				}
				handler.sendMessage(msg);
			}
			
		}).start();
	}
	public interface DatabaseCallback{
		void onSuccess(int tag);
		void onFail(int tag);
	}

}
