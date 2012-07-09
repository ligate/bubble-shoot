package com.likeapp.game.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕信息类
 */
public final class ScreenUtil {
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(DisplayMetrics metrics, float dipValue) {
		float scale = metrics.density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(DisplayMetrics metrics, float pxValue) {
		final float scale = metrics.density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 判断是否为hdpi屏幕
	 * @param ctx
	 * @return
	 */
	public static boolean isLargeScreen (Context ctx){
		DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
		int widthPixel = metrics.widthPixels;
		int heightPixel = metrics.heightPixels;

		int tmpMaxPixel = widthPixel > heightPixel ? widthPixel : heightPixel;
		if (tmpMaxPixel >= 800) {
			return true;
		} else {
			return false;
		}
    }
}
