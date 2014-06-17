package org.youdian.android_demos.contentprovider;

import org.youdian.android_demos.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final int STATUS_REMOVED = 0;
	public static final int STATUS_ACTIVE = 1;

	private static final int DEFAULT_LIST_NAME_RESOURCE_ID = R.string.default_list;

	private static final int VERSION = 1;
	public static final String DB_NAME = "details.db";
	private static final String ITEM_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ Details.ITEM_TABLE_NAME
			+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ Details.Item.ITEM_NAME + " NVARCHAR," + Details.Item.ITEM_COUNT
			+ " INTEGER," + Details.Item.ITEM_STATUS + " INTEGER DEFAULT "
			+ STATUS_ACTIVE + "," + Details.Item.ADDED_TIME
			+ " TIMESTAMP DEFAULT (datetime('now','localtime')),"
			+ Details.Item.LIST_ID + " INTEGER NOT NULL" + ")";

	private static final String LIST_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ Details.LIST_TABLE_NAME
			+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ Details.List.LIST_NAME + " NVARCHAR," + Details.List.CREATED_TIME
			+ " TIMESTAMP DEFAULT (datetime('now','localtime'))" + ")";

	private Context context;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context) {
		this(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(ITEM_TABLE_SQL);
		db.execSQL(LIST_TABLE_SQL);
		ContentValues values = new ContentValues();
		values.put(Details.List.LIST_NAME,
				context.getResources().getString(DEFAULT_LIST_NAME_RESOURCE_ID));
		db.insert(Details.LIST_TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + Details.ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Details.LIST_TABLE_NAME);
		onCreate(db);
	}

}
