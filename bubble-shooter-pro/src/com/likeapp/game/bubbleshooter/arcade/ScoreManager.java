package com.likeapp.game.bubbleshooter.arcade;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.likeapp.game.bubbleshooter.BubbleShooterActivity;
import com.likeapp.game.bubbleshooter.GameConfig;
import com.likeapp.game.bubbleshooter.LevelManager;
import com.likeapp.game.bubbleshooter.R;

/**
 * @author sloanwu 2012-5-15 下午3:17:48
 * 
 */
public class ScoreManager {
	
	public static final String PREF_SCORE = "score";
	public static final String PREF_HIGH_SCORE = "high_score";

	public static final class HONOR{
		public static void checkCXZL(Activity act,int maxLevel,long score){
			if(maxLevel>=10){
			}
		}
		/**
		 */
		public static void checkDTRS(Activity act,int maxLevel,long score){
			if(maxLevel>=100 || score>10000){
			}
		}
		/**
		 */
		public static void checkYZCS(Activity act,int maxLevel,long score){
			if(maxLevel>=200 || score>20000){
			}
		}
		/**
		 */
		public static void checkJRJJ(Activity act,int maxLevel,long score){
			if(maxLevel>=300 || score>60000){
			}
		}
		/**
		 */
		public static final String LHCQ = "8bdb71a3dbdeb067";
		public static final String Champion = "2365";
		public static void checkLHCQ(Activity act,int maxLevel,long score){
			if(maxLevel>=400 || score>100000){
			}
		}
		
		/**
		 */
		public static final String ZCYP = "dba85bae0a4e33d3";
		public static final String Master = "2367";
		public static void checkZCYP(Activity act,int maxLevel,long score){
			if(maxLevel>=500 || score>180000){
			}
		}
		/**
		 */
		public static final String GXYM = "390483cb766fc94d";
		public static final String GrandMaster = "2369";
		public static void checkGXYM(Activity act,int maxLevel,long score){
			if(maxLevel>=630 || score>600000){
			}
		}
	}
	private static ScoreManager instance;

	private long tempScore;
	private long score;
	private long bestScore;
	

	private Activity currentActivity;
	private static boolean isInit = false;
	private ScoreManager() {
	}

	public static ScoreManager getInstance() {
		if (null == instance) {
			instance = new ScoreManager();
		}
		return instance;
	}

	public void init(Activity activity) {
		currentActivity = activity;

		score = GameConfig.getInstance().get(PREF_SCORE, 0L);
		bestScore = GameConfig.getInstance().get(PREF_HIGH_SCORE, 0L);
		tempScore = 0;
		
		
	}
	/**
	 * @param act
	 */
	public void initGameCenter(Activity act){
		if(!isInit){
			isInit = true;
		}
	}
	public void addTempScore(long s){
		tempScore += s;
	}
	
	public void addToTotalScore(){
		addScore(tempScore);
		submitToBestScore();
	}

	public void clearTempScore(){
		tempScore = 0;
	}
	
	private void addScore(long s) {
		score += s;
	}

	public void reset() {
		clearTempScore();
		score = 0;
		save();
	}

	public long getScore() {
		return score + tempScore;
	}
	public long getBestScore(){
		return this.bestScore;
	}
	public void save() {
		GameConfig.getInstance().put(PREF_SCORE, score);
		GameConfig.getInstance().put(PREF_HIGH_SCORE, bestScore);
	}

	private void submitToBestScore(){
		if(score > bestScore){
			bestScore = score;
		}
		save();
	}
	
	
	public void submitArcadeThenOpenLeadboardActivity(Activity activity){
		initGameCenter(activity);
		
		String leaderborderId= "";
		String leaderbordName = "";
	}
	public void submitPuzzleThenOpenLeadboardActivity(Activity activity){
		initGameCenter(activity);
		
		String leaderborderId= "";
		String leaderbordName = "";
		SharedPreferences sp = activity.getSharedPreferences(BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);	
		int maxLevel = sp.getInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);//
		if(maxLevel>LevelManager.MAX_LEVEL_NUM){
			maxLevel = LevelManager.MAX_LEVEL_NUM;
		}
	}
	/**
	 * @param activity
	 */
	public void checkHonor(Activity act){
		initGameCenter(act);
		SharedPreferences sp = act.getSharedPreferences(BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);	
		int maxLevel = sp.getInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);//
		long score = this.getBestScore();
		
		HONOR.checkCXZL(act,maxLevel,score);
		HONOR.checkDTRS(act,maxLevel,score);
		HONOR.checkYZCS(act,maxLevel,score);
		HONOR.checkJRJJ(act,maxLevel,score);
		HONOR.checkLHCQ(act,maxLevel,score);
		HONOR.checkZCYP(act,maxLevel,score);
		HONOR.checkGXYM(act,maxLevel,score);
	}
	
	public void openHonor(Activity activity){
		initGameCenter(activity);
	}
}
