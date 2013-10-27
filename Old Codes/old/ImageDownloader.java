package com.webeyn.android.old;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

/**
 * An asynchronous task to download an image as a Bitmap in the memory
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap>
{
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_ImageDownloader";
	
	@Override
	protected Bitmap doInBackground(String... params)
	{
		// Set up the URL from the first (which will also be the only) String parameter
		URL url = null;
		try
		{
			url = new URL(params[0]);
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while creating a URL to download image!", e);
			
			return null;
		}
		
		try
		{
			// Open a connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			
			// Get the input stream of this connection
			InputStream inputStream = connection.getInputStream();
			
			// Decode that input stream into a Bitmap
			return BitmapFactory.decodeStream(inputStream);
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while downloading image!", e);
			
			return null;
		}
	}
}