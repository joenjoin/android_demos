package org.youdian.android_demos.contentprovider;

import org.youdian.android_demos.R;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener {
	private static final String NEW_ITEM_FRAGMENT_TAG = "new_item_fragment";
	private static final int LIST_ITEM_LOADER_ID = 0x01;
	DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
	DatabaseAsyncService mDatabaseService;
	CursorAdapter mListAdapter;
	ActionBar mActionBar;
	boolean isActionModeShowing = false;
	Cursor cursor;
	ListView mList;
	long mCurrentListId = 1;
	LoaderCallbacks<Cursor> mListLoaderCallbacks;
	DatabaseAsyncService.DatabaseCallback mDatabaseCallback = new DatabaseAsyncService.DatabaseCallback() {

		@Override
		public void onSuccess(int tag) {
			// TODO Auto-generated method stub
			switch (tag) {
			case DatabaseAsyncService.TAG_ITEM_INSERT:
				Toast.makeText(MainActivity.this, R.string.insert_success,
						Toast.LENGTH_SHORT).show();
				break;
			case DatabaseAsyncService.TAG_ITEM_DELETE:
				Toast.makeText(MainActivity.this, R.string.delete_success,
						Toast.LENGTH_SHORT).show();
				break;
			}

		}

		@Override
		public void onFail(int tag) {
			// TODO Auto-generated method stub
			switch (tag) {
			case DatabaseAsyncService.TAG_ITEM_INSERT:
				Toast.makeText(MainActivity.this, R.string.insert_fail,
						Toast.LENGTH_SHORT).show();
				break;
			case DatabaseAsyncService.TAG_ITEM_DELETE:
				Toast.makeText(MainActivity.this, R.string.delete_fail,
						Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentprovider_main);
		mDatabaseService = new DatabaseAsyncService(this, mDatabaseCallback);
		initActionBar();
		initList();

	}

	private void initList() {
		mList = (ListView) findViewById(R.id.list);
		mList.setOnItemClickListener(this);
		initListLoader();
	}

	private void initActionBar() {
		mActionBar = getSupportActionBar();
	}

	private void initListLoader() {
		mListLoaderCallbacks = new LoaderCallbacks<Cursor>() {

			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				// TODO Auto-generated method stub
				System.out.println("onCreateLoader");
				String selection = Details.Item.LIST_ID + "=" + mCurrentListId;
				System.out.println("currentlistid=" + mCurrentListId);
				CursorLoader cursorLoader = new CursorLoader(MainActivity.this,
						Details.Item.CONTENT_URI, Details.ITEM_PROJECTIONS,
						selection, null, null);
				return cursorLoader;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
				// TODO Auto-generated method stub
				if (mListAdapter == null) {
					mListAdapter = new ItemListAdapter(MainActivity.this,
							cursor);
					mList.setAdapter(mListAdapter);
				}
				mListAdapter.swapCursor(cursor);
				System.out.println(" list loader finished");
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				// TODO Auto-generated method stub
				mListAdapter.swapCursor(null);
			}

		};
		getSupportLoaderManager().initLoader(LIST_ITEM_LOADER_ID, null,
				mListLoaderCallbacks);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_contentprovider_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.add:
			NewItemFragment newItemFragment = new NewItemFragment();
			Bundle args = new Bundle();
			args.putLong("list_id", mCurrentListId);
			newItemFragment.setArguments(args);
			newItemFragment.show(getSupportFragmentManager(),
					NEW_ITEM_FRAGMENT_TAG);
			break;
		default:
			throw new RuntimeException("Unknown Operation");
		}
		return super.onOptionsItemSelected(item);
	}

	public void asyncAddItem(String name, int count, long list_id) {
		ContentValues cv = new ContentValues();
		cv.put(Details.Item.ITEM_NAME, name);
		cv.put(Details.Item.ITEM_COUNT, count);
		cv.put(Details.Item.LIST_ID, list_id);
		mDatabaseService.asyncInsert(Details.Item.CONTENT_URI, cv,
				DatabaseAsyncService.TAG_ITEM_INSERT);

	}

	public void asyncUpdateItem(long id, ContentValues values) {
		Uri newUri = ContentUris.withAppendedId(Details.Item.CONTENT_URI, id);
		mDatabaseService.asyncUpdate(newUri, values, null, null,
				DatabaseAsyncService.TAG_ITEM_UPDATE);
	}

	public void asyncDeleteItem(long... id) {
		StringBuilder selection = new StringBuilder();
		String[] selectionArgs = new String[id.length];
		int i = 0;
		for (long l : id) {
			if (i == 0)
				selection.append(" _id = ? ");
			else
				selection.append(" or _id=? ");
			selectionArgs[i] = String.valueOf(l);
			i++;
		}
		mDatabaseService.asyncDelete(Details.Item.CONTENT_URI,
				selection.toString(), selectionArgs,
				DatabaseAsyncService.TAG_ITEM_DELETE);
	}

	public void asyncAddList(String name) {
		ContentValues values = new ContentValues();
		values.put(Details.List.LIST_NAME, name);
		mDatabaseService.asyncInsert(Details.List.CONTENT_URI, values,
				DatabaseAsyncService.TAG_LIST_INSERTION);
	}

	public void asyncUpdateList(long id, ContentValues values) {
		Uri newUri = ContentUris.withAppendedId(Details.List.CONTENT_URI, id);
		mDatabaseService.asyncUpdate(newUri, values, null, null,
				DatabaseAsyncService.TAG_LIST_UPDATE);
	}

	public void asyncDeleteList(long... id) {
		StringBuilder selection = new StringBuilder();
		String[] selectionArgs = new String[id.length];
		int i = 0;
		for (long l : id) {
			if (i == 0)
				selection.append(" _id = ? ");
			else
				selection.append(" or _id=? ");
			selectionArgs[i] = String.valueOf(l);
			i++;
		}
		mDatabaseService.asyncDelete(Details.List.CONTENT_URI,
				selection.toString(), selectionArgs,
				DatabaseAsyncService.TAG_LIST_DELETE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

}
