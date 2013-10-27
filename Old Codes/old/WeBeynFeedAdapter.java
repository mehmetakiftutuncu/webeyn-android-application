package com.webeyn.android.old;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.webeyn.android.R;

/**
 * An adapter that supplies the feed list with items from
 * an {@link ArrayList} of {@link Item}s and sets how each item
 * in the list is defined
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class WeBeynFeedAdapter extends ArrayAdapter<Item>
{
	/**	Format definition of the publish date of an {@link Item} */
	private static final String PUBLISH_DATE_FORMAT = "d MMMM, yyyy - HH:mm";
	
	/** {@link Context} of the {@link Activity} that has the {@link ListView} that will use this adapter */
	private Context mContext;
	/** {@link ArrayList} of {@link Item}s that will be shown in {@link ListView} that will use this adapter */
	private ArrayList<Item> mItems;
	
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_FeedAdapter";
	
	/**
	 * Constructs a {@link WeBeynFeedAdapter}
	 * 
	 * @param context {@link WeBeynFeedAdapter#mContext}
	 * @param items {@link WeBeynFeedAdapter#mItems}
	 */
	public WeBeynFeedAdapter(Context context, ArrayList<Item> items)
	{
		super(context, R.layout.layout_item, items);
		
		mContext = context;
		mItems = items;
	}
	
	/**
	 * Holder class for each {@link Item} containing references to it's components
	 */
	static class ViewHolder
	{
		public TextView title;
		public TextView creator;
		public TextView publishDate;
		public TextView numberOfComments;
		public ImageView image;
		public TextView summary;
	}
	
	/* Defines how each item is generated and called every time that item is visible */
	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		/* Get the convert view that is given by the system first
		 * to check if we can still use the existing resource. */
		View itemRow = convertView;
		
		/* If convert view is null
		 * 
		 * This means either this item has never been seen, therefore created before,
		 * or the system killed it's resources once it was not visible.
		 * 
		 * In this case, a new view is going to be inflated and set
		 */
		if(itemRow == null)
		{
			// Get layout inflater service of the system
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// Inflate the layout for an item
			itemRow = inflater.inflate(R.layout.layout_item, parent, false);
			
			// Create a view holder for this item since it is a newly created one
			ViewHolder holder = new ViewHolder();
			
			// Get references to each component of the item into it's view holder
			holder.title = (TextView) itemRow.findViewById(R.id.textView_item_title);
			holder.creator = (TextView) itemRow.findViewById(R.id.textView_item_creator);
			holder.publishDate = (TextView) itemRow.findViewById(R.id.textView_item_publishDate);
			holder.numberOfComments = (TextView) itemRow.findViewById(R.id.textView_item_numberOfComments);
			holder.image = (ImageView) itemRow.findViewById(R.id.imageView_item_image);
			holder.summary = (TextView) itemRow.findViewById(R.id.textView_item_summary);
			
			/* Set the defined view holder as the tag of this item's view object
			 * so references to it's components can easily be accessed later */
			itemRow.setTag(holder);
		}
		
		// Get the item's view holder (At this point, we are sure that it has one.)
		ViewHolder myHolder = (ViewHolder) itemRow.getTag();
		
		// Set the information in item's view using it's holder with the item object's information at current position in the list
		myHolder.title.setText(mItems.get(position).getTitle());
		myHolder.creator.setText(mContext.getString(R.string.item_creator, mItems.get(position).getCreator()));
		myHolder.publishDate.setText(new SimpleDateFormat(PUBLISH_DATE_FORMAT).format(mItems.get(position).getPublishDate()));
		myHolder.numberOfComments.setText(mContext.getString(R.string.item_numberOfComments, mItems.get(position).getNumberOfComments()));
		
		//myHolder.summary.setText(mItems.get(position).getContent());
		myHolder.summary.setText(Html.fromHtml(mItems.get(position).getContent()));
		
		myHolder.image.setImageBitmap(getImage(mItems.get(position)));
		
		myHolder.numberOfComments.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mContext, PostViewerActivity.class);
				intent.putExtra(Constants.TAG_EXTRA_LINK, mItems.get(position).getCommentsLink());
				((Activity) mContext).startActivity(intent);
			}
		});
		
		// Return the view of current item all of whose information we just set
		return itemRow;
	}
	
	/**
	 * Gets the image of given post item either from the {@link Item} itself,
	 * by loading it from cache if it is not in memory, or downloading if it is in cache
	 * 
	 * @param item {@link Item} whose image is wanted
	 * 
	 * @return Image of the given {@link Item} or null if any error occurs
	 */
	private Bitmap getImage(Item item)
	{
		try
		{
			// Get and check the image in item object
			Bitmap result = item.getImage();
			
			// If image is not set in the item object
			if(result == null)
			{
				// Check the cache first
				if(CacheUtilities.isFileCached(item.getImageCacheTag()))
				{
					// Image is cached, load it from cache
					result = new ImageLoader().execute(item.getImageCacheTag()).get();
				}
				else
				{
					// Image is not cached, download it first
					result = new ImageDownloader().execute(item.getImageLink()).get();
					
					// If successfully downloaded
					if(result != null)
					{
						// Cache the image
						new ImageSaver(result).execute(item.getImageCacheTag());
					}
				}
				
				// If successfully loaded
				if(result != null)
				{
					// Set the image for the item
					item.setImage(result);
				}
			}
			
			return result;
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while getting image!", e);
			
			return null;
		}
	}
}