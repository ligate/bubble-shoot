package com.likeapp.game.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.admogo.AdMogoLayout;

public class MgAdUtils {
	/**
	 * 初始化芒果广告
	 * 
	 * @param pActivity
	 * @param adLayout
	 */
	public static void initAdsmogo(Activity pActivity, LinearLayout adLayout, String mgKey, boolean isBottom) {
		//anroid1.6 及 1.6以下的不加载广告
		if(Integer.parseInt(Build.VERSION.SDK)>4){
			adLayout = null;
			AdMogoLayout adMogoLayoutCode = new AdMogoLayout(pActivity, mgKey);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			// 设置广告出现的位置(悬浮于底部)
			if (isBottom) {
				params.bottomMargin = 0;
				params.gravity = Gravity.BOTTOM;
			}
			if (adLayout != null) {
				adLayout.addView(adMogoLayoutCode, params);
			} else {
				pActivity.addContentView(adMogoLayoutCode, params);
			}
		}
	}
}
