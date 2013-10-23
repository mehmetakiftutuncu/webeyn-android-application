package com.webeyn.android.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import com.webeyn.android.Constants;
import com.webeyn.android.R;

/**
 * A utility class for handling menu actions
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class MenuHandler
{
	/**
	 * Handle the menu item click
	 * 
	 * @param context Context of the activity
	 * @param item Menu item that is clicked
	 */
	public static void handle(Context context, MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				((Activity) context).finish();
				break;
				
			case R.id.item_rate:
				Intent intentRate = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URI_MARKET_PAGE));
				context.startActivity(Intent.createChooser(intentRate, context.getString(R.string.menu_rate)));
				break;
				
			case R.id.item_contact:
				Intent intentContact = new Intent(Intent.ACTION_SEND);
				intentContact.setType("message/rfc822");
				intentContact.putExtra(Intent.EXTRA_EMAIL, new String[] {Constants.CONTACT});
				intentContact.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.contact_subject));
				context.startActivity(Intent.createChooser(intentContact, context.getString(R.string.menu_contact)));
				break;
				
			case R.id.item_website:
				Intent intentWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEBSITE_URL));
				context.startActivity(Intent.createChooser(intentWebsite, context.getString(R.string.menu_website)));
				break;
				
			case R.id.item_about:
				break;
		}
	}
}