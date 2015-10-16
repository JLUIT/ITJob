package com.job.activity;

import java.util.ArrayList;
import java.util.List;

import com.job.R;
import com.job.adapter.CompanyAdapter;
import com.job.base.BaseActivity;
import com.job.bean.CompanyMsg;
import com.job.view.PullToRefreshBase;
import com.job.view.PullToRefreshListView;
import com.job.view.SoundPullEventListener;
import com.job.view.PullToRefreshBase.OnRefreshListener2;
import com.job.view.PullToRefreshBase.State;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MessageActivity extends BaseActivity {

	private PullToRefreshListView mPullRefreshListView;
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;
	private int list_num = 10;
	private CompanyAdapter adapter;
	private List<CompanyMsg> list = new ArrayList<CompanyMsg>();
	private View messageLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		init();
		new GetDataTask().execute(); 	
		
	}

	
	private void init() {
		mPullRefreshListView = (PullToRefreshListView) messageLayout.findViewById(R.id.pull_refresh_list);
		// Set a listener to be invoked when the list should be refreshed.
		
				mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>()
				{
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) 
					{ 
						Log.e("TAG", "onPullDownToRefresh");
						//	����д����ˢ�µ�����
						String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
 
						// Do work to refresh the list here.  
						new GetDataTask().execute();
					} 
		
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						Log.e("TAG", "onPullUpToRefresh");
						//����д�������ظ��������
						new GetMoreDateTask().execute();
					}
				});
				
				/**
				 * Add Sound Event Listener
				 */
				SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(MessageActivity.this);
				soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
				soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
				soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
				mPullRefreshListView.setOnPullEventListener(soundListener);		
	}
	
	//����ˢ��ʱ����������Ƹ��Ϣ
		private class GetDataTask extends AsyncTask<Void, Void, List<CompanyMsg>> {

			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				list.clear();
				//�����ݿ��ȡ���µ���Ƹ��Ϣ
				for(int i=0; i<list_num; i++){
					
				}
				
				return list;
			}

			@Override
			protected void onPostExecute(List<CompanyMsg> result) {
				  
				adapter = new CompanyAdapter(MessageActivity.this, result);
				mPullRefreshListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
			
			
		}

		//���ظ��࣬ÿ����һ�μ���ʮ������ 
		private class GetMoreDateTask extends AsyncTask<Void, Void, List<CompanyMsg>>{
			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				//���ݿ��ȡ�����ʮ������
				for(int i=0; i<list_num; i++){
					
				}
				
				return list;
			}
			
			@Override
			protected void onPostExecute(List<CompanyMsg> result) {
				adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
			
			
		}
}
