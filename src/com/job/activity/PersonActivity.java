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
		// ��һ������ʱѡ�е�0��tab
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
		
		Menu menu1 = new Menu("�ҵĺ���");
		Menu menu2 = new Menu("�ҵ��ղ�");
		Menu menu3 = new Menu("�����ʷ");
		Menu menu4 = new Menu("�ҵļ���");
		Menu menu5 = new Menu("�˳�Ӧ��");
		
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
			// ���������Ϣtabʱ��ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.contacts_layout:
			// ���������ϵ��tabʱ��ѡ�е�2��tab
			setTabSelection(1);
			break;
		case R.id.school_layout:
			// ������˶�̬tabʱ��ѡ�е�3��tab
			setTabSelection(2);
			break;
		case R.id.guide_layout:
			// �����������tabʱ��ѡ�е�4��tab
			setTabSelection(3);
			break;
		default:
			break;
		}
		
	}

	private void setTabSelection(int index) {
		
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
				clearSelection();
				// ����һ��Fragment����
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
				hideFragments(transaction);
				switch (index) {
				case 0:
					// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
					messageImage.setImageResource(R.drawable.message_selected);
					messageText.setTextColor(Color.WHITE);
					if (messageFragment == null) {
						// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
						messageFragment = new MessageFragment();
						transaction.add(R.id.content, messageFragment);
					} else {
						// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(messageFragment);
					}
					break;
				case 1:
					// ���������ϵ��tabʱ���ı�ؼ���ͼƬ��������ɫ
					contactsImage.setImageResource(R.drawable.contacts_selected);
					contactsText.setTextColor(Color.WHITE);
					if (contactsFragment == null) {
						// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
						contactsFragment = new ContactFragment();
						transaction.add(R.id.content, contactsFragment);
					} else {
						// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(contactsFragment);
					}
					break;
				case 2:
					// ������˶�̬tabʱ���ı�ؼ���ͼƬ��������ɫ
					schoolImage.setImageResource(R.drawable.news_selected);
					schoolText.setTextColor(Color.WHITE);
					if (schoolFragment == null) {
						// ���NewsFragmentΪ�գ��򴴽�һ������ӵ�������
						schoolFragment = new SchoolFragment();
						transaction.add(R.id.content, schoolFragment);
					} else {
						// ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(schoolFragment);
					} 	
					break;
				case 3:
				default:
					// �����������tabʱ���ı�ؼ���ͼƬ��������ɫ
					guideImage.setImageResource(R.drawable.setting_selected);
					guideText.setTextColor(Color.WHITE);
					if (guideFragment == null) {
						// ���SettingFragmentΪ�գ��򴴽�һ������ӵ�������
						guideFragment = new GuideFragment();
						transaction.add(R.id.content, guideFragment);
					} else {
						// ���SettingFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(guideFragment);
					}
					break;
				}
				transaction.commit();
	}
	
	/**
	 * ��������е�ѡ��״̬��
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
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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
