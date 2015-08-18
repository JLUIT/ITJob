package com.job.activity;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.job.R;
import com.job.adapter.MenuAdapter;
import com.job.bean.Menu;
import com.job.fragment.ContactFragment;
import com.job.fragment.GuideFragment;
import com.job.fragment.MessageFragment;
import com.job.fragment.SchoolFragment;
import com.job.util.ImageUtil;
import com.job.view.DragLayout;
import com.job.view.DragLayout.DragListener;
import com.nineoldandroids.view.ViewHelper;

public class PersonActivity extends Activity implements OnClickListener{

	private DragLayout dl; 
	private ListView lv;
	private ImageView iv_icon,iv_bottom;
	
	private MessageFragment messageFragment;
	private ContactFragment contactsFragment;
	private SchoolFragment schoolFragment;
	private GuideFragment guideFragment;
	private View messageLayout;
	private View contactsLayout;
	private View schoolLayout;	
	private View guideLayout;
	private ImageView messageImage;
	private ImageView contactsImage;
	private ImageView schoolImage;
	private ImageView guideImage;
	private TextView messageText;
	private TextView contactsText;
	private TextView schoolText;
	private TextView guideText;
	private FragmentManager fragmentManager;
	private List<Menu> menuList = new ArrayList<Menu>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		initDragLayout();
		initView();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	private void initView() {
	
		messageLayout = findViewById(R.id.message_layout);
		schoolLayout = findViewById(R.id.school_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		guideLayout = findViewById(R.id.guide_layout);
		messageImage = (ImageView) findViewById(R.id.message_image);
		schoolImage = (ImageView) findViewById(R.id.news_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		guideImage = (ImageView) findViewById(R.id.setting_image);
		messageText = (TextView) findViewById(R.id.message_text);
		schoolText = (TextView) findViewById(R.id.news_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		guideText = (TextView) findViewById(R.id.setting_text);
		
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		guideLayout.setOnClickListener(this);
		schoolLayout.setOnClickListener(this);
		
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		InputStream is = getResources().openRawResource(R.drawable.profile);  
		Bitmap bitmap = ImageUtil.getRoundBitmap(BitmapFactory.decodeStream(is));
		iv_icon.setImageBitmap(bitmap);

		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		InputStream in = getResources().openRawResource(R.drawable.profile);  
		Bitmap bp = ImageUtil.getRoundBitmap(BitmapFactory.decodeStream(in));
		iv_bottom.setImageBitmap(bp);
		
		lv = (ListView) findViewById(R.id.lv);
		initMenu();
		MenuAdapter adapter = new MenuAdapter(PersonActivity.this, R.layout.item_menu, menuList);
		lv.setAdapter(adapter);
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) { 
				dl.open();
			}
		});
		
	}


	
	private void initMenu() {
		
		Menu menu1 = new Menu("我的好友");
		Menu menu2 = new Menu("我的收藏");
		Menu menu3 = new Menu("浏览历史");
		Menu menu4 = new Menu("我的简历");
		Menu menu5 = new Menu("退出应用");
		
		menuList.add(menu1);
		menuList.add(menu2);
		menuList.add(menu3);
		menuList.add(menu4);
		menuList.add(menu5);
	}

	private void initDragLayout() {
		
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {

			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.contacts_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.school_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.guide_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
		
	}

	private void setTabSelection(int index) {
		
		// 每次选中之前先清楚掉上次的选中状态
				clearSelection();
				// 开启一个Fragment事务
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
				hideFragments(transaction);
				switch (index) {
				case 0:
					// 当点击了消息tab时，改变控件的图片和文字颜色
					messageImage.setImageResource(R.drawable.message_selected);
					messageText.setTextColor(Color.WHITE);
					if (messageFragment == null) {
						// 如果MessageFragment为空，则创建一个并添加到界面上
						messageFragment = new MessageFragment();
						transaction.add(R.id.content, messageFragment);
					} else {
						// 如果MessageFragment不为空，则直接将它显示出来
						transaction.show(messageFragment);
					}
					break;
				case 1:
					// 当点击了联系人tab时，改变控件的图片和文字颜色
					contactsImage.setImageResource(R.drawable.contacts_selected);
					contactsText.setTextColor(Color.WHITE);
					if (contactsFragment == null) {
						// 如果ContactsFragment为空，则创建一个并添加到界面上
						contactsFragment = new ContactFragment();
						transaction.add(R.id.content, contactsFragment);
					} else {
						// 如果ContactsFragment不为空，则直接将它显示出来
						transaction.show(contactsFragment);
					}
					break;
				case 2:
					// 当点击了动态tab时，改变控件的图片和文字颜色
					schoolImage.setImageResource(R.drawable.news_selected);
					schoolText.setTextColor(Color.WHITE);
					if (schoolFragment == null) {
						// 如果NewsFragment为空，则创建一个并添加到界面上
						schoolFragment = new SchoolFragment();
						transaction.add(R.id.content, schoolFragment);
					} else {
						// 如果NewsFragment不为空，则直接将它显示出来
						transaction.show(schoolFragment);
					} 	
					break;
				case 3:
				default:
					// 当点击了设置tab时，改变控件的图片和文字颜色
					guideImage.setImageResource(R.drawable.setting_selected);
					guideText.setTextColor(Color.WHITE);
					if (guideFragment == null) {
						// 如果SettingFragment为空，则创建一个并添加到界面上
						guideFragment = new GuideFragment();
						transaction.add(R.id.content, guideFragment);
					} else {
						// 如果SettingFragment不为空，则直接将它显示出来
						transaction.show(guideFragment);
					}
					break;
				}
				transaction.commit();
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.message_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.contacts_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		schoolImage.setImageResource(R.drawable.news_unselected);
		schoolText.setTextColor(Color.parseColor("#82858b"));
		guideImage.setImageResource(R.drawable.setting_unselected);
		guideText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (schoolFragment != null) {
			transaction.hide(schoolFragment);
		}
		if (guideFragment != null) {
			transaction.hide(guideFragment);
		}
	}
	
}
