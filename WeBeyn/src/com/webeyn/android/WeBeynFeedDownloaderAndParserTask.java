package com.webeyn.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

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
	private static final String DEBUG_TAG = "WeBeyn_ParserTask";
	
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