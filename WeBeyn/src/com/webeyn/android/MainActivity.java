package com.webeyn.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;


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
		
		// Create a task and fill the list to test
		new WeBeynFeedDownloaderAndParserTask(this, (ListView) findViewById(R.id.listView1)).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}