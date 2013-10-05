package com.webeyn.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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
		
		// Test start
		try
		{
			for(Item i : new WeBeynFeedDownloaderAndParserTask().execute().get())
			{
				Log.d("WeBeyn", i.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// Test end TODO: Delete
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}