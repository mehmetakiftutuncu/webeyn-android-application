package com.webeyn.android.old;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.webeyn.android.R;

/**
 * An {@link AsyncTask} to download and parse the RSS feed of WeBeyn
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class WeBeynFeedDownloaderAndParserTask extends AsyncTask<Void, Void, ArrayList<Item>>
{
	/** URL of RSS feed to download and parse */
	private static final String RSS_FEED_URL = "http://www.webeyn.com/feed";
	
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_FeedDownloaderAndParserTask";
	
	/** {@link Context} of the {@link Activity} that has the {@link ListView} */
	private Context mContext;
	/** {@link ListView} in which the parsed {@link Items}s will be shown using a {@link WeBeynFeedAdapter} */
	private ListView mListView;
	/** {@link MenuItem} for refreshing the feed that will show an indeterminate progress while refreshing */
	private MenuItem mRefreshMenuItem;
	
	/**
	 * Constructs a {@link WeBeynFeedDownloaderAndParserTask}
	 * 
	 * @param context {@link WeBeynFeedDownloaderAndParserTask#mContext}
	 * @param listView {@link WeBeynFeedDownloaderAndParserTask#mListView}
	 * @param refreshMenuItem {@link WeBeynFeedDownloaderAndParserTask#mRefreshMenuItem}
	 */
	public WeBeynFeedDownloaderAndParserTask(Context context, ListView listView, MenuItem refreshMenuItem)
	{
		mContext = context;
		mListView = listView;
		mRefreshMenuItem = refreshMenuItem;
	}
	
	/* The task that will be done in the background thread */
	@Override
	protected ArrayList<Item> doInBackground(Void... params)
	{
		try
		{
			// Connect to internet and download the RSS feed
			InputStream inputStream = downloadFeed();
			
			// Instantiate a parser
			WeBeynParser parser = new WeBeynParser();
			
			// Parse the downloaded feed and return result
			return parser.parse(inputStream);
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while downloading and parsing RSS feed!", e);
			
			return null;
        }
    }
	
	/* This method will be called after the task is finished supplying the result as parameter */
	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(ArrayList<Item> items)
	{
		super.onPostExecute(items);

		// Set the list adapter with the resulting items
		mListView.setAdapter(new WeBeynFeedAdapter(mContext, items));
		
		if(mRefreshMenuItem != null)
		{
			// Turn off the progress in refresh button
			mRefreshMenuItem.setActionView(null);
			
			// Show toast to inform
			Toast.makeText(mContext, mContext.getString(R.string.refresh_finished), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Connects to internet and downloads the RSS feed
	 * 
	 * @return {@link InputStream} for the downloaded RSS feed
	 * 
	 * @throws IOException
	 */
	private InputStream downloadFeed() throws IOException
	{
		// Set up the connection
		URL url = new URL(RSS_FEED_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
		// Connect
		conn.connect();
		
		// Get an input stream for the rss feed
		return conn.getInputStream();
	}
}