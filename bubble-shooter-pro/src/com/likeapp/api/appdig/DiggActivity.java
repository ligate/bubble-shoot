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
 * Bubble-Shooter-Pro:http://code.google.com/p/bubble-shoot/
 */
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

/**
 * 
 * @author bin
 *
 */

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