package com.likeapp.game.bubbleshooter.arcade;

/**
 * @author sloanwu
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
