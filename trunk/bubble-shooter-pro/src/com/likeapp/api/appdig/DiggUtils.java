/*
 *                 [[ Frozen-Bubble ]]
 *
 * Copyright (c) 2000-2003 Guillaume Cottenceau.
 * Java sourcecode - Copyright (c) 2003 Glenn Sanson.
 *
 * This code is distributed under the GNU General Public License
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *
 * Artwork:
 *    Alexis Younes <73lab at free.fr>
 *      (everything but the bubbles)
 *    Amaury Amblard-Ladurantie <amaury at linuxfr.org>
 *      (the bubbles)
 *
 * Soundtrack:
 *    Matthias Le Bidan <matthias.le_bidan at caramail.com>
 *      (the three musics and all the sound effects)
 *
 * Design & Programming:
 *    Guillaume Cottenceau <guillaume.cottenceau at free.fr>
 *      (design and manage the project, whole Perl sourcecode)
 *
 * Java version:
 *    Glenn Sanson <glenn.sanson at free.fr>
 *      (whole Java sourcecode, including JIGA classes
 *             http://glenn.sanson.free.fr/jiga/)
 *
 * Android port:
 *    Pawel Aleksander Fedorynski <pfedor@fuw.edu.pl>
 *    Copyright (c) Google Inc.
 *
 *          [[ http://glenn.sanson.free.fr/fb/ ]]
 *          [[ http://www.frozen-bubble.org/   ]]
 *          
 * Bubble-Shooter-Pro:http://code.google.com/p/bubble-shoot/
 */
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
