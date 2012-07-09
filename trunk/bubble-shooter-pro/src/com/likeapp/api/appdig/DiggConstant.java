package com.likeapp.api.appdig;

public interface DiggConstant {
	// 要推广的包名
	public static final String DIGG_APP_PKGNAME = "com.appcup.bubble";

	// 推广的时间间隔，现在定为5天，单位：毫秒
	public static final long DIGG_PERIOD_TIME = 5 * 24 * 60 * 60 * 1000;

	// 自动关闭窗口的时间长度，单位：毫秒
	public static final int ATUO_CLOSE_TIME = 8000;
	
	public static final String IS_AUTO_CLOSE_KEY = "isAutoClose";
}
