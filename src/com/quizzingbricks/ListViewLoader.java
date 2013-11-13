//package com.quizzingbricks;
//
//import android.app.ActionBar.LayoutParams;
//import android.app.ListActivity;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.app.Activity;
//import android.content.CursorLoader;
//import android.content.Intent;
//import android.content.Loader;
//import android.database.Cursor;
//import android.support.v4.widget.SimpleCursorAdapter;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.support.v4.app.LoaderManager;
//
//public class ListViewLoader extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
//	
//	// This is the Adapter being used to display the list's data
//	SimpleCursorAdapter mAdapter;
//	
//	static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
//        ContactsContract.Data.DISPLAY_NAME};
//
//	// This is the select criteria
//	static final String SELECTION = "((" + 
//    ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
//    ContactsContract.Data.DISPLAY_NAME + " != '' ))";
//
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//	
//	// Create a progress bar to display while the list loads
//	ProgressBar progressBar = new ProgressBar(this);
//	progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//	        LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//	progressBar.setIndeterminate(true);
//	getListView().setEmptyView(progressBar);
//	
//	// Must add the progress bar to the root of the layout
//	ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//	root.addView(progressBar);
//	
//	// For the cursor adapter, specify which columns go into which views
//	String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
//	int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1
//	
//	// Create an empty adapter we will use to display the loaded data.
//	// We pass null for the cursor, then update it in onLoadFinished()
//	mAdapter = new SimpleCursorAdapter(this, 
//	        android.R.layout.simple_list_item_1, null,
//	        fromColumns, toViews, 0);
//	setListAdapter(mAdapter);
//	
//	// Prepare the loader.  Either re-connect with an existing one,
//	// or start a new one.
//	getLoaderManager().restartLoader(0, null, this);
//	}
//	
//	// Called when a new Loader needs to be created
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//	// Now create and return a CursorLoader that will take care of
//	// creating a Cursor for the data being displayed.
//	return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
//	        PROJECTION, SELECTION, null, null);
//	}
//	
//	// Called when a previously created loader has finished loading
//	
//	
//	// Called when a previously created loader is reset, making the data unavailable
//	
//	@Override 
//	public void onListItemClick(ListView l, View v, int position, long id) {
//	// Do something when a list item is clicked
//	}
//
//	@Override
//	public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0,
//			Cursor arg1) {
//		// TODO Auto-generated method stub
//		mAdapter.swapCursor(data);
//	}
//
//	@Override
//	public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
//		// TODO Auto-generated method stub
//		mAdapter.swapCursor(null);
//	}
//
//	
//}
