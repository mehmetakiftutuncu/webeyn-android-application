package com.webeyn.android;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * A custom {@link Drawable} implementation for the images in each {@link Item} summary
 * 
 * @author ezgihacihalil
 * @author mehmetakiftutuncu
 */
@SuppressWarnings("deprecation")
public class URLDrawable extends BitmapDrawable
{
	// the drawable that you need to set, you could set the initial drawing
	// with the loading image if you need to
	protected Drawable drawable;

	@Override
	public void draw(Canvas canvas)
	{
		// override the draw to facilitate refresh function later
		if(drawable != null)
		{
			drawable.draw(canvas);
		}
	}
}