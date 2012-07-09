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
 */
// This file is derived from the LunarLander.java file which is part of
// the Lunar Lander game included with Android documentation.  The copyright
// notice for the Lunar Lander is reproduced below.
/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.likeapp.game.bubbleshooter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.admogo.AdMogoManager;
import com.google.ads.AdView;
import com.likeapp.game.bubbleshooter.GameConfig.GameMode;
import com.likeapp.game.bubbleshooter.arcade.ArcadeGameView;
import com.likeapp.game.bubbleshooter.arcade.ArcadeGameView.GameThread;
import com.likeapp.game.bubbleshooter.arcade.ScoreManager;
import com.likeapp.game.utils.MgAdUtils;

public class BubbleArcadeActivity extends Activity {
	public final static int SOUND_WON = 0;
	public final static int SOUND_LOST = 1;
	public final static int SOUND_LAUNCH = 2;
	public final static int SOUND_DESTROY = 3;
	public final static int SOUND_REBOUND = 4;
	public final static int SOUND_STICK = 5;
	public final static int SOUND_HURRY = 6;
	public final static int SOUND_NEWROOT = 7;
	public final static int SOUND_NOH = 8;
	public final static int NUM_SOUNDS = 9;

	public final static int GAME_NORMAL = 0;
	public final static int GAME_COLORBLIND = 1;

	public final static int MENU_COLORBLIND_MODE_ON = 1;
	public final static int MENU_COLORBLIND_MODE_OFF = 2;
	public final static int MENU_FULLSCREEN_ON = 3;
	public final static int MENU_FULLSCREEN_OFF = 4;
	public final static int MENU_SOUND_ON = 5;
	public final static int MENU_SOUND_OFF = 6;
	public final static int MENU_DONT_RUSH_ME = 7;
	public final static int MENU_RUSH_ME = 8;
	public final static int MENU_NEW_GAME = 9;
	public final static int MENU_ABOUT = 10;
	public final static int MENU_EDITOR = 11;


	public final static String PREFS_NAME = "frozenbubble_arcade";
	public final static String PREFS_LEVEL_KEY_NAME = "level";// 当前关卡索引
	public final static String PREFS_UNLOCK_LEVEL_KEY_NAME = "Unlock_level";// 解锁的最大关数
	private static int gameMode = GAME_NORMAL;
	private static boolean soundOn = true;
	private static boolean dontRushMe = false;

	private boolean fullscreen = true;

	private GameThread mGameThread;
	private ArcadeGameView mGameView;

