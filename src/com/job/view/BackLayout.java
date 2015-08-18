package com.job.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.job.R;

public class BackLayout extends RelativeLayout {

	public BackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.back, this);
		ImageView img = (ImageView) findViewById(R.id.back);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				((Activity)getContext()).finish();
				
			}
		});
	}

}
