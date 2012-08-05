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



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.likeapp.game.utils.ViewUtils;

/**
 * @author jackyli
 * 
 */
public class LevelSelectorActivity extends Activity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
	//private static final int REQUESTCODE_LAUNCH_LEVEL = 1337;
	private static final int NUM_COLUMNS = 3;
	private LevelAdapter mLevelAdapter;
	private GridView localGridView;
	private Vibrator localVibrator;
	private Activity mContext;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		boolean bool = requestWindowFeature(Window.FEATURE_NO_TITLE );
		
		setContentView(R.layout.levelselector);
		//AdUtils.initAd(this, null);
		localVibrator = (Vibrator)getSystemService("vibrator");
		localGridView = (GridView)findViewById(R.id.grid_levelselector);
		
	    localGridView.setOnItemClickListener(this);	   
	    localGridView.setOnItemSelectedListener(this);
	   
	    this.mLevelAdapter = new LevelAdapter(this, R.layout.levelselector_level, R.id.tv_levelselector_level);
	   
	    localGridView.setAdapter(this.mLevelAdapter);
	    //setFeatureDrawableResource(Window.FEATURE_LEFT_ICON , 17301659);
	    
	    sp = this.getSharedPreferences(BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);	
	}
	/**
	 * @author jackyli
	 *
	 */
	class LevelAdapter extends ArrayAdapter<LevelSelectorActivity.LevelInfo> {
		private final SparseArray<LevelSelectorActivity.LevelInfo> mLevelInfos;
		private Activity  mContext;
		public LevelAdapter(Activity pContext, int resource, int textViewResourceId) {			
			super(pContext,resource, textViewResourceId);
			mContext = pContext;
			this.mLevelInfos =  new SparseArray<LevelSelectorActivity.LevelInfo>();
			 if(sp==null){
				 sp = LevelSelectorActivity.this.getSharedPreferences(BubbleShooterActivity.PREFS_NAME, Context.MODE_PRIVATE);	
			 }
			int maxLevel = sp.getInt(BubbleShooterActivity.PREFS_UNLOCK_LEVEL_KEY_NAME, 0);//			
			TelephonyManager tm = (TelephonyManager)LevelSelectorActivity.this.getSystemService(Context.TELEPHONY_SERVICE);  
			String imei = tm.getDeviceId();   
			
			for(int i=1;i<=LevelManager.MAX_LEVEL_NUM;i++){
				 //levels[i-1] = mContext.getResources().getString(R.string.prefix_level_str)+i;
				 mLevelInfos.put(i, new LevelSelectorActivity.LevelInfo(i,i<=maxLevel+1));
			 } 
		}

		private View populateView(View paramView, int paramInt) {
			LevelSelectorActivity.LevelInfo localLevelInfo = getItem(paramInt);
			TextView localTextView = (TextView) paramView.findViewById(R.id.tv_levelselector_level);
			localTextView.setBackgroundResource(R.drawable.levelselector_level);
			String str = String.valueOf(localLevelInfo.getLevel());
			localTextView.setText(str);
			if (localLevelInfo.isUnlocked()){
				localTextView.setEnabled(true);
				localTextView.setBackgroundResource(R.drawable.unlocked);
			}else{				
				localTextView.setEnabled(false);
				localTextView.setBackgroundResource(R.drawable.locked);
				localTextView.setText("");
			}
			return paramView;
		}

		public int getCount() {
			return mLevelInfos.size();
		}


		public LevelSelectorActivity.LevelInfo getItem(int paramInt) {
			int i = paramInt + 1;
			LevelSelectorActivity.LevelInfo localLevelInfo1 =this.mLevelInfos.get(i);
			return localLevelInfo1;			
		}

		public View getView(int position, View convertView,
				ViewGroup parent) {
		
			View localView = super.getView(position, convertView, parent);
			return populateView(localView, position);
		}

		public void notifyDataSetChanged() {
			this.mLevelInfos.clear();
			super.notifyDataSetChanged();
		}
	}

	public class LevelInfo {
		private int mLevel;
		private boolean mUnlocked;

		public LevelInfo(int level, boolean bool) {
			this.mLevel = level;
			this.mUnlocked = bool;
		}

		public int getLevel() {
			return this.mLevel;
		}

		public boolean isUnlocked() {
			return this.mUnlocked;
		}
	}
	private void showLevelNotYetUnlockedToast()
	  {
	    View localView = ViewUtils.inflate(this, R.layout.levelselector_toast_level_locked);
	    Toast localToast = new Toast(this);
	    localToast.setGravity(16, 0, 0);
	    localToast.setDuration(0);
	    localToast.setView(localView);
	    localToast.show();
	  }
	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
		// TODO Auto-generated method stub
		paramView.setSelected(true);
	    //LevelSelectorActivity.access$0(this.this$0, paramView);
	    final LevelSelectorActivity.LevelInfo localLevelInfo = mLevelAdapter.getItem(paramInt);
	   
	    if (localLevelInfo.isUnlocked())
	    {
	    	Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.levelselector_level_sel);
		    paramView.startAnimation(animation);
	       
	        	
    		Intent i = null;
        	i = new Intent(LevelSelectorActivity.this, BubbleShooterActivity.class);        	
        	
        	sp.edit().putInt(BubbleShooterActivity.PREFS_LEVEL_KEY_NAME,localLevelInfo.mLevel-1).commit();
        	startActivity(i);
        	LevelSelectorActivity.this.finish();
	        		
	        	
	        paramView.startAnimation(animation);
	    }else{
	      localVibrator.vibrate(100L);
	      Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.levelselector_level_locked);
	      paramView.startAnimation(animation);
	      showLevelNotYetUnlockedToast();
	    }
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
}
