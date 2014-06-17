package org.youdian.android_demos.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class DetailsContentProvider extends ContentProvider {
	private static final UriMatcher sUriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	private DatabaseHelper mDataBaseHelper;
	private static final String TYPE_ITEM_DIR = "vnd.android.cursor.dir/vnd.org.youdian.detailedlist.items";
	private static final String TYPE_ITEM_ITEM = "vnd.android.cursor.item/vnd.org.youdian.detailedlist.items";
	private static final String TYPE_LIST_DIR = "vnd.android.cursor.dir/vnd.org.youdian.detailedlist.lists";
	private static final String TYPE_LIST_ITEM = "vnd.android.cursor.item/vnd.org.youdian.detailedlist.lists";
	private static final int ITEMS = 1;
	private static final int ITEM = 2;
	private static final int LISTS = 3;
	private static final int LIST = 4;

	static {
		sUriMatcher.addURI(Details.AUTHORITY, Details.ITEM_TABLE_NAME, ITEMS);
		sUriMatcher.addURI(Details.AUTHORITY, Details.ITEM_TABLE_NAME + "/#",
				ITEM);
		sUriMatcher.addURI(Details.AUTHORITY, Details.LIST_TABLE_NAME, LISTS);
		sUriMatcher.addURI(Details.AUTHORITY, Details.LIST_TABLE_NAME + "/#",
				LIST);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub

		mDataBaseHelper = new DatabaseHelper(getContext());
		mDataBaseHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			cursor = db.query(Details.ITEM_TABLE_NAME,
					Details.ITEM_PROJECTIONS, selection, selectionArgs, null,
					null, Details.Item.ADDED_TIME + " DESC");
			break;
		case ITEM:
			long id = ContentUris.parseId(uri);
			String where = " _id=" + id;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				where = selection + " and " + where;
			}
			cursor = db.query(Details.ITEM_TABLE_NAME,
					Details.ITEM_PROJECTIONS, where, selectionArgs, null, null,
					sortOrder);
			break;
		case LISTS:
			cursor = db.query(Details.LIST_TABLE_NAME,
					Details.LIST_PROJECTIONS, selection, selectionArgs, null,
					null, Details.List.CREATED_TIME + " DESC");
			break;
		case LIST:
			long list_id = ContentUris.parseId(uri);
			String list_where = " _id=" + list_id;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				list_where = selection + " and " + list_where;
			}
			cursor = db.query(Details.LIST_TABLE_NAME,
					Details.LIST_PROJECTIONS, list_where, selectionArgs, null,
					null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown uri:" + uri.toString());
		}

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			return TYPE_ITEM_DIR;
		case ITEM:
			return TYPE_ITEM_ITEM;
		case LISTS:
			return TYPE_LIST_DIR;
		case LIST:
			return TYPE_LIST_ITEM;
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
		Uri insertedUri = null;
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			long itemId = db.insert(Details.ITEM_TABLE_NAME, null, values);
			insertedUri = ContentUris.withAppendedId(uri, itemId);
			break;
		case LISTS:
			long listId = db.insert(Details.LIST_TABLE_NAME, null, values);
			insertedUri = ContentUris.withAppendedId(uri, listId);
			break;
		default:
			throw new IllegalArgumentException("Unknown uri:" + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return insertedUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
		int count = 0;
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			count = db
					.delete(Details.ITEM_TABLE_NAME, selection, selectionArgs);
			break;
		case ITEM:
			long itemId = ContentUris.parseId(uri);
			String itemWhere = " _id=" + itemId;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				itemWhere = selection + " and " + itemWhere;
			}
			count = db
					.delete(Details.ITEM_TABLE_NAME, itemWhere, selectionArgs);
			break;
		case LISTS:
			count = db
					.delete(Details.LIST_TABLE_NAME, selection, selectionArgs);
			break;
		case LIST:
			long listId = ContentUris.parseId(uri);
			String listWhere = " _id=" + listId;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				itemWhere = selection + " and " + listWhere;
			}
			count = db
					.delete(Details.LIST_TABLE_NAME, listWhere, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown uri:" + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
		int count = 0;
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			count = db.update(Details.ITEM_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case ITEM:
			long itemId = ContentUris.parseId(uri);
			String itemWhere = " _id=" + itemId;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				itemWhere = selection + " and " + itemWhere;
			}
			count = db.update(Details.ITEM_TABLE_NAME, values, itemWhere,
					selectionArgs);
			break;
		case LISTS:
			count = db.update(Details.LIST_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case LIST:
			long listId = ContentUris.parseId(uri);
			String listWhere = " _id=" + listId;
			if (selection != null && !TextUtils.isEmpty(selection)) {
				itemWhere = selection + " and " + listWhere;
			}
			count = db.update(Details.LIST_TABLE_NAME, values, listWhere,
					selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown uri:" + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
