package com.example.bing.rssreader.data;

import android.provider.BaseColumns;

/**
 * Created by Bing on 2017/5/31.
 */

public class EntryContract {

	public static final class Entry implements BaseColumns {

		public static final String TABLE_NAME = "entry_like";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_LINK = "link";
		public static final String COLUMN_UPDATED = "updated";
		public static final String COLUMN_CATEGORY = "category";
		public static final String COLUMN_IMAGE = "image";
	}
}
