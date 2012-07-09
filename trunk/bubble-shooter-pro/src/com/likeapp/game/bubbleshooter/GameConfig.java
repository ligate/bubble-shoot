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
