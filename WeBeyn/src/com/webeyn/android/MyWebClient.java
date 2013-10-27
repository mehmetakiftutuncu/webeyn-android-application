package com.webeyn.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Constant definitions
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
@SuppressLint("NewApi")
public class MyWebClient extends WebViewClient
{
	private Context context;
	private MenuItem refreshMenuItem;
	private boolean isRefreshClicked = false;
	
	public MyWebClient(Context context)
	{
		this.context = context;
	}
	
	public void setRefreshMenuItem(MenuItem refreshMenuItem)
	{
		this.refreshMenuItem = refreshMenuItem;
	}
	
	public void setRefreshClicked(boolean isRefreshClicked)
	{
		this.isRefreshClicked = isRefreshClicked;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onPageFinished(WebView view, String url)
	{
		super.onPageFinished(view, url);
		
		if(((MainActivity) context).isShowingSplash())
		{
			((MainActivity) context).hideSplash();
		}
		
		if(refreshMenuItem != null && isRefreshClicked)
		{
			// Turn off the progress in refresh button
			MenuItemCompat.setActionView(refreshMenuItem, null);
			
			// Show toast to inform
			Toast.makeText(context, context.getString(R.string.refresh_finished), Toast.LENGTH_SHORT).show();
			
			// Reset clicked state
			isRefreshClicked = false;
		}
	}
}