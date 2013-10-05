package com.webeyn.android;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Xml;

/**
 * XML parser for the RSS feed of WeBeyn
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class WeBeynParser
{
	/** Root tag in RSS feed */
	public static final String TAG_RSS = "rss";
	/** Tag in RSS feed containing {@link Item} definitions */
	public static final String TAG_CHANNEL = "item";
	/** Tag in RSS feed corresponding to {@link Item} */
	public static final String TAG_ITEM = "item";
	/** Format to parse the publish date from RSS feed */
	public static final String PUBLISH_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	/** Tag for debugging */
	private static final String DEBUG_TAG = "WeBeyn_Parser";

	/**
	 * Reads the XML from given input stream and parses it
	 * 
	 * @param inputStream Stream to read XML from
	 * 
	 * @return	{@link ArrayList} of {@link Item}s corresponding to the given XML RSS feed,
	 * 			null if any error occurs
	 */
	public ArrayList<Item> parse(InputStream inputStream)
	{
		try
		{
			// Create an XmlPullParser that doesn't process namespaces
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			
			// Set input as the given input stream
			parser.setInput(inputStream, null);
			
			// Find the first tag and start
			parser.nextTag();
			
			// Reed the feed and return
			return readFeed(parser);
        }
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Error occurred while parsing WeBeyn feed!", e);
			
			return null;
		}
		finally
		{
			// Whether parsing was successful or not, close the input stream
			try
			{
				inputStream.close();
			}
			catch(Exception e)
			{
				Log.e(DEBUG_TAG, "Error occurred while closing input stream!", e);
			}
		}
	}
	
	/**
	 * Parses the XML with given parser
	 * 
	 * @param parser {@link XmlPullParser} to parse the XML with
	 * 
	 * @return	{@link ArrayList} of {@link Item}s corresponding to the given XML RSS feed,
	 * 			might be of size 0
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private ArrayList<Item> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		// Resulting items
		ArrayList<Item> items = new ArrayList<Item>();
		
		// Require XML to contain root tag
		parser.require(XmlPullParser.START_TAG, null, TAG_RSS);
		
		// Get and check next tag until the tag is not end tag
		while(parser.next() != XmlPullParser.END_TAG)
		{
			// Continue if there is a start tag
			if(parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			
			// Get the name of the tag
			String rootName = parser.getName();
			
			// Check if the tag is the tag that contains item definitions
			if(rootName.equals(TAG_CHANNEL))
			{
				// Require XML to contain tag that contains item definitions
				parser.require(XmlPullParser.START_TAG, null, TAG_CHANNEL);
				
				// Get and check next tag until the tag is not end tag
				while(parser.next() != XmlPullParser.END_TAG)
				{
					// Continue if there is a start tag
					if(parser.getEventType() != XmlPullParser.START_TAG)
					{
						continue;
					}
					
					// Get the name of the tag
					String name = parser.getName();
					
					// Check if the tag is a post item, if so parse and add, if not just skip
					if(name.equals(TAG_ITEM))
					{
						items.add(readItem(parser));
					}
					else
					{
						skip(parser);
					}
				}
			}
			else
			{
				skip(parser);
			}
		}
		
		// Return resulting items
		return items;
	}
	
	/**
	 * Parses the XML with given parser to get an {@link Item}
	 * 
	 * @param parser {@link XmlPullParser} to parse the XML with
	 * 
	 * @return	{@link Item} with attributes corresponding to the given XML RSS feed
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	@SuppressLint("SimpleDateFormat")
	private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		// Require XML to contain tag of an item
		parser.require(XmlPullParser.START_TAG, null, TAG_ITEM);
		
		// Attributes of an item
		String title = null;
		String summary = null;
		String link = null;
		Date publishDate = null;
		String creator = null;
		ArrayList<String> categories = null;
		int numberOfComments = 0;
		
		// Get and check next tag until the tag is not end tag
		while(parser.next() != XmlPullParser.END_TAG)
		{
			// Continue if there is a start tag
			if(parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			
			// Get the name of the tag
			String name = parser.getName();
			
			// Now check which attribute of item is read and set that attribute accordingly
			if(name.equals(Item.TAG_TITLE))
			{
				title = read(parser);
			}
			else if(name.equals(Item.TAG_SUMMARY))
			{
				summary = read(parser);
			}
			else if(name.equals(Item.TAG_LINK))
			{
				link = read(parser);
			}
			else if(name.equals(Item.TAG_PUBLISH_DATE))
			{
				// Get the date text
				String dateText = read(parser);
				
				// Create a formatter with necessary format
				SimpleDateFormat formatter = new SimpleDateFormat(PUBLISH_DATE_FORMAT);
				
				// Parse the date and set
				try
				{
					publishDate = formatter.parse(dateText);
				}
				catch(ParseException e)
				{
					Log.e(DEBUG_TAG, "Couldn't parse publish date!", e);
				}
			}
			else if(name.equals(Item.TAG_CREATOR))
			{
				creator = read(parser);
			}
			else if(name.equals(Item.TAG_CATEGORY))
			{
				// If not initialized before, initialize categories
				if(categories == null)
				{
					categories = new ArrayList<String>();
				}
				
				// Add the read category
				categories.add(read(parser));
			}
			else if(name.equals(Item.TAG_NUMBER_OF_COMMENTS))
			{
				// Parse the value
				numberOfComments = Integer.parseInt(read(parser));
			}
			else
			{
				skip(parser);
			}
		}
		
	    return new Item(title, summary, link, publishDate, creator, categories, numberOfComments);
	}
	
	/**
	 * Reads the contents of the current tag
	 * 
	 * @param parser {@link XmlPullParser} to parse the XML with
	 * 
	 * @return	Contents of the current tag
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private String read(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		// Resulting contents
		String result = "";
		
		// If the content is text
		if(parser.next() == XmlPullParser.TEXT)
		{
			// Read contents and move to next tag
			result = parser.getText();
			parser.nextTag();
		}
		
		// Return the result
		return result;
	}
	
	/**
	 * Skips tags until the end tag of the current tag is passed
	 * 
	 * @param parser {@link XmlPullParser} to parse the XML with
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		// If the current tag of parser is not a start tag, throw exception
		if(parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
		}
		
		// Assume the depth is 1 (which can be eliminated by just the end tag of current tag)
		int depth = 1;
		
		// Until the depth is 0
		while(depth != 0)
		{
			// Go to next tag
			switch(parser.next())
			{
				// If this is an end tag, go one level up that is decrease the depth
				case XmlPullParser.END_TAG:
					depth--;
					break;
				// If this is a start tag (another tag opened inside the current tag), go one level down that is increase the depth
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}
}