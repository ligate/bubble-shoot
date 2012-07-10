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

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: sloanwu 2012-5-15 下午9:43:36
 * 
 */

public class GameConfig {
	public static final String PREF_ROOT = "com.betterapp.bubble.config";
	public static final String PREF_GAME_MODE = "game_mode";
	public static final String PREF_SOUND = "sound";

	public static GameConfig instance;

	private SharedPreferences sp;

	public GameConfig(){}
	
	public static GameConfig getInstance() {
		if (null == instance) {
			instance = new GameConfig();
		}
		return instance;
	}

	public void init(Context context) {
		sp = context.getSharedPreferences(PREF_ROOT, Context.MODE_PRIVATE);
	}

	public GameMode getGameMode(){
		String mode = sp.getString(PREF_GAME_MODE, GameMode.PuzzleMode.name());
		return GameMode.valueOf(mode);
	}
	
	public void setGameMode(GameMode mode){
		put(PREF_GAME_MODE, mode.name());
	}
	
	public boolean isSoundOn(){
		return get(PREF_SOUND, true);
	}
	
	public void setSoundOn(boolean val){
		put(PREF_SOUND, val);
	}
	
	public int get(String key, int defaultVal) {
		return sp.getInt(key, defaultVal);
	}

	public void put(String key, int val) {
		sp.edit().putInt(key, val).commit();
	}

	public long get(String key, long defaultVal) {
		return sp.getLong(key, defaultVal);
	}
	
	public void put(String key, long val) {
		sp.edit().putLong(key, val).commit();
	}
	
	public boolean get(String key, boolean defaultVal) {
		return sp.getBoolean(key, defaultVal);
	}
	
	public void put(String key, boolean val) {
		sp.edit().putBoolean(key, val).commit();
	}
	
	public void put(String key, String val) {
		sp.edit().putString(key, val).commit();
	}
	
	public enum GameMode{
		PuzzleMode, ArcadeMode
	}
}
