package com.webeyn.android.old;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.webeyn.android.NetworkUtilities;
import com.webeyn.android.R;

/**
 * Main activity of the application
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class MainActivity extends Activity
{
	private ListView listView;
	private MenuItem refreshMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get list view
		listView = (ListView) findViewById(R.id.listView1);
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
		
		// Refresh the feed
		refreshFeed();
	}

	private void refreshFeed()
	{
		// If network connection is available
		if(NetworkUtilities.isNetworkAvailable(this))
		{
			// Create a task and fill the list to test
			new WeBeynFeedDownloaderAndParserTask(this, listView, refreshMenuItem).execute();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Create refresh menu item
		refreshMenuItem = menu.add("item_refresh");
		refreshMenuItem.setIcon(R.drawable.ic_action_refresh)
		.setTitle(getString(R.string.menu_refresh))
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.equals(refreshMenuItem))
		{
			// Show toast to inform
			Toast.makeText(this, getString(R.string.refresh_started), Toast.LENGTH_SHORT).show();
			
			// Create the progress bar
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.actionbar_progress, null);
            item.setActionView(v);
            
            // Refresh the feed
            refreshFeed();
		}
		else
		{
			// Handle the selection of any menu item
			//MenuHandler.handle(this, item);
		}
		
		return true;
	}
}