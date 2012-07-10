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

package com.likeapp.game.bubbleshooter.arcade;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.likeapp.game.bubbleshooter.BubbleArcadeActivity;

public class ArcadeLevelManager {
	private int currentLevel;
	public static final int MAX_LEVEL_NUM = 100;
	public static final int DEFAULT_MAX_ROW = 12;
	public static final int DEFAULT_MAX_COL = 8;

	// 关卡与关卡数据
	private Map<Integer, ArcadeLevel> levelMap = new HashMap<Integer, ArcadeLevel>();

	private Context mContext;

	public void saveState(Bundle map) {
		map.putInt("LevelManager-currentLevel", currentLevel);
	}

	public void restoreState(Bundle map) {
		currentLevel = map.getInt("LevelManager-currentLevel");
	}

	public ArcadeLevelManager(Context mContext, int startingLevel) {
		currentLevel = startingLevel;
		this.mContext = mContext;
		load();
	}

	private void load() {
		levelMap.clear();
		try {
			InputStream is = mContext.getAssets().open(
					"levels_arcade/levels.txt");
			// InputStream is = mContext.getAssets().open(
			// getLevelFileName(currentLevel));// "levels.txt");
			byte[] levels = new byte[is.available()];
			is.read(levels);
			is.close();
			parse(levels);
		} catch (IOException e) {
			// Should never happen.
			throw new RuntimeException(e);
		}

	}

	private void parse(byte[] levels) {
		String allLevels = new String(levels);
		String levelArray[] = allLevels.split("\n");
		for (int index = 0; index < levelArray.length; index++) {
			
//			Log.d("sloan", "Level " + index + " Length:" + levelArray[index].length());
//			Log.d("sloan", "Level Height:" + levelArray[index].length() / DEFAULT_MAX_COL);
			
			
			String levelEntry[] = levelArray[index].split(" ");
			levelMap.put(index, new ArcadeLevel(levelArray[index].length() / DEFAULT_MAX_COL, levelEntry));
		}

		if (currentLevel >= MAX_LEVEL_NUM) {
			currentLevel = 0;
		}
	}

	public byte[][] getCurrentLevel() {
		if (levelMap == null || !levelMap.containsKey(this.currentLevel)) {
			// 加载
			load();
		}
		if (currentLevel < MAX_LEVEL_NUM) {
			return levelMap.get(currentLevel).getOriginLevel();
		}

		return null;
	}

	public void goToNextLevel() {
		SharedPreferences sp = this.mContext.getSharedPreferences(
				BubbleArcadeActivity.PREFS_NAME, Context.MODE_PRIVATE);
		currentLevel = sp.getInt(BubbleArcadeActivity.PREFS_LEVEL_KEY_NAME, 0);
		int maxLevel = sp.getInt(
				BubbleArcadeActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);
		currentLevel++;
		if (maxLevel <= currentLevel) {
			maxLevel = currentLevel;
		}
		sp.edit()
				.putInt(BubbleArcadeActivity.PREFS_LEVEL_KEY_NAME, currentLevel)
				.putInt(BubbleArcadeActivity.PREFS_UNLOCK_LEVEL_KEY_NAME,
						maxLevel).commit();
		if (currentLevel >= MAX_LEVEL_NUM) {
			currentLevel = 0;
		}
	}

	public void goToFirstLevel() {
		currentLevel = 0;
	}

	public int getLevelIndex() {
		return currentLevel;
	}

	public int getLevelRows() {
		return levelMap.get(currentLevel).getHeight();
	}

	public float getCurrentSpeed() {
		return levelMap.get(currentLevel).getSpeed();
	}

	public int getWidth() {
		return levelMap.get(currentLevel).getWidth();
	}

	class ArcadeLevel {
		private byte[][] level;
		private int mHeight = DEFAULT_MAX_ROW;
		private int mWidth = DEFAULT_MAX_COL;
		private float mSpeed = 0.4F;

		public ArcadeLevel(int height, String[] entry) {
			mSpeed = Float.valueOf(entry[0]);
//			Log.d("sloan", "Speed: "+mSpeed);
			mHeight = height;
			initLevel(entry[1]);
		}

		public int getHeight() {
			return this.mHeight;
		}

		public byte[][] getOriginLevel() {
			return this.level;
		}

		public float getSpeed() {
			return this.mSpeed;
		}

		public int getWidth() {
			return this.mWidth;
		}
		
		private void initLevel(String data) {
			level = new byte[mWidth][mHeight];

			for (int j = 0; j < mHeight; j++) {
				for (int i = 0; i < mWidth; i++) {
					level[i][j] = -1;
				}
			}

			int tempX = 0;
			int tempY = 0;

			for (int i = 0; i < data.length(); i++) {
				if (data.charAt(i) >= 48 && data.charAt(i) <= 55) {
					level[tempX][tempY] = (byte) (data.charAt(i) - 48);
					tempX++;
				} else if (data.charAt(i) == 45) {
					level[tempX][tempY] = -1;
					tempX++;
				}

				if (tempX == 8) {
					tempY++;
					if(tempY == mHeight){
						break;
					}
					tempX = tempY % 2;
				}
			}
		}
	}

}
