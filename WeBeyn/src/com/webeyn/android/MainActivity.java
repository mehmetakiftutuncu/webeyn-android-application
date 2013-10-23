package com.webeyn.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.webeyn.android.tasks.WeBeynFeedDownloaderAndParserTask;
import com.webeyn.android.utilities.MenuHandler;
import com.webeyn.android.utilities.NetworkUtilities;

/**
 * Main activity of the application
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get list view
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View clickedView, int position, long id)
			{
				Item item = (Item) adapter.getItemAtPosition(position);
				
				Intent intent = new Intent(MainActivity.this, PostViewerActivity.class);
				intent.putExtra(Constants.TAG_EXTRA_LINK, item.getLink());
				startActivity(intent);
			}
		});
		
		// If network connection is available
		if(NetworkUtilities.isNetworkAvailable(this))
		{
			// Create a task and fill the list to test
			new WeBeynFeedDownloaderAndParserTask(this, listView).execute();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Create refresh menu item
		menu.add("item_refresh")
		.setIcon(R.drawable.ic_action_refresh)
		.setTitle(getString(R.string.menu_refresh))
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle the selection of any menu item
		MenuHandler.handle(this, item);
		
		return true;
	}
}