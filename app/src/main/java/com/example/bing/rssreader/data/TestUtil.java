package com.example.bing.rssreader.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bing.rssreader.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bing on 2017/5/31.
 */

public class TestUtil {

	public static long insertData(SQLiteDatabase db, Entry entry) {
		if (db == null) {
			return -1;
		}

		long _id = -1;

		ContentValues cv = new ContentValues();
		cv.put(EntryContract.Entry.COLUMN_ID, entry.id);
		cv.put(EntryContract.Entry.COLUMN_TITLE, entry.title);
		cv.put(EntryContract.Entry.COLUMN_LINK, entry.link);
		cv.put(EntryContract.Entry.COLUMN_UPDATED, entry.updated);
		cv.put(EntryContract.Entry.COLUMN_CATEGORY, entry.category);
		cv.put(EntryContract.Entry.COLUMN_IMAGE, entry.image);

		try {
			db.beginTransaction();
			_id = db.insert(EntryContract.Entry.TABLE_NAME, null, cv);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return _id;
	}

	public static boolean deleteData(SQLiteDatabase db, long _id) {
		return db.delete(EntryContract.Entry.TABLE_NAME, EntryContract.Entry._ID + "=" +_id, null) > 0;
	}

	public static long checkDataBaseRecord(SQLiteDatabase db, Entry entry) {

		String[] selectionArguments = new String[]{entry.title};
		String[] projection = new String[]{EntryContract.Entry._ID};

		Cursor cursor = db.query(
				EntryContract.Entry.TABLE_NAME,
				projection,
				EntryContract.Entry.COLUMN_TITLE + " = ? ",
				selectionArguments,
				null,
				null,
				null);
		if(cursor.getCount() <= 0) {
			cursor.close();
			return -1;
		} else {
			cursor.moveToFirst();
			long _id = cursor.getLong(0);
			cursor.close();
			return _id;
		}

	}
}
