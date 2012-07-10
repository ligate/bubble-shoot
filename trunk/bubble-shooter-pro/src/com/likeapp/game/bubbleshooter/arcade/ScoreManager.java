package com.likeapp.game.bubbleshooter.arcade;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.likeapp.game.bubbleshooter.BubbleShooterActivity;
import com.likeapp.game.bubbleshooter.GameConfig;
import com.likeapp.game.bubbleshooter.LevelManager;
import com.likeapp.game.bubbleshooter.R;
import com.likeapp.game.utils.GameCenterUtils;

/**
 * @author sloanwu 2012-5-15 下午3:17:48
 * 
 */
public class ScoreManager {
	/*public static final String OPEN_FEINT_NAME= SDKFlag.FLAG.the9.equals(SDKFlag.flag)?"泡泡龙":"Bubble Shooter";//中文采用九城 :英文官方原版
	public static final String OPEN_FEINT_KEY = SDKFlag.FLAG.the9.equals(SDKFlag.flag)?"og9c60v6XolfJFigRAQ":"J08TtcwhH2gzHLTxBlJIg";
	public static final String OPEN_FEINT_SECRET = SDKFlag.FLAG.the9.equals(SDKFlag.flag)?"uzQKicmIkB2eVZg686CYUcKmokMTtXpy":"67aKNRrtEyBdy1CNrBzWZCaiNYpe42eniMHEvfRM";
	public static final String OPEN_FEINT_ID =SDKFlag.FLAG.the9.equals(SDKFlag.flag)?"1060314222":"499813";
	
	public static final String OPEN_FEINT_LEADERBOARD_ID = SDKFlag.FLAG.the9.equals(SDKFlag.flag)?"916960872":"1169347";*/

	public static final String PREF_SCORE = "score";
	public static final String PREF_HIGH_SCORE = "high_score";

	public static final String ScoreLoop_Secret="Dwi3/uKfsQWRu9447u1HDpmNoGXUb3yk7JPrimWhHqy6lrZOGl1jcQ==";
	public static final Integer[] ScoreLoop_Mode_Arcade = new Integer[]{0,R.string.arcade_mode_name};
	public static final Integer[] ScoreLoop_Mode_Puzzle = new Integer[]{1,R.string.puzzle_mode_name};
	public static final String WIYUN_ARCADE_LEADERBORDER_ID="eef182c4241f3b34";
	public static final String WIYUN_PUZZLE_LEADERBORDER_ID = "8be9c1f0a6626d06";
	
	public static final String SWARM_ARCADE_LEADERBORDER_ID="1367";
	public static final String SWARM_PUZZLE_LEADERBORDER_ID = "1369";
	
	public static final class HONOR{
		public static final String CXZL = "7c07eac2ee4645d3";
		public static final String GreenHorn = "2357";
		public static void checkCXZL(Activity act,int maxLevel,long score){
			if(maxLevel>=10){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, CXZL);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, CXZL);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, GreenHorn);
					break;
				}
			}
		}
		/**
		 */
		public static final String DTRS = "67dbd380708a6f75";
		public static final String Scout = "2359";
		public static void checkDTRS(Activity act,int maxLevel,long score){
			if(maxLevel>=100 || score>10000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, DTRS);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, DTRS);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, Scout);
					break;
				}
			}
		}
		/**
		 */
		public static final String YZCS = "d33538c32932bb43";
		public static final String Expert = "2361";
		public static void checkYZCS(Activity act,int maxLevel,long score){
			if(maxLevel>=200 || score>20000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, YZCS);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, YZCS);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, Expert);
					break;
				}
			}
		}
		/**
		 */
		public static final String JRJJ = "2912c358ac436e6d";
		public static final String Professional = "2363";
		public static void checkJRJJ(Activity act,int maxLevel,long score){
			if(maxLevel>=300 || score>60000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, JRJJ);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, JRJJ);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, Professional);
					break;
				}
			}
		}
		/**
		 */
		public static final String LHCQ = "8bdb71a3dbdeb067";
		public static final String Champion = "2365";
		public static void checkLHCQ(Activity act,int maxLevel,long score){
			if(maxLevel>=400 || score>100000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, LHCQ);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, LHCQ);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, Champion);
					break;
				}
			}
		}
		
		/**
		 */
		public static final String ZCYP = "dba85bae0a4e33d3";
		public static final String Master = "2367";
		public static void checkZCYP(Activity act,int maxLevel,long score){
			if(maxLevel>=500 || score>180000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, ZCYP);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, ZCYP);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, Master);
					break;
				}
			}
		}
		/**
		 */
		public static final String GXYM = "390483cb766fc94d";
		public static final String GrandMaster = "2369";
		public static void checkGXYM(Activity act,int maxLevel,long score){
			if(maxLevel>=630 || score>600000){
				switch(GameCenterUtils.sdkFlag){
				case GameCenterUtils.SDK_FLAG.WIYUN:
					GameCenterUtils.unlockAchievement(act, GXYM);
					break;
				case GameCenterUtils.SDK_FLAG.SCORELOOP:
					GameCenterUtils.unlockAchievement(act, GXYM);
					break;
				case GameCenterUtils.SDK_FLAG.SWARM:
					GameCenterUtils.unlockAchievement(act, GrandMaster);
					break;
				}
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
			GameCenterUtils.init(act,"949","5c526201b8083b6c1556dd7016daaa38",null);
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
		String leaderbordName = activity.getString(ScoreManager.ScoreLoop_Mode_Arcade[1]);
		switch(GameCenterUtils.sdkFlag){
		case GameCenterUtils.SDK_FLAG.WIYUN:
			leaderborderId=ScoreManager.WIYUN_ARCADE_LEADERBORDER_ID;
			break;
		case GameCenterUtils.SDK_FLAG.SCORELOOP:
			leaderborderId = String.valueOf(ScoreManager.ScoreLoop_Mode_Arcade[0]);
			break;
		case GameCenterUtils.SDK_FLAG.SWARM:
			leaderborderId = ScoreManager.SWARM_ARCADE_LEADERBORDER_ID;
		}
		//GameCenterUtils.sumbit(activity, String.valueOf(ScoreManager.getInstance().getScore()),leaderborderId);
		GameCenterUtils.submitThenOpenLeadboardActivity(activity, String.valueOf(ScoreManager.getInstance().getScore()),leaderborderId,leaderbordName);
		
	}
	public void submitPuzzleThenOpenLeadboardActivity(Activity activity){
		initGameCenter(activity);
		
		String leaderborderId= "";
		String leaderbordName = "";
		switch(GameCenterUtils.sdkFlag){
		case GameCenterUtils.SDK_FLAG.WIYUN:
			leaderborderId=ScoreManager.WIYUN_PUZZLE_LEADERBORDER_ID;
			break;
		case GameCenterUtils.SDK_FLAG.SCORELOOP:
			leaderborderId = String.valueOf(ScoreManager.ScoreLoop_Mode_Puzzle[0]);
			leaderbordName = activity.getString(ScoreManager.ScoreLoop_Mode_Puzzle[1]);
			break;
		case GameCenterUtils.SDK_FLAG.SWARM:
			leaderborderId = ScoreManager.SWARM_PUZZLE_LEADERBORDER_ID;
		}
		SharedPreferences sp = activity.getSharedPreferences(BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);	
		int maxLevel = sp.getInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);//
		if(maxLevel>LevelManager.MAX_LEVEL_NUM){
			maxLevel = LevelManager.MAX_LEVEL_NUM;
		}
		GameCenterUtils.submitThenOpenLeadboardActivity(activity, String.valueOf(maxLevel),leaderborderId,leaderbordName);		
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
		GameCenterUtils.openAchievementActivity(activity);
	}
}
