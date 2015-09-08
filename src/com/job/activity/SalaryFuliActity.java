package com.job.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class SalaryFuliActity extends BaseActivity implements OnClickListener{

	private Button tag1, tag2, tag3, tag4, tag5, tag6;
	private TextView save;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salary_fuli);
		init();
	}

	private void init() {
		
		tag1 = (Button) findViewById(R.id.tag1);
		tag2 = (Button) findViewById(R.id.tag2);
		tag3 = (Button) findViewById(R.id.tag3);
		tag4 = (Button) findViewById(R.id.tag4);
		tag5 = (Button) findViewById(R.id.tag5);
		tag6 = (Button) findViewById(R.id.tag6);
		save = (TextView) findViewById(R.id.save);
		
		tag1.setOnClickListener(this);
		tag2.setOnClickListener(this);
		tag3.setOnClickListener(this);
		tag4.setOnClickListener(this);
		tag5.setOnClickListener(this);
		tag6.setOnClickListener(this);
		save.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tag1:
			tag1.setTextColor(Color.WHITE);
			tag1.setBackgroundColor(Color.GREEN);
			break;
		case R.id.tag2:
			tag2.setTextColor(Color.WHITE);
			tag2.setBackgroundColor(Color.GREEN);
			break;
		case R.id.tag3:
			tag3.setTextColor(Color.WHITE);
			tag3.setBackgroundColor(Color.GREEN);
			break;
		case R.id.tag4:
			tag4.setTextColor(Color.WHITE);
			tag4.setBackgroundColor(Color.GREEN);
			break;
		case R.id.tag5:
			tag5.setTextColor(Color.WHITE);
			tag5.setBackgroundColor(Color.GREEN);
			break;
		case R.id.tag6:
			tag6.setTextColor(Color.WHITE);
			tag6.setBackgroundColor(Color.GREEN);
			break;
		case R.id.save:
			save_salary();
			break;
		default:
			break;
		}
	}

	private void save_salary() {
		
	}

	
	
}
