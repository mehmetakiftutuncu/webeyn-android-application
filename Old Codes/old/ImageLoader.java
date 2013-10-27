package com.webeyn.android.old;

import java.io.File;
import java.io.FileInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

/**
 * An asynchronous task to load a previously downloaded and saved image from disk
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap>
{
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_ImageLoader";
	
	@Override
	protected Bitmap doInBackground(String... params)
	{
		try
		{
			// Initialize the cache directory first, if fails, return null
			if(!CacheUtilities.initializeCacheDirectory())
			{
				return null;
			}
			
			// Open a file and a file input stream to read
			File file = new File(Constants.CACHE_DIRECTORY + params[0]);
			FileInputStream fileInputStream = new FileInputStream(file);
			
			// Decode that input stream into a Bitmap
			return BitmapFactory.decodeStream(fileInputStream);
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while loading image!", e);
			
			return null;
		}
	}
}