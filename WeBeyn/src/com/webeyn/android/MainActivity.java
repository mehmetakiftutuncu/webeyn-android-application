package com.webeyn.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Main activity of the application
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class MainActivity extends ActionBarActivity
{
	private ImageView splashImage;
	private FrameLayout webViewPlaceHolder;
	private WebView webView;
	private MyWebClient webClient;
	private MenuItem refreshMenuItem;
	
	private boolean shouldShowSplash = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSupportActionBar().hide();
		
		initialize();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// Check if the key event was the Back button and if there's history
		if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
		{
			webView.goBack();
	    	return true;
	    }
		// If it wasn't the Back key or there's no web page history, bubble up to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Create refresh menu item
		refreshMenuItem = menu.add("item_refresh");
		refreshMenuItem.setIcon(R.drawable.ic_action_refresh)
		.setTitle(getString(R.string.menu_refresh));
		MenuItemCompat.setShowAsAction(refreshMenuItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		// Set the refresh button
		webClient.setRefreshMenuItem(refreshMenuItem);
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId() == refreshMenuItem.getItemId())
		{
			// Show toast to inform
			Toast.makeText(this, getString(R.string.refresh_started), Toast.LENGTH_SHORT).show();
			
			// Create the progress bar
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.actionbar_progress, null);
            MenuItemCompat.setActionView(item, v);
            
            // Set client as refresh button clicked
            webClient.setRefreshClicked(true);
            
            // Refresh the page
            webView.reload();
		}
		else
		{
			// Handle the selection of any menu item
			MenuHandler.handle(this, item);
		}
		
		return true;
	}
	
	private void initialize()
	{
		// Retrieve UI elements
		splashImage = (ImageView) findViewById(R.id.splashImage);
		webViewPlaceHolder = ((FrameLayout)findViewById(R.id.webView_placeHolder));
		
		// Initialize the WebView if necessary
		if(webView == null)
		{
	    	// Create web view
	    	webView = new WebView(this);
	    	webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
	    	
	    	// Set the web client
			webClient = new MyWebClient(this);
			webView.setWebViewClient(webClient);
			
			// Set options
			webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			webView.setScrollbarFadingEnabled(true);
			
			// Load
			loadWebeyn();
		}
		
		// Attach the WebView to its placeholder
		webViewPlaceHolder.addView(webView);
		
		// Enable home button and show it as up button
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Hide splash if necessary
		if(!shouldShowSplash)
		{
			hideSplash();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		if(webView != null)
		{
			// Remove the WebView from the old placeholder
			webViewPlaceHolder.removeView(webView);
		}
		
		super.onConfigurationChanged(newConfig);
		
		// Load the layout resource for the new configuration
		setContentView(R.layout.activity_main);
		
		// Reinitialize the UI
		initialize();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		
		// Save the state of the WebView
		webView.saveState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		// Restore the state of the WebView
		webView.restoreState(savedInstanceState);
	}
	
	public void loadWebeyn()
	{
		// If network connection is available
		if(NetworkUtilities.isNetworkAvailable(this))
		{
			// Load
			webView.loadUrl(Constants.WEBSITE_URL);
		}
	}
	
	public void hideSplash()
	{
		splashImage.setVisibility(View.GONE);
		
		shouldShowSplash = false;
		
		getSupportActionBar().show();
	}
	
	public boolean isShowingSplash()
	{
		return shouldShowSplash;
	}
}