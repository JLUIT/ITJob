package com.job.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.job.R;
import com.job.adapter.CompanyAdapter;
import com.job.base.BaseActivity;
import com.job.bean.CompanyMsg;
import com.job.view.PullToRefreshBase;
import com.job.view.PullToRefreshBase.OnRefreshListener2;
import com.job.view.PullToRefreshBase.State;
import com.job.view.PullToRefreshListView;
import com.job.view.SoundPullEventListener;

public class MessageActivity extends BaseActivity {

	private PullToRefreshListView mPullRefreshListView;
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;
	private int list_num = 10;
	private CompanyAdapter adapter;
	private List<CompanyMsg> list = new ArrayList<CompanyMsg>();
	private String Info;//筛选条件
	private String result="";//筛选结果
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		init();
		Intent intent=getIntent();
		Info=intent.getStringExtra("content");//初始时显示筛选后的招聘信息列表5条；下拉刷新；上拉再加载5条；
		Thread thread=new Thread(new selectThread());
		thread.start();
//		new GetDataTask().execute(); 	
		
	}

	Handler handler = new Handler()  
    {  
		public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
            	Toast.makeText(getApplicationContext(), "没有符合要求的招聘信息，请重新筛选！", Toast.LENGTH_SHORT).show();
            	finish();
            	break;
            case 1://成功返回筛选结果
            	try {
					JSONArray JobInfo=new JSONArray(result);
					int size=JobInfo.length();
					for(int i=0;i<size;i++)
					{
						final JSONObject object=JobInfo.optJSONObject(i);
						String value=object.getString("jobName")+" "+object.getString("e_Name")+" "+object.getInt("salary")+" "
								+object.getString("province")+" "+object.getString("city");//value是需要显示的信息
						//接下来写职位列表代码，将value信息添加上去；并且设置列表点击监听器；
						CompanyMsg item = new CompanyMsg();
						item.setJob_name(object.getString("jobName"));
						item.setSalary("￥"+object.getInt("salary"));
						item.setSite(object.getString("province")+object.getString("city"));
						item.setCompany_name(object.getString("e_Name"));
						list.add(item);
						
					}
					adapter = new CompanyAdapter(MessageActivity.this, list);
					mPullRefreshListView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }  
              
        }  
    };  
	
    class selectThread implements Runnable
    {

		@Override
		public void run() {
			String[] str=Info.split(" ");
			JSONObject object=new JSONObject();
			Message msg = handler.obtainMessage();
			try {
				object.put("province", str[0]);
				object.put("city", str[1]);
				object.put("salary", str[2]);
				object.put("property", str[3]);
				object.put("scope", str[4]);
				object.put("acaQualification", str[5]);
				object.put("sendTime", str[6]);
				boolean result=SelectServer(object);
				if(result)
				{
					msg.what=1;
					handler.sendMessage(msg);
				}
				else
				{
					msg.what=0;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    
    private boolean SelectServer(JSONObject resume)  
    {  
    	String path=LoginActivity.URL+"SelectJobInfo";
    	try{
    		URL url=new URL(path);
    		String content = String.valueOf(resume);
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setConnectTimeout(5000);
        	conn.setDoOutput(true);
        	conn.setRequestMethod("POST");
        	conn.setRequestProperty("User-Agent", "Fiddler");
        	conn.setRequestProperty("Content-Type", "application/json");
        	OutputStream os = conn.getOutputStream();
        	os.write(content.getBytes());
        	os.close();
        	int code = conn.getResponseCode();
        	if(code == 200)
        	{
        		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        		String str="";
        		while((str=in.readLine())!=null)
 	        		result+=str;
 	        	in.close();
        		if(result.equals("null"))
        			return false;
        		else return true;
        	}
        	else return false;
    	}catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return false;
    }
    
	private void init() {
		mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
		// Set a listener to be invoked when the list should be refreshed.
				mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
							
					}
				});
				mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>()
				{
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) 
					{ 
						Log.e("TAG", "onPullDownToRefresh");
						//	这里写下拉刷新的任务
						String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
 
						// Do work to refresh the list here.  
						Thread thread=new Thread(new selectThread());
						thread.start();
//						new GetDataTask().execute();
					} 
		
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						Log.e("TAG", "onPullUpToRefresh");
						//这里写上拉加载更多的任务
						Thread thread=new Thread(new selectThread());
						thread.start();
//						new GetMoreDateTask().execute();
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
	
	//下拉刷新时加载最新招聘信息		
		private class GetDataTask extends AsyncTask<Void, Void, List<CompanyMsg>> {

			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				list.clear();
				//从数据库读取最新的招聘信息
				for(int i=0; i<list_num; i++){
					CompanyMsg item = new CompanyMsg("Android开发工程师", "阿里巴巴", "本科", 
							"广东省广州市", "20~90人", "￥6k-12k", "国企", "2010年6月12日");
					list.add(item);
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

		//加载更多，每上拉一次加载十条数据 
		private class GetMoreDateTask extends AsyncTask<Void, Void, List<CompanyMsg>>{
			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				//数据库读取额外的十条数据
				for(int i=0; i<list_num; i++){
					CompanyMsg item = new CompanyMsg("Android开发工程师", "阿里巴巴", "本科", 
							"广东省广州市", "20~90人", "￥6k-12k", "国企", "2010年6月12日");
					list.add(item);
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
