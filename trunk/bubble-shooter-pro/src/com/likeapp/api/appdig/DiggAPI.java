package com.likeapp.api.appdig;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DiggAPI implements DiggConstant {

	public static void start(Context ctx) {
		Context context = ctx;
		long lastDplTime = DiggUtils.getLastDispalyTime(context);
		long curTime = System.currentTimeMillis();
		
		boolean blNetWorkAvailable = DiggUtils.isNetWorkAvailable(context);
		boolean isInstalled = DiggUtils.isInstalled(context, DIGG_APP_PKGNAME);
		//1.包名没有被安装过，2.有网络连接
		if (blNetWorkAvailable && !isInstalled) {
			// 没有时间key(lastDplTime < 0)：1.第一次下载 2.第一次升级到这个版本
			// 两种情况：没有时间key和超过时间间隔，都必须弹出DIGG窗口
			if (lastDplTime < 0 || (curTime - lastDplTime) >= DIGG_PERIOD_TIME) {
				DiggUtils.setLastDispalyTime(context, curTime);
				
				Intent it = new Intent();
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				it.setClass(context, DiggActivity.class);
				it.putExtra(DiggConstant.IS_AUTO_CLOSE_KEY, true);
				context.startActivity(it);
			}
		}
	}
	
	public static void openMoreBoard(Context ctx) {
		Context context = ctx;
		//long lastDplTime = DiggUtils.getLastDispalyTime(context);
		//long curTime = System.currentTimeMillis();
		
		//boolean blNetWorkAvailable = DiggUtils.isNetWorkAvailable(context);
		//boolean isInstalled = DiggUtils.isInstalled(context, DIGG_APP_PKGNAME);
		//1.包名没有被安装过，2.有网络连接
		//if (blNetWorkAvailable) {
			// 没有时间key(lastDplTime < 0)：1.第一次下载 2.第一次升级到这个版本
			// 两种情况：没有时间key和超过时间间隔，都必须弹出DIGG窗口
			//if (lastDplTime < 0 || (curTime - lastDplTime) >= DIGG_PERIOD_TIME) {
				//DiggUtils.setLastDispalyTime(context, curTime);
				
				Intent it = new Intent();
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				it.setClass(context, DiggActivity.class);
				it.putExtra(DiggConstant.IS_AUTO_CLOSE_KEY, false);
				context.startActivity(it);
			//}
		//}
	}
}