	private static final String EDITORACTION = "com.likeapp.game.bubbleshooter.GAME";
	private boolean activityCustomStarted = false;
	private AdView adView;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_NEW_GAME, 0, R.string.menu_new_game);
		// menu.add(0, MENU_COLORBLIND_MODE_ON, 0,
		// R.string.menu_colorblind_mode_on);
		// menu.add(0, MENU_COLORBLIND_MODE_OFF, 0,
		// R.string.menu_colorblind_mode_off);
		// menu.add(0, MENU_FULLSCREEN_ON, 0, R.string.menu_fullscreen_on);
		// menu.add(0, MENU_FULLSCREEN_OFF, 0, R.string.menu_fullscreen_off);
		menu.add(0, MENU_SOUND_ON, 0, R.string.menu_sound_on);
		menu.add(0, MENU_SOUND_OFF, 0, R.string.menu_sound_off);
		// menu.add(0, MENU_DONT_RUSH_ME, 0, R.string.menu_dont_rush_me);
		// menu.add(0, MENU_RUSH_ME, 0, R.string.menu_rush_me);
		// menu.add(0, MENU_ABOUT, 0, R.string.menu_about);

		// menu.add(0, MENU_EDITOR, 0, R.string.menu_editor);
		return true;
	}

	private void fullScreen(boolean enable) {

		/*
		 * if(enable){ // go full screen WindowManager.LayoutParams attrs =
		 * getWindow().getAttributes(); attrs.flags |=
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN;
		 * getWindow().setAttributes(attrs);
		 * getWindow().addFlags(WindowManager.LayoutParams
		 * .FLAG_LAYOUT_NO_LIMITS); //hideStatusBar=true; }else{ // go non-full
		 * screen WindowManager.LayoutParams attrs =
		 * getWindow().getAttributes(); attrs.flags &=
		 * (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 * getWindow().setAttributes(attrs);
		 * getWindow().clearFlags(WindowManager
		 * .LayoutParams.FLAG_LAYOUT_NO_LIMITS); //hideStatusBar=false; }
		 */
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub

		super.onWindowFocusChanged(hasFocus);
		fullScreen(true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(MENU_SOUND_ON).setVisible(!getSoundOn());
		menu.findItem(MENU_SOUND_OFF).setVisible(getSoundOn());

		// 中文用户才可 使用积分墙功能

		// Calendar c1 = Calendar.getInstance();
		// c1.set(2011, Calendar.SEPTEMBER, 23);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(sdf.format(c1.getTime()));
		// Calendar now = Calendar.getInstance();
		// System.out.println(sdf.format(now.getTime()));
		// System.out.println(now.after(c1));
		

		// menu.findItem(MENU_COLORBLIND_MODE_ON).setVisible(getMode() ==
		// GAME_NORMAL);
		// menu.findItem(MENU_COLORBLIND_MODE_OFF).setVisible(getMode() !=
		// GAME_NORMAL);
		// menu.findItem(MENU_FULLSCREEN_ON).setVisible(!fullscreen);
		// menu.findItem(MENU_FULLSCREEN_OFF).setVisible(fullscreen);
		// menu.findItem(MENU_DONT_RUSH_ME).setVisible(!getDontRushMe());
		// menu.findItem(MENU_RUSH_ME).setVisible(getDontRushMe());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_NEW_GAME:
			handleNewGame();
			return true;
		case MENU_COLORBLIND_MODE_ON:
			setMode(GAME_COLORBLIND);
			return true;
		case MENU_COLORBLIND_MODE_OFF:
			setMode(GAME_NORMAL);
			return true;
		case MENU_FULLSCREEN_ON:
			fullscreen = true;
			setFullscreen();
			return true;
		case MENU_FULLSCREEN_OFF:
			fullscreen = false;
			setFullscreen();
			return true;
		case MENU_SOUND_ON:
			setSoundOn(true);
			return true;
		case MENU_SOUND_OFF:
			setSoundOn(false);
			return true;
		case MENU_ABOUT:
			mGameView.getThread().setState(GameView.GameThread.STATE_ABOUT);
			return true;
		case MENU_DONT_RUSH_ME:
			setDontRushMe(true);
			return true;
		case MENU_RUSH_ME:
			setDontRushMe(false);
			return true;
		case MENU_EDITOR:
			return true;
			
		}
		return false;
	}

	private void handleNewGame() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.new_game_dialog_title)
				.setMessage(R.string.new_game_dialog_content)
				.setPositiveButton(R.string.new_game_dialog_yes,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mGameThread.newGame();
							}
						})
				.setNegativeButton(R.string.new_game_dialog_no,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).show();
	}

	private void setFullscreen() {
		mGameView.requestLayout();
	}

	public synchronized static void setMode(int newMode) {
		gameMode = newMode;
	}

	public synchronized static int getMode() {
		return gameMode;
	}

	public synchronized static boolean getSoundOn() {
		return GameConfig.getInstance().isSoundOn();
	}

	public synchronized static void setSoundOn(boolean so) {
		GameConfig.getInstance().setSoundOn(so);
	}

	public synchronized static boolean getDontRushMe() {
		return dontRushMe;
	}

	public synchronized static void setDontRushMe(boolean dont) {
		dontRushMe = dont;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Allow editor functionalities.
		Intent i = getIntent();
		if (null == i || null == i.getExtras()
				|| !i.getExtras().containsKey("levels")) {
			// Default intent.
			activityCustomStarted = false;
			setContentView(R.layout.arcade_main);
			mGameView = (ArcadeGameView) findViewById(R.id.game);

			// 广告

			// final LinearLayout adLayout =
			// (LinearLayout)findViewById(R.id.adLayout);
			MgAdUtils.initAdsmogo(this, null,	"f48978ae26e2426e8e66d29e8ea6ed4e", false);
			// AdUtils.initAd(this, adLayout);
		} else {
			// Get custom level last played.
			SharedPreferences sp = getSharedPreferences(
					BubbleArcadeActivity.PREFS_NAME, Context.MODE_PRIVATE);
			int startingLevel = sp.getInt("levelCustom", 0);
			int startingLevelIntent = i.getIntExtra("startingLevel", -2);
			startingLevel = (startingLevelIntent == -2) ? startingLevel
					: startingLevelIntent;
			activityCustomStarted = true;
			mGameView = new ArcadeGameView(this, i.getExtras().getByteArray(
					"levels"), startingLevel);
			setContentView(mGameView);
		}

		mGameThread = mGameView.getThread();

		if (savedInstanceState != null) {
			mGameThread.restoreState(savedInstanceState);
		}
		mGameView.requestFocus();
		setFullscreen();

		GameConfig.getInstance().init(this);

		GameConfig.getInstance().setGameMode(GameMode.ArcadeMode);

		ScoreManager.getInstance().init(this);

		// AdManager.setPublisherId("a14d68d95e74143");
	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		// Log.i("frozen-bubble", "FrozenBubble.onPause()");
		super.onPause();
		mGameView.getThread().pause();
		// Allow editor functionalities.
		Intent i = getIntent();
		// If I didn't run game from editor, save last played level.
		if (null == i || !activityCustomStarted) {
			SharedPreferences sp = getSharedPreferences(PREFS_NAME,
					Context.MODE_PRIVATE);
			int unLockLevel = sp.getInt(
					BubbleArcadeActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);
			int level = mGameThread.getCurrentLevelIndex();
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt(BubbleArcadeActivity.PREFS_LEVEL_KEY_NAME,
					mGameThread.getCurrentLevelIndex());
			if (level > unLockLevel) {
				unLockLevel = level;
				editor.putInt(BubbleArcadeActivity.PREFS_UNLOCK_LEVEL_KEY_NAME,
						unLockLevel);
			}
			editor.commit();
		} else {
			// Editor's intent is running.
			SharedPreferences sp = getSharedPreferences(PREFS_NAME,
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("levelCustom", mGameThread.getCurrentLevelIndex());
			editor.commit();
		}
	}

	@Override
	protected void onStop() {
		// Log.i("frozen-bubble", "FrozenBubble.onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// Log.i("frozen-bubble", "FrozenBubble.onDestroy()");
		if (adView != null) {
			adView.stopLoading();
			adView.destroyDrawingCache();
		}
		super.onDestroy();
		if (mGameView != null) {
			mGameView.cleanUp();
		}
		mGameView = null;
		mGameThread = null;

		// 保存分数
		ScoreManager.getInstance().save();

		// 广告清理
		AdMogoManager.clear();
	}

	/**
	 * Notification that something is about to happen, to give the Activity a
	 * chance to save state.
	 * 
	 * @param outState
	 *            a Bundle into which this Activity should save its state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Log.i("frozen-bubble", "FrozenBubble.onSaveInstanceState()");
		// Just have the View's thread save its state into our Bundle.
		super.onSaveInstanceState(outState);
		mGameThread.saveState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		if (null != intent && EDITORACTION.equals(intent.getAction())) {
			if (!activityCustomStarted) {
				activityCustomStarted = true;

				// Get custom level last played.
				SharedPreferences sp = getSharedPreferences(
						BubbleArcadeActivity.PREFS_NAME, Context.MODE_PRIVATE);
				int startingLevel = sp.getInt("levelCustom", 0);
				int startingLevelIntent = intent.getIntExtra("startingLevel",
						-2);
				startingLevel = (startingLevelIntent == -2) ? startingLevel
						: startingLevelIntent;

				mGameView = null;
				mGameView = new ArcadeGameView(this, intent.getExtras()
						.getByteArray("levels"), startingLevel);
				setContentView(mGameView);
				mGameThread = mGameView.getThread();
				mGameThread.newGame();
				mGameView.requestFocus();
				setFullscreen();
			}
		}
	}

	
}
