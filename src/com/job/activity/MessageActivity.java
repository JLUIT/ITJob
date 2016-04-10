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
	private JSONArray JobInfo;
	private String type;//filter表示筛选，alljob表示显示某公司的所有信息。
	private String E_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		init();
		Intent intent=getIntent();
		type=intent.getStringExtra("type");
		if(type.equals("filter"))
			Info=intent.getStringExtra("content");
		else E_name=intent.getStringExtra("e_Name");
		new GetDataTask().execute(); 	
		
	}
    
    private boolean FilterServer(JSONObject resume)  
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
				
					mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							Intent intent=new Intent();
							intent.setClass(MessageActivity.this, JobInforAcitivity.class);
							intent.putExtra("content", JobInfo.optJSONObject(position-1).toString());
						    startActivity(intent); 
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
						new GetDataTask().execute();
					} 
		
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						Log.e("TAG", "onPullUpToRefresh");
						//这里写上拉加载更多的任务
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
	
	//下拉刷新时加载最新招聘信息		
		private class GetDataTask extends AsyncTask<Void, Void, List<CompanyMsg>> {
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				list.clear();
				if(type.equals("filter"))
				{
					String[] str=Info.split(" ");
					JSONObject object=new JSONObject();
					try {
						object.put("province", str[0]);
						object.put("city", str[1]);
						object.put("salary", str[2]);
						object.put("property", str[3]);
						object.put("scope", str[4]);
						object.put("acaQualification", str[5]);
						object.put("sendTime", str[6]);
						if(FilterServer(object))
						{
							JobInfo=new JSONArray(result);
							int size=JobInfo.length();
							for(int i=0;i<size;i++)
							{
								JSONObject one=JobInfo.optJSONObject(i);
								CompanyMsg item = new CompanyMsg();
								item.setJob_name(one.getString("jobName"));
								item.setSalary("￥"+one.getInt("salary"));
								item.setSite(one.getString("province")+one.getString("city"));
								item.setCompany_name(one.getString("e_Name"));
								list.add(item);
								
							}
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					JSONObject object=new JSONObject();
					try {
						object.put("e_Name", E_name);
						if(SelectServer(object))
						{
							JobInfo=new JSONArray(result);
							int size=JobInfo.length();
							for(int i=0;i<size;i++)
							{
								JSONObject one=JobInfo.optJSONObject(i);
								CompanyMsg item = new CompanyMsg();
								item.setJob_name(one.getString("jobName"));
								item.setSalary("￥"+one.getInt("salary"));
								item.setSite(one.getString("province")+one.getString("city"));
								item.setCompany_name(one.getString("e_Name"));
								list.add(item);
								
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return list;
			}

			@Override
			protected void onPostExecute(List<CompanyMsg> result) {
				  
				adapter = new CompanyAdapter(MessageActivity.this, result);
				mPullRefreshListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
				if(result.size()==0)
				{
					Toast.makeText(getApplicationContext(), "没有符合要求的招聘信息，请重新筛选！", Toast.LENGTH_SHORT).show();
	            	finish();
				}
				adapter = new CompanyAdapter(MessageActivity.this, list);
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
				return list;
			}
			
			@Override
			protected void onPostExecute(List<CompanyMsg> result) {
				adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
			
			
		}
		
		 private boolean SelectServer(JSONObject resume)  
		    {  
		    	String path=LoginActivity.URL+"SelectAllJobOfCmp";
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
}
