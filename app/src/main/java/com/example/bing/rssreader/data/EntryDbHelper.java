package com.example.bing.rssreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bing.rssreader.data.EntryContract.*;

/**
 * Created by Bing on 2017/5/31.
 */

public class EntryDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "entry.db";
	private static final int DATABASE_VERSION = 1;

	public EntryDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + Entry.TABLE_NAME + " (" +

				Entry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +

				Entry.COLUMN_ID         + " TEXT NOT NULL, "    +

				Entry.COLUMN_TITLE      + " TEXT NOT NULL, "    +

				Entry.COLUMN_LINK       + " TEXT NOT NULL, "    +

				Entry.COLUMN_UPDATED    + " INTEGER NOT NULL, " +

				Entry.COLUMN_CATEGORY   + " TEXT NOT NULL, "    +

				Entry.COLUMN_IMAGE      + " TEXT DEFAULT NULL" + "); ";

		db.execSQL(SQL_CREATE_WAITLIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
		onCreate(db);
	}
}
