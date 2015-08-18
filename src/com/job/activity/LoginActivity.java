package com.job.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.R;
import com.job.adapter.LoginPagerAdapter;
import com.job.util.ImageUtil;


public class LoginActivity extends Activity implements OnClickListener{

	private ViewPager viewPager;
	private ImageView imageView;
	
	private TextView textView1,textView2;
	private List<View> views;
	private TextView forget_pass1,forget_pass2;
	private Button login_btn1,login_btn2,register_btn1,register_btn2;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1,view2;//各个页卡
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		initImageView();
		initTextView();
		
		initViewPager();
		init();
	}

	private void init() {
		forget_pass1 = (TextView) view1.findViewById(R.id.tv_forget_password1);
		forget_pass2 = (TextView) view2.findViewById(R.id.tv_forget_password2);
		login_btn1 = (Button) view1.findViewById(R.id.login1);
		login_btn2 = (Button) view2.findViewById(R.id.login2);
		register_btn1 = (Button) view1.findViewById(R.id.register1);
		register_btn2 = (Button) view2.findViewById(R.id.register2);
		login_btn1.setOnClickListener(this);
		login_btn2.setOnClickListener(this);
		register_btn1.setOnClickListener(this);
		register_btn2.setOnClickListener(this);
		forget_pass1.setOnClickListener(this);
		forget_pass2.setOnClickListener(this);
	}

	private void initImageView() {
		
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
		
	}

	private void initViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.person_login, null);
		view2=inflater.inflate(R.layout.company_login, null);
		
		views.add(view1);
		views.add(view2);
		
		viewPager.setAdapter(new LoginPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}

	private void initTextView() {
		
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);	
		
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		
		
	}

	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
        	index=i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);			
		}
		
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 4 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int arg0) {
			
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			
		}
    	
    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.login1:
			loginPerson();
			break;
		case R.id.login2:
			loginCompany();
			break;
		case R.id.register1:
			registerPerson();
			break;
		case R.id.register2:
			registerCompany();
			break;
		case R.id.tv_forget_password1:
			forget_person();
			break;
		case R.id.tv_forget_password2:
			forget_company();
			break;
		default:
			break;
		}
		
	}

	private void forget_company() {
				
	}

	private void forget_person() {
		
		
	}

	private void registerCompany() {
		
		Intent intent = new Intent(this, RegisterPhoneActivity.class);
		intent.putExtra("register_type", "company");
		startActivity(intent);
		
	}

	private void registerPerson() {
		
		Intent intent = new Intent(this, RegisterPhoneActivity.class);  
		intent.putExtra("register_type", "person");
		
		startActivity(intent);
	}

	private void loginCompany() {
		
		
		
	}

	private void loginPerson() {
	
		if(loginCheckPerson()){
			Intent intent = new Intent(this,PersonActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
		
	}

	//验证用户登录是否正确的方法
	private boolean loginCheckPerson() {
		
		return false;
	}
	
	

}
