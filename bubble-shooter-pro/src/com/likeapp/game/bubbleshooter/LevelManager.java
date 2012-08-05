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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LevelManager {
	private int currentLevel;
	public static final int MAX_LEVEL_NUM = 640;
	private Map<String, byte[][]> lvlsMap = new HashMap<String, byte[][]>();
	private int page;
	private final int PAGE_SIZE_OF_LEVEL = 10;
	public final String LINE_SEPARATOR2 = System.getProperty ("line.separator")+System.getProperty ("line.separator");
	
	private Context mContext;
	private int maxFixedBubbleCount =0;
	/**
	 * @return
	 */
	public int getMaxFixedBubbleCount(){
		if(maxFixedBubbleCount==0){
			if(currentLevel+1>900){
				maxFixedBubbleCount= 5;
			}else if(currentLevel+1>800){
				maxFixedBubbleCount= 6;
			}else if(currentLevel+1>640){
				maxFixedBubbleCount= 7;
			}else{
				maxFixedBubbleCount = 8;
			}
		}
		return maxFixedBubbleCount;
	}
	public void saveState(Bundle map) {
		map.putInt("LevelManager-currentLevel", currentLevel);
	}

	public void restoreState(Bundle map) {
		currentLevel = map.getInt("LevelManager-currentLevel");
	}

	private String getLevelFileName(int startingLevel) {
		page = (startingLevel + 1) / PAGE_SIZE_OF_LEVEL + 1;
		if ((startingLevel + 1) % PAGE_SIZE_OF_LEVEL == 0) {
			page--;
		}
		return "lvl2/" + page + ".txt";
	}

	public LevelManager(Context mContext, int startingLevel) {
		currentLevel = startingLevel;
		this.mContext = mContext;
		load();
	}
	private void load(){
		lvlsMap.clear();
		try {
			InputStream is = mContext.getAssets().open(
					getLevelFileName(currentLevel));// "levels.txt");
			int size = is.available();
			byte[] levels = new byte[size];
			is.read(levels);
			is.close();
			parse(levels);
		} catch (IOException e) {
			// Should never happen.
			throw new RuntimeException(e);
		}
		
	}
	private void  parse(byte[] levels) {
		String allLevels = new String(levels);
		
		allLevels = allLevels.replaceAll("\r", "");
		int nextLevel = allLevels.indexOf(LINE_SEPARATOR2);//("\n\n");
		if (nextLevel == -1 && allLevels.trim().length() != 0) {
			nextLevel = allLevels.length();
		}
		int startNum = 0;
		while (nextLevel != -1) {
			String currentLevel = allLevels.substring(0, nextLevel).trim();
			String key = ""+((this.page - 1)*this.PAGE_SIZE_OF_LEVEL  + startNum);
			lvlsMap.put(key, getLevel(currentLevel));
			//levelList.addElement(getLevel(currentLevel));

			allLevels = allLevels.substring(nextLevel).trim();

			if (allLevels.length() == 0) {
				nextLevel = -1;
			} else {
				nextLevel = allLevels.indexOf(LINE_SEPARATOR2);//("\n\n");

				if (nextLevel == -1) {
					nextLevel = allLevels.length();
				}
			}
			startNum++;
		}

		if (currentLevel >= MAX_LEVEL_NUM) {
			currentLevel = 0;
		}
	}

	private byte[][] getLevel(String data) {
		byte[][] temp = new byte[8][12];

		for (int j = 0; j < 12; j++) {
			for (int i = 0; i < 8; i++) {
				temp[i][j] = -1;
			}
		}

		int tempX = 0;
		int tempY = 0;

		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) >= 48 && data.charAt(i) <= 55) {
				temp[tempX][tempY] = (byte) (data.charAt(i) - 48);
				tempX++;
			} else if (data.charAt(i) == 45) {
				temp[tempX][tempY] = -1;
				tempX++;
			}

			if (tempX == 8) {
				tempY++;

				if (tempY == 12) {
					return temp;
				}

				tempX = tempY % 2;
			}
		}

		return temp;
	}

	public byte[][] getCurrentLevel() {
		if(lvlsMap==null || !lvlsMap.containsKey(this.currentLevel+"")){
			load();
		}
		if (currentLevel < MAX_LEVEL_NUM) {
			return lvlsMap.get(String.valueOf(currentLevel));
		}

		return null;
	}

	public void goToNextLevel() {
		SharedPreferences sp =this.mContext.getSharedPreferences(
	               BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);
		currentLevel = sp.getInt(BubbleShooterActivity.PREFS_LEVEL_KEY_NAME, 0);
		int maxLevel = sp.getInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);		
		currentLevel++;
		if(maxLevel<=currentLevel){
			maxLevel=currentLevel;
		}
		sp.edit().putInt(BubbleShooterActivity.PREFS_LEVEL_KEY_NAME, currentLevel).putInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, maxLevel).commit();
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
}
