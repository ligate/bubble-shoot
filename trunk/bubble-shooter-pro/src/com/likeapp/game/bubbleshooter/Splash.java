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

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.likeapp.api.appdig.DiggAPI;
import com.likeapp.game.bubbleshooter.arcade.ScoreManager;
/**
 * 
 * @author jackyli
 */
public class Splash extends Activity implements OnClickListener {
	// ----------
	public boolean isDownloadZipFile = false;
	// ----------
	private Button resumeButton;
	private Button newButton;
	private Button selLevelButton;
	private Button moreAppButton;
	private Button helpButton;
	private Button appPointButton;
	private SharedPreferences sp;
	private int unLockLevel;
	private int REMCOMMEND_LIMIT_UNLOCKLEVEL = 50;// 当用户玩关卡解锁到指定的级别才开始推荐
	private RankPopupWindowManager popupManager;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		
		// Crash Handler
		//CrashHandler.getInstance().init(getApplicationContext());
		
		// ad
		final LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
		// AdUtils.initAd(this, adLayout);
		resumeButton = (Button) findViewById(R.id.resumeGameButton);
		resumeButton.setOnClickListener(this);
		newButton = (Button) findViewById(R.id.newGameButton);
		newButton.setOnClickListener(this);
		selLevelButton = (Button) findViewById(R.id.selectLevelButton);
		selLevelButton.setOnClickListener(this);
		moreAppButton = (Button) findViewById(R.id.moreAppButton);
		moreAppButton.setOnClickListener(this);
		//moreAppButton.setVisibility(View.GONE);
		helpButton = (Button) this.findViewById(R.id.helpButton);
		helpButton.setOnClickListener(this);
		appPointButton = (Button) this.findViewById(R.id.appPointButton);
		appPointButton.setOnClickListener(this);
		// 中文用户才可 使用积分墙功能
		appPointButton.setVisibility("CN".equals(Locale.getDefault()
				.getCountry()) ? View.VISIBLE : View.GONE);
		//
		appPointButton.setVisibility(View.GONE);

		findViewById(R.id.btnArcade).setOnClickListener(this);
		findViewById(R.id.btnLeaderboard).setOnClickListener(this);

		

		// 兼容旧版本
		sp = this.getSharedPreferences(BubbleShooterActivity.PREFS_NAME,
				Context.MODE_PRIVATE);
		unLockLevel = sp.getInt(
				BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);
		int level = sp.getInt(BubbleShooterActivity.PREFS_LEVEL_KEY_NAME, 0);
		if (level > unLockLevel) {
			unLockLevel = level;
			sp.edit()
					.putInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME,
							unLockLevel).commit();
		}
		
		
		
		GameConfig.getInstance().init(this);
		ScoreManager.getInstance().init(this);
		//Log.i("jackyli", ScoreManager.OPEN_FEINT_NAME);
		popupManager = new RankPopupWindowManager(this);
		popupManager.setupPopup(getApplicationContext());
		
		//推荐
		DiggAPI.start(getApplicationContext());
		//ScoreManager.getInstance().initGameCenter(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		ImageView btnLeaderboard = (ImageView) findViewById(R.id.btnLeaderboard);
//		btnLeaderboard.setBackgroundResource(R.drawable.rank);
		AnimationDrawable leaderBoardAnim = (AnimationDrawable) btnLeaderboard.getBackground();
		leaderBoardAnim.start();
		
	}

	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.resumeGameButton:
			// 继续游戏,保持原来的关卡
			i = new Intent(this, BubbleShooterActivity.class);
			break;
		case R.id.newGameButton:
			// 重新开始游戏,即关卡复们为0
			i = new Intent(this, BubbleShooterActivity.class);
			// i.putExtra("levelCustom", 0);
			sp.edit().putInt(BubbleShooterActivity.PREFS_LEVEL_KEY_NAME, 0)
					.commit();
			break;
		case R.id.selectLevelButton:
			// 关卡选择
			// this.showDialog(DIALOG_SELECT_LEVELS);
			i = new Intent(this, LevelSelectorActivity.class);
			break;

		case R.id.btnArcade:
			i = new Intent(this, BubbleArcadeActivity.class);
			break;

		case R.id.moreAppButton:
			/*i = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://search?q=pub:\"AL GAME\""));*/
			//推荐
			 DiggAPI.openMoreBoard(getApplicationContext());
			 Log.i("jackyli","打开推荐");
			break;
		case R.id.helpButton:
			// 帮助
			break;
		
		case R.id.btnLeaderboard:
			try{
				popupManager.showPopup(v);
			}catch(Exception e){}
			break;
		}
		if (i != null) {
			startActivity(i);
		}
	}

	@Override
	public void finish() {

		super.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && popupManager.isRankWindowShowing()) {
			//if(popupManager.isRankWindowShowing()){
				popupManager.dissmissRankWindow();
				return true;
			//}
			
			//showDialog(EXIT_CONFIRM_DIALOG);
			//return false;

		}
		return super.onKeyDown(keyCode, event);
	}
}
