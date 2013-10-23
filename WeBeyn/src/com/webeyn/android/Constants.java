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
	
	/** URI for the market page of the application */
	public static final String URI_MARKET_PAGE = "market://details?id=com.webeyn.android";
	
	/**	E-mail contact address of the developer */
	public static final String CONTACT = "webeyn@gmail.com";
	/**	URL of developer web site */
	public static final String WEBSITE_URL = "http://www.webeyn.com";
}