package com.likeapp.game.bubbleshooter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.likeapp.game.bubbleshooter.arcade.ScoreManager;
import com.likeapp.game.utils.ScreenUtil;



public class RankPopupWindowManager{
	private PopupWindow mPopupWindow;
	// 弹出窗口相关
	private TextView mButtonScoreRank;
	private TextView mButtonLevelRank;
	private TextView mButtonHonor;
	
	static Activity act = null;
	public RankPopupWindowManager(Activity act){
		RankPopupWindowManager.act= act;
	}
	public void setupPopup(Context mContext) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.rank_popup_menu, null);
		mPopupWindow = new PopupWindow(view);
		mPopupWindow.setAnimationStyle(R.style.RankPopupAnimation);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setWidth(ScreenUtil.dip2px(mContext, 320));
		mPopupWindow.setHeight(ScreenUtil.dip2px(mContext, 250));

		mButtonScoreRank = (TextView) view.findViewById(R.id.scorerank);
		mButtonLevelRank = (TextView) view.findViewById(R.id.levelrank);
		mButtonHonor = (TextView)view.findViewById(R.id.honor);
		
		mButtonScoreRank.setOnClickListener(onPopupItemClickListener);
		mButtonLevelRank.setOnClickListener(onPopupItemClickListener);
		mButtonHonor.setOnClickListener(onPopupItemClickListener);
		
		
	}

	public void showPopup(View v) {
		if(mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}else{
			mPopupWindow.showAtLocation(v, 0, 0, 0);
		}
	}
	
	public boolean isRankWindowShowing() {
		if (mPopupWindow != null) {
			return mPopupWindow.isShowing();
		} else {
			return false;
		}
	}

	public void dissmissRankWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	private OnClickListener onPopupItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mPopupWindow.dismiss();

			if (v == mButtonScoreRank) {
				ScoreManager.getInstance().submitArcadeThenOpenLeadboardActivity(act);
			} else if (v == mButtonLevelRank) {
				ScoreManager.getInstance().submitPuzzleThenOpenLeadboardActivity(act);
			}else if(v == mButtonHonor){
				ScoreManager.getInstance().checkHonor(act);
				ScoreManager.getInstance().openHonor(act);
			}
		}
	};
}
