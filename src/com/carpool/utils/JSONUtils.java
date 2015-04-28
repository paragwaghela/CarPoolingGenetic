package com.carpool.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {
	
	private static String TAG="JSONUtils";

	public static String getString(final JSONObject obj, final String name) {
		try {
			return obj.getString(name);
		} catch (Exception e) {

		}
		return "";
	}

	public static int getInt(final JSONObject obj, final String name) {
		try {
			return obj.getInt(name);
		} catch (Exception e) {

		}
		return 0;
	}

	public static double getDouble(final JSONObject obj, final String name) {
		try {
			return obj.getDouble(name);
		} catch (Exception e) {

		}
		return 0;
	}

	public static boolean getBoolean(final JSONObject obj, final String name) {
		try {
			return obj.getBoolean(name);
		} catch (Exception e) {

		}
		return false;
	}

	public static long getLong(final JSONObject obj, final String name) {
		try {
			return obj.getLong(name);
		} catch (Exception e) {

		}
		return 0;
	}

	public static JSONArray getJSONArray(final JSONObject obj, final String name) {
		try {
			return obj.getJSONArray(name);
		} catch (Exception e) {

		}
		return null;
	}

	public static JSONObject getJSONObject(final JSONObject obj,
			final String name) {
		try {
			return obj.getJSONObject(name);
		} catch (Exception e) {

		}
		return null;
	}
}
