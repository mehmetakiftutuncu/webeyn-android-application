package com.webeyn.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.webeyn.android.utilities.MenuHandler;
import com.webeyn.android.utilities.NetworkUtilities;

/**
 * Post viewer activity that will show the selected post
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class PostViewerActivity extends Activity
{
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postviewer);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null && extras.containsKey(Constants.TAG_EXTRA_LINK))
		{
			WebView webView = (WebView) findViewById(R.id.webView);
			
			// If network connection is available
			if(NetworkUtilities.isNetworkAvailable(this))
			{
				// Load the web page for the selected post
				webView.loadUrl(extras.getString(Constants.TAG_EXTRA_LINK));
			}
		}
		
		// Enable home button and show it as up button
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
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