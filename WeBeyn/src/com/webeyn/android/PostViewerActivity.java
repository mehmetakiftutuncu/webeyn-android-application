package com.webeyn.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;


/**
 * Post viewer activity that will show the selected post
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class PostViewerActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postviewer);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null && extras.containsKey(Constants.TAG_EXTRA_LINK))
		{
			WebView webView = (WebView) findViewById(R.id.webView);
			
			webView.loadUrl(extras.getString(Constants.TAG_EXTRA_LINK));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}