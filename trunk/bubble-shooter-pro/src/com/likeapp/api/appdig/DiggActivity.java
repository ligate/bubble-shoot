package com.likeapp.api.appdig;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.likeapp.game.bubbleshooter.R;



public class DiggActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreenMode();
		setContentView(R.layout.digg_main);
		initView();
	}

	public void setFullscreenMode() {
		requestWindowFeature(Window.FEATURE_PROGRESS);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	private void initView() {
		findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});

		findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoMarket();
			}
		});
		boolean isAutoClose = this.getIntent().getBooleanExtra(DiggConstant.IS_AUTO_CLOSE_KEY, true);
		if(isAutoClose) {
			mHandler.sendEmptyMessageDelayed(0, DiggConstant.ATUO_CLOSE_TIME);
		} 
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			close();
		}
	};

	private void close() {
		finish();
	}

	private void gotoMarket() {
		try {
			String uriString = "market://details?id=" + DiggConstant.DIGG_APP_PKGNAME;
			Uri uri = Uri.parse(uriString);
			Intent it = new Intent();
			it.setData(uri);
			startActivity(it);
			
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}