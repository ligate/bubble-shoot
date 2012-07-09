package com.likeapp.game.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ViewUtils {
	
	public static View inflate(final Context pContext, final int pLayoutID){
		final LayoutInflater inflater = LayoutInflater.from(pContext);
		return inflater.inflate(pLayoutID, null);
	}
}
