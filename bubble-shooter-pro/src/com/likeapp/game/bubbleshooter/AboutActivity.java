package com.likeapp.game.bubbleshooter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

public class AboutActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(getApplicationContext());
		
		StringBuilder buf = new StringBuilder("original frozen bubble:\n");
		buf.append("guillaume cottenceau\n");
		buf.append("alexis younes\n");
		buf.append("amaury amblard-ladurantie\n");
		buf.append("matthias le bidan\n");
		
		buf.append("\n");
		buf.append("java version:");
		buf.append("glenn sanson\n");
		
		buf.append("\n");
		buf.append("android port:");
		buf.append("aleksander fedorynski\n");
		
		buf.append("\n");
		buf.append("android port source code is available at:http://code.google.com/p/frozenbubbleandroid\n");
		
		buf.append("\n");
		buf.append("bubble shooter pro source code is available at:http://code.google.com/p/bubble-shoot\n");

		textView.setText(buf.toString());
		textView.setGravity(Gravity.LEFT);
		textView.setPadding(10, 20, 10, 20);
		textView.setTextSize(16);
		
		addContentView(textView,   new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	}
}
