package com.example.bing.rssreader.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by Bing on 2017/5/30.
 */

public class ModelUtils {
	private static Gson gson = new Gson();

	public static <T> T toObject(String json, TypeToken<T> typeToken) {
		return gson.fromJson(json, typeToken.getType());
	}

	public static <T> String toString(T object, TypeToken<T> typeToken) {
		return gson.toJson(object, typeToken.getType());
	}
}
