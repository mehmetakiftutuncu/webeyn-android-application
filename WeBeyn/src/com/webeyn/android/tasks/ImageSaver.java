package com.webeyn.android.tasks;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.webeyn.android.Constants;
import com.webeyn.android.utilities.CacheUtilities;

/**
 * An asynchronous task to save a downloaded image to disk
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class ImageSaver extends AsyncTask<String, Void, Void>
{
	private Bitmap bitmap;
	
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_ImageSaver";
	
	/**
	 * Constructs an {@link ImageSaver} specifying what Bitmap will be saved
	 * 
	 * @param bitmap Bitmap to be saved
	 */
	public ImageSaver(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	@Override
	protected Void doInBackground(String... params)
	{
		try
		{
			// Initialize the cache directory first, if fails, return null
			if(!CacheUtilities.initializeCacheDirectory())
			{
				return null;
			}
			
			// Open a file and a file output stream to write
			File file = new File(Constants.CACHE_DIRECTORY + params[0]);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			
			// Compress the bitmap and write it into the output stream
			bitmap.compress(Bitmap.CompressFormat.PNG, 75, fileOutputStream);
			
			// Flush and close the output stream
			fileOutputStream.flush();
			fileOutputStream.close();
			
			// Since we just saved a new item to the cache, delete the old items if possible
			CacheUtilities.clearOldCachedItems();
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while saving the image!", e);
		}
		
		return null;
	}
}