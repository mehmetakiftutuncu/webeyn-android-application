package com.webeyn.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
		public Button numberOfComments;
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
			holder.numberOfComments = (Button) itemRow.findViewById(R.id.button_item_numberOfComments);
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
		
		//myHolder.summary.setText(mItems.get(position).getSummary());
		Spanned html = Html.fromHtml(mItems.get(position).getSummary(), new URLImageParser(myHolder.image, mContext), null);
		myHolder.summary.setText(html);
		
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
}