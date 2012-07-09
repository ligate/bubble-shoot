package com.likeapp.api.appdig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DiggUtils {
	private static final String DIGG_PREFERENCES = "digg.preferences";

	public static boolean isNetWorkAvailable(Context context) {
		boolean result = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (null != connectivityManager) {
			networkInfo = connectivityManager.getActiveNetworkInfo();
			if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
				result = true;
			}
		}
		return result;
	}

	public static boolean isInstalled(Context ctx, String packageName) {
		SharedPreferences sp = ctx.getSharedPreferences(DIGG_PREFERENCES, Context.MODE_PRIVATE);
		if (sp.contains(packageName)) {
			return true;
		} else {
			PackageManager pm = ctx.getPackageManager();
			try {
				pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
				sp.edit().putInt(packageName, 1).commit();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public static boolean setLastDispalyTime(Context context, long value) {
		SharedPreferences sp = context.getSharedPreferences(DIGG_PREFERENCES, Context.MODE_PRIVATE);
		return sp.edit().putLong("last_dig_time", value).commit();
	}

	public static long getLastDispalyTime(Context context) {
		SharedPreferences sp = context.getSharedPreferences(DIGG_PREFERENCES, Context.MODE_PRIVATE);
		long value = sp.getLong("last_dig_time", -1);
		return value;
	}
}
