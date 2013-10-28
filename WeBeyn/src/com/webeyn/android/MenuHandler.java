package com.webeyn.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

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
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void handle(Context context, MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				((MainActivity) context).loadWebeyn();
				break;
				
			case R.id.item_copyLink:
				WebView webView = ((MainActivity) context).getWebView();
				if(webView != null)
				{
					String textToClip = webView.getTitle() + " " + webView.getUrl();
					
					if(Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
					{
						android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
						clipboard.setText(textToClip);
					}
					else
					{
						android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
						android.content.ClipData clip = android.content.ClipData.newPlainText("WeBeyn", textToClip);
						clipboard.setPrimaryClip(clip);
					}
					
					Toast.makeText(context, context.getString(R.string.linkCopied), Toast.LENGTH_SHORT).show();
				}
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
				
			case R.id.item_about:
				showAboutDialog(context);
				break;
		}
	}
	
	/**
     * Shows the about dialog when user selected about in the menu
     * 
     * @param context Context of the activity
     */
	public static void showAboutDialog(Context context)
	{
		AlertDialog dialog;
    	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    	
    	// Start preparing dialog
    	String versionName = "";
		try
		{
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		dialogBuilder.setTitle(context.getString(R.string.aboutDialog_title, versionName));
		dialogBuilder.setMessage(context.getResources().getText(R.string.aboutDialog_message));
		dialogBuilder.setPositiveButton(context.getString(R.string.dialog_ok), new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		dialogBuilder.setIcon(R.drawable.ic_launcher);
		
		dialog = dialogBuilder.create();
		dialog.show();
	}
}