package com.likeapp.game.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.app.Activity;
import android.util.Log;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmAchievement.GotAchievementsMapCB;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmLeaderboard;
import com.swarmconnect.SwarmLeaderboard.GotLeaderboardCB;
import com.swarmconnect.delegates.SwarmLoginListener;

/**
 * Use swarm game center, need to add swarm project
 * 
 * @author bin-note
 *
 */

public class GameCenterUtils {
	public final static int sdkFlag =  SDK_FLAG.SWARM;
	
	public static final class SDK_FLAG{
		public static final int SCORELOOP = 0 ;
		public static final int OPENFEINT_EN = 1;
		public static final int OPENFEINT_CN = 2;
		
		public static final int WIYUN = 3;
		public static final int SWARM = 4;
	}
	
	
	public final static String  NeedSubMitFirst = "needSubmitFirst";
	public final static String  ScoreStr = "scoreStr";
	public final static String  ModeStr = "modeStr";
	public final static String  ModeNameStr = "modeNameStr";
	
	
	private static Map<Integer, SwarmAchievement> achievements = null;//new HashMap<Integer, SwarmAchievement>();
	
	private static boolean hasLoggedIn = false;//是否已登录swarm
	private static  Activity act = null;
	
	private static Map<Integer,Float> scoreCache = new ConcurrentHashMap<Integer,Float>();
	private static Integer currentLeaderBoard =0;//先提交分数再展现排行榜
	private static boolean isOpenLeaderboardsAfterUserLoggedIn=false;//在登录后，回调打开排行榜列表
	private static boolean isOpenAchievementAfterUserLoggedIn = false;//在登录后，回调打开成就列表
	private static ConcurrentLinkedQueue<Integer> cacheAchievements = new ConcurrentLinkedQueue<Integer>();
	private static  SwarmLoginListener mySwarmLoginListener = new SwarmLoginListener() {

		// This method is called when the login process has started
		// (when a login dialog is displayed to the user).
		public void loginStarted() {
			Log.i("jackyli","swarm loginStarted");
		}

		// This method is called if the user cancels the login process.
		public void loginCanceled() {
			Log.i("jackyli","swarm loginCanceled");
		}

		// This method is called when the user has successfully logged in.
		public void userLoggedIn(SwarmActiveUser user) {
			Log.i("jackyli","swarm userLoggedIn,cache score = "+scoreCache.size());
			hasLoggedIn = true;
			//登录后，把登录前未提交的分数进行提交
			Set<Integer> leaderbordIds = scoreCache.keySet();
			if(leaderbordIds!=null && leaderbordIds.size()>0){
				for(final Integer leaderBoardId:leaderbordIds){
					final Float score = scoreCache.get(leaderBoardId);
					if(act != null){
						Swarm.setActive(act);
					}
					SwarmLeaderboard.getLeaderboardById(leaderBoardId, new GotLeaderboardCB() {
					    public void gotLeaderboard(SwarmLeaderboard leaderboard) {
					    	if (leaderboard != null) {
					    		leaderboard.submitScore(score);
					    		Log.i("jackyli","remove cache Score:"+leaderBoardId);
					    		scoreCache.remove(leaderBoardId);
					    		
					    		if(leaderBoardId == currentLeaderBoard){
					    			Log.i("jackyli","userLoggedIn:show leaderboard");
					    			leaderboard.showLeaderboard();
					    			currentLeaderBoard = 0;
					    		}
					        }
					    }
					});
				}
			}
			//成就检测
			if(cacheAchievements!=null && cacheAchievements.size()>0){
				for(final Integer achievementId:cacheAchievements){
					Swarm.setActive(act);		
					if(achievements!=null){
						 // Store the map of achievements somewhere to be used later.
					       // achievements;
					        SwarmAchievement achievement = achievements.get(achievementId);
					        // No need to unlock more than once...
					        if (achievement != null && achievement.unlocked == false) {
					            achievement.unlock();
					            cacheAchievements.remove(achievementId);
					        }
					}else{
						SwarmAchievement.getAchievementsMap(new GotAchievementsMapCB() {
						    public void gotMap(Map<Integer, SwarmAchievement> _achievements) {
						    	
						        // Store the map of achievements somewhere to be used later.
						    	
						    	if(_achievements!=null){
						    		achievements = _achievements;
							        SwarmAchievement achievement = achievements.get(achievementId);
							        // No need to unlock more than once...
							        if (achievement != null && achievement.unlocked == false) {
							            achievement.unlock();
							            cacheAchievements.remove(achievementId);
							        }
						    	}
						    }
						});
					}
				}
			}
			
			//在登录后，回调打开排行榜列表
			if(isOpenLeaderboardsAfterUserLoggedIn){
				Swarm.setActive(act);
				Swarm.showLeaderboards();
				isOpenLeaderboardsAfterUserLoggedIn = false;
			}
			//在登录后，回调打开成就列表
			if(isOpenAchievementAfterUserLoggedIn){
				Swarm.setActive(act);
				Swarm.showAchievements();				
				isOpenAchievementAfterUserLoggedIn = false;
			}
		}

		// This method is called when the user logs out.
		public void userLoggedOut() {
			Log.i("jackyli","swarm userLoggedOut");
			hasLoggedIn = false;
			cacheAchievements.clear();
			scoreCache.clear();
		}

	};
	/**
	 * 初始化
	 * @param activity
	 * @param appId
	 * @param secrectKey
	 * @param paramMap 允许为null
	 */
	public static void init(Activity activity,String appId,String secrectKey,Map<String,String> paramMap) {		
		Integer appId_ = Integer.parseInt(appId);
		Swarm.setActive(activity);
		act = activity;
		Swarm.init(activity, appId_, secrectKey,mySwarmLoginListener);
		
	}
	
	
	
	

	/**
	 * 提交分数
	 * 
	 * @param scoreValue
	 */
	public static void submit(final Activity act, final String scoreStr, final String _leaderBoardId) {
		final Float score = Float.parseFloat(scoreStr);
		Integer leaderBoardId = Integer.parseInt(_leaderBoardId);
		if(hasLoggedIn){
			Swarm.setActive(act);
			GameCenterUtils.act = act;
			SwarmLeaderboard.getLeaderboardById(leaderBoardId, new GotLeaderboardCB() {
			    public void gotLeaderboard(SwarmLeaderboard leaderboard) {
			    	if (leaderboard != null) {
			    		leaderboard.submitScore(score);
			        }
			    }
			});
		}else{
			Log.i("jackyli","cache score for userloggedin callback");
			//未登录，先缓存待提交分数
			scoreCache.put(leaderBoardId, score);
		}
	}

	/**
	 * 打开全球排行榜
	 */
	public static void openLeaderboardActivity(Activity act, String _leaderBoardId) {
		Swarm.setActive(act);
		Integer leaderBoardId = Integer.parseInt(_leaderBoardId);
		SwarmLeaderboard.getLeaderboardById(leaderBoardId, new GotLeaderboardCB() {
		    public void gotLeaderboard(SwarmLeaderboard leaderboard) {
		    	if (leaderboard != null) {
		    		leaderboard.showLeaderboard();
		        }
		        }
		});
	}
	public static void openLeaderboards(final Activity act ){
		if(hasLoggedIn){
			Swarm.setActive(act);
			Swarm.showLeaderboards();
		}else{
			isOpenLeaderboardsAfterUserLoggedIn = true;
		}
	}

	/**
	 * 先提交本地分数再打开全球排行榜
	 * @param act
	 * @param scoreValue
	 * @param mode			0或1或2...
	 * @param modeNameStr     模式名
	 */
	public static void submitThenOpenLeadboardActivity(final Activity act, final String scoreStr, final String _leaderBoardId, final String modeNameStr) {
		final Float score = Float.parseFloat(scoreStr);
		Integer leaderBoardId = Integer.parseInt(_leaderBoardId);
		if(hasLoggedIn){
			Swarm.setActive(act);
			GameCenterUtils.act = act;
			SwarmLeaderboard.getLeaderboardById(leaderBoardId, new GotLeaderboardCB() {
			    public void gotLeaderboard(SwarmLeaderboard leaderboard) {
			    	if (leaderboard != null) {
			    		leaderboard.submitScore(score);
			    		leaderboard.showLeaderboard();
			        }
			    }
			});
		}else{
			Log.i("jackyli","cache score for userloggedin callback");
			currentLeaderBoard = leaderBoardId;
			//未登录，先缓存待提交分数
			scoreCache.put(leaderBoardId, score);
		}
		
	}
	
	
	/**
	 * 打开成就列表
	 */
	public static void openAchievementActivity(Activity act){
		if(hasLoggedIn){
			Swarm.setActive(act);
			Swarm.showAchievements();
		}else{
			isOpenAchievementAfterUserLoggedIn = true;
		}
	}
	

	/**
	 * 解锁成就
	 */
	public static void unlockAchievement(final Activity act, final String awardIdentifier){		
		final Integer ACHIEVEMENT_ID = Integer.parseInt(awardIdentifier);
		if(hasLoggedIn){
			Swarm.setActive(act);		
			if(achievements!=null){
				 // Store the map of achievements somewhere to be used later.
			       // achievements;
			        SwarmAchievement achievement = achievements.get(ACHIEVEMENT_ID);
			        // No need to unlock more than once...
			        if (achievement != null && achievement.unlocked == false) {
			            achievement.unlock();
			        }
			}else{
				SwarmAchievement.getAchievementsMap(new GotAchievementsMapCB() {
				    public void gotMap(Map<Integer, SwarmAchievement> _achievements) {
				    	
				        // Store the map of achievements somewhere to be used later.
				    	
				    	if(_achievements!=null){
				    		achievements = _achievements;
					        SwarmAchievement achievement = achievements.get(ACHIEVEMENT_ID);
					        // No need to unlock more than once...
					        if (achievement != null && achievement.unlocked == false) {
					            achievement.unlock();
					        }
				    	}
				    }
				});
			}
		}else{
			//缓存待检测
			cacheAchievements.add(ACHIEVEMENT_ID);
		}

		
	}
	
	
	
	
}
