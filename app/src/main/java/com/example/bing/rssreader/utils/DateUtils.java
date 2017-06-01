package com.example.bing.rssreader.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bing on 2017/5/30.
 */

public class DateUtils {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

	public static String longToString(long longDate) {
		Date date = new Date(longDate);
		String str = dateFormat.format(date);
		return str;
	}
}
