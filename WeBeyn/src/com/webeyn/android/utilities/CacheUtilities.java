package com.webeyn.android.utilities;

import java.io.File;

import android.os.Environment;
import android.util.Log;

import com.webeyn.android.Constants;
import com.webeyn.android.Item;

/**
 * Contains utility methods related to caching of images of {@link Item}s
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class CacheUtilities
{
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_CacheUtilities";
	
	/**
	 * Checks whether a file is cached or not
	 * 
	 * @param fileName Name (only name, not including full path) of the cached file
	 * 
	 * @return true if the given file is cached, false if it is not cached
	 */
	public static boolean isFileCached(String fileName)
	{
		// Initialize the cache directory first, if fails, return false
		if(!initializeCacheDirectory())
		{
			return false;
		}
		
		File file = new File(Constants.CACHE_DIRECTORY + fileName);
		
		return file.exists();
	}
	
	/**
	 * Initializes the cache directory and makes sure it can be accessed
	 * 
	 * @return true if everything is OK,
	 * false if initialization failed due to inability to access the cache directory
	 */
	public static boolean initializeCacheDirectory()
	{
		// Check if the external media is available in read/write mode
		String state = Environment.getExternalStorageState();
		if(!Environment.MEDIA_MOUNTED.equals(state))
		{
			return false;
		}
		
		// Create a file for cache directory
		File cacheDirectory = new File(Constants.CACHE_DIRECTORY);
		
		/* Create directories and sub-directories for cache directory.
		 * If this returns false, check if cache directory exists.
		 * If it does, the result is true. If it doesn't than there is a problem.
		 * Result is false.
		 */
		boolean result = cacheDirectory.mkdirs() ? true : cacheDirectory.exists();
		
		if(!result)
		{
			Log.e(DEBUG_TAG, "Error occurred while accessing cache directory!");
		}
		
		return result;
	}
	
	/**
	 * Deletes cached entries older than a day
	 */
	public static void clearOldCachedItems()
	{
		// Initialize the cache directory first, if fails, return
		if(!initializeCacheDirectory())
		{
			return;
		}
		
		try
		{
			// Define the length of a day in milliseconds
			final long lengthOfADay = 1000 * 60 * 60 * 24;
			
			// Create a file for cache directory
			File cacheDirectory = new File(Constants.CACHE_DIRECTORY);
			
			// For every file in cache directory
			for(File file : cacheDirectory.listFiles())
			{
				// Get file name
				String fileName = file.getName();
				
				/* Get the first part of the file name because it contains
				 * the milliseconds value of the publishDate of the post that this cached item belonged to
				 */
				String timeTag = fileName.split("_")[0];
				
				// Set the times
				long time = Long.parseLong(timeTag);
				long currentTime = System.currentTimeMillis();
				
				// Check if the cached item is old enough
				if(currentTime - time > lengthOfADay)
				{
					// It is old, delete
					file.delete();
				}
			}
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while clearing the cache!", e);
		}
	}
}