package com.likeapp.game.bubbleshooter.arcade;

/**
 * @author sloanwu
 * 2012-5-15 下午3:52:07
 *
 * 分数统计
 */
public class ArcadeStatistics {

	public static int countJumpScore(int count){
		int score = 0;
		
		score = count * 10 + (count - 3) * 5;
		
		return score;
	}
	
	public static int countFallScore(int count){
		int score = 0;
		
		score = count * 5;
		
		return score;
	}
}
