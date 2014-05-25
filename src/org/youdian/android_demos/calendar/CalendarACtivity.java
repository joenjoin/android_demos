package org.youdian.android_demos.calendar;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;

public class CalendarACtivity	extends Activity {
	
	/*
	 * 创建本地日历账号
	 */
	@SuppressLint("NewApi")
	public void createLocalAccount(){
		Cursor cursor=this.getContentResolver().query(Calendars.CONTENT_URI,null,null,null,null);
		if(cursor.getCount()==0){
		Account account=new Account("Phone",CalendarContract.ACCOUNT_TYPE_LOCAL);
		createCalendar(this,account);
			}
	}
	
	@SuppressLint({"NewApi" })
	private void createCalendar( Context mContext, Account account) { 
		final ContentValues v = new ContentValues(); 
		v.put(CalendarContract.Calendars.NAME,account.name); 
		v.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, account.name); 
		v.put(CalendarContract.Calendars.ACCOUNT_NAME, account.name); 
		v.put(CalendarContract.Calendars.ACCOUNT_TYPE, account.type); 
		v.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.GREEN); 
		v.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,Calendars.CAL_ACCESS_OWNER); 
		v.put(CalendarContract.Calendars.OWNER_ACCOUNT, account.name); 
		v.put(CalendarContract.Calendars._ID, 123);// u can give any id there and use same id any where u need to create event 
		v.put(Calendars.SYNC_EVENTS, 1); 
		v.put(Calendars.VISIBLE, 1); 
		Uri creationUri = asSyncAdapter(Calendars.CONTENT_URI, account.name, account.type); 
		Uri calendarData = mContext.getContentResolver().insert(creationUri, v); 
		long id = Long.parseLong(calendarData.getLastPathSegment()); 
		} 
	
	private Uri asSyncAdapter(Uri uri, String account, String accountType) { 
		return uri.buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").appendQueryParameter (Calendars.ACCOUNT_NAME,account) .appendQueryParameter(Calendars.ACCOUNT_TYPE, accountType) .build(); 
		
	}

}
