package com.webeyn.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.webeyn.android.R;

/**
 * Contains utility methods related to downloading and caching of images and data of {@link Item}s
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class NetworkUtilities
{
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_NetworkUtilities";
	
	/**
	 * Checks the network availability
	 * 
	 * @param context Context to access system services
	 * 
	 * @return true if there is a network connection, false otherwise
	 */
	public static boolean isNetworkAvailable(Context context)
	{
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    
	    if(networkInfo != null && networkInfo.isConnected())
	    {
	        return true;
	    }
	    
	    Log.e(DEBUG_TAG, "No network connection is available!");
	    
	    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
	    
	    return false;
	}
}