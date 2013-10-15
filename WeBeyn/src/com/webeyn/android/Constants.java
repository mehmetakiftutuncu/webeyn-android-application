package com.webeyn.android;

import android.os.Environment;

/**
 * Constant definitions
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class Constants
{
	/** URL of the post to view */
	public static final String TAG_EXTRA_LINK = "com.webeyn.android.PostViewerActivity.link";
	
	/** Path to the caching directory */
	public static final String CACHE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.webeyn.android/cache/";
}