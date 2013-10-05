package com.webeyn.android;

import java.util.ArrayList;
import java.util.Date;


/**
 * Represents a feed item which is a post on WeBeyn
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
public class Item
{
	/** Tag in RSS feed corresponding to {@link Item#title} */
	public static final String TAG_TITLE = "title";
	/** Tag in RSS feed corresponding to {@link Item#summary} */
	public static final String TAG_SUMMARY = "description";
	/** Tag in RSS feed corresponding to {@link Item#link} */
	public static final String TAG_LINK = "link";
	/** Tag in RSS feed corresponding to {@link Item#publishDate} */
	public static final String TAG_PUBLISH_DATE = "pubDate";
	/** Tag in RSS feed corresponding to {@link Item#creator} */
	public static final String TAG_CREATOR = "dc:creator";
	/** Tag in RSS feed corresponding to one category in {@link Item#categories} */
	public static final String TAG_CATEGORY = "category";
	/** Tag in RSS feed corresponding to {@link Item#numberOfComments} */
	public static final String TAG_NUMBER_OF_COMMENTS = "splash:comments";
	
	/** Suffix to use while generating {@link Item#commentsLink} */
	private static final String COMMENTS_LINK_SUFFIX = "#comments";
	/** Suffix to use while generating {@link Item#commentsRss} */
	private static final String COMMENTS_RSS_SUFFIX = "feed";
	
	/** Title of the post */
	private String title;
	/** Summary of the post */
	private String summary;
	/** URL of the post */
	private String link;
	/** Date the post was publish */
	private Date publishDate;
	/** Creator of the post */
	private String creator;
	/** Categories that the post is in */
	private ArrayList<String> categories;
	/** Number of comments posted for the post */
	private int numberOfComments;
	
	/**
	 * Constructs a post item specifying all it's attributes
	 */
	public Item(	String title, String summary, String link,
					Date publishDate, String creator,
					ArrayList<String> categories, int numberOfComments)
	{
		setTitle(title);
		setSummary(summary);
		setLink(link);
		setPublishDate(publishDate);
		setCreator(creator);
		setCategories(categories);
		setNumberOfComments(numberOfComments);
	}
	
	/**
	 * @return {@link Item#title}
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * @return {@link Item#summary}
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * @return {@link Item#link}
	 */
	public String getLink()
	{
		return link;
	}
	
	/**
	 * @return {@link Item#publishDate}
	 */
	public Date getPublishDate()
	{
		return publishDate;
	}

	/**
	 * @return {@link Item#creator}
	 */
	public String getCreator()
	{
		return creator;
	}

	/**
	 * @return {@link Item#categories}
	 */
	public ArrayList<String> getCategories()
	{
		return categories;
	}

	/**
	 * @return {@link Item#numberOfComments}
	 */
	public int getNumberOfComments()
	{
		return numberOfComments;
	}

	/**
	 * @return URL to the comments section of the post
	 */
	public String getCommentsLink()
	{
		return link + COMMENTS_LINK_SUFFIX;
	}

	/**
	 * @return URL for RSS feed of the comments of the post
	 */
	public String getCommentsRss()
	{
		return link + COMMENTS_RSS_SUFFIX;
	}

	/**
	 * Sets the {@link Item#title}
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Sets the {@link Item#summary}
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	
	/**
	 * Sets the {@link Item#link}
	 */
	public void setLink(String link)
	{
		this.link = link;
	}
	
	/**
	 * Sets the {@link Item#publishDate}
	 */
	public void setPublishDate(Date publishDate)
	{
		this.publishDate = publishDate;
	}
	
	/**
	 * Sets the {@link Item#creator}
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}
	
	/**
	 * Sets the {@link Item#categories}
	 */
	public void setCategories(ArrayList<String> categories)
	{
		this.categories = categories;
	}
	
	/**
	 * Sets the {@link Item#numberOfComments}
	 */
	public void setNumberOfComments(int numberOfComments)
	{
		this.numberOfComments = numberOfComments;
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		return String.format("Item [title=%s, summary=%s, link=%s, publishDate=%s, creator=%s, categories=%s, numberOfComments=%s]",
						title, summary, link, publishDate, creator, categories, numberOfComments);
	}	
}