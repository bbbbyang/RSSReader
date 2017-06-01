package com.example.bing.rssreader.model;

import android.support.test.espresso.core.deps.guava.base.Joiner;
import static com.example.bing.rssreader.utils.GetImgStr.getImgStr;

/**
 * Created by Bing on 2017/5/30.
 */

public class Entry {
	public long _id;
	public final String id;
	public final String title;
	public final String link;
	public final long updated;
	public final String category;
	public final String content;
	public final String image;
	public boolean like;

	public Entry(String id, String title, String link, long updated, String category, String content, String image) {
		this._id = -1;
		this.id = id;
		this.title = title;
		this.link = link;
		this.updated = updated;
		this.category = category;
		this.content = content;
		this.image = Joiner.on(" ").join(getImgStr(this.content));
		this.like = false;
	}


}