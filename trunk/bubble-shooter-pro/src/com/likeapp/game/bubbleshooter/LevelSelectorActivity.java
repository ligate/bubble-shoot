/**
 * 
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
	 * 关卡适配器
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
			
			if(//"354635031971620".equals(imei)||
				"354795046744154".equals(imei)
				||"354957031875589".equals(imei)
				||"356723040690999".equals(imei)
				||"57d286ceeedc4b2845f1c8d7ebe36519".equals(imei)
				||"000000000000000".equals(imei)){
				maxLevel = LevelManager.MAX_LEVEL_NUM;
			}
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
	       
	        	
    		//进入关卡游戏
    		Intent i = null;
        	//设置当前关卡                	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
