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
 * Bubble-Shooter-Pro Project:http://code.google.com/p/bubble-shoot/
 */
package com.likeapp.game.bubbleshooter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.likeapp.game.bubbleshooter.arcade.ScoreManager;
import com.likeapp.game.utils.ScreenUtil;



public class RankPopupWindowManager{
	private PopupWindow mPopupWindow;
	private TextView mButtonScoreRank;
	private TextView mButtonLevelRank;
	private TextView mButtonHonor;
	
	static Activity act = null;
	public RankPopupWindowManager(Activity act){
		RankPopupWindowManager.act= act;
	}
	public void setupPopup(Context mContext) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.rank_popup_menu, null);
		mPopupWindow = new PopupWindow(view);
		mPopupWindow.setAnimationStyle(R.style.RankPopupAnimation);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setWidth(ScreenUtil.dip2px(mContext, 320));
		mPopupWindow.setHeight(ScreenUtil.dip2px(mContext, 250));

		mButtonScoreRank = (TextView) view.findViewById(R.id.scorerank);
		mButtonLevelRank = (TextView) view.findViewById(R.id.levelrank);
		mButtonHonor = (TextView)view.findViewById(R.id.honor);
		
		mButtonScoreRank.setOnClickListener(onPopupItemClickListener);
		mButtonLevelRank.setOnClickListener(onPopupItemClickListener);
		mButtonHonor.setOnClickListener(onPopupItemClickListener);
		
		
	}

	public void showPopup(View v) {
		if(mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}else{
			mPopupWindow.showAtLocation(v, 0, 0, 0);
		}
	}
	
	public boolean isRankWindowShowing() {
		if (mPopupWindow != null) {
			return mPopupWindow.isShowing();
		} else {
			return false;
		}
	}

	public void dissmissRankWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	private OnClickListener onPopupItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mPopupWindow.dismiss();

			if (v == mButtonScoreRank) {
				ScoreManager.getInstance().submitArcadeThenOpenLeadboardActivity(act);
			} else if (v == mButtonLevelRank) {
				ScoreManager.getInstance().submitPuzzleThenOpenLeadboardActivity(act);
			}else if(v == mButtonHonor){
				ScoreManager.getInstance().checkHonor(act);
				ScoreManager.getInstance().openHonor(act);
			}
		}
	};
}
