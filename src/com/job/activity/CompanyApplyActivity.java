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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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

public class CompanyApplyActivity extends BaseActivity {

	private ImageView edit_msg;
	private PullToRefreshListView mPullRefreshListView;
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;
	private int list_num = 10;
	private CompanyAdapter adapter;
	private List<CompanyMsg> list = new ArrayList<CompanyMsg>();
	private String result="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_apply_message);
		init();
		new GetDataTask().execute();
		
	}

	private void init() {
		
		 edit_msg = (ImageView)  findViewById(R.id.release);
		 edit_msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				intent2Activity(Publishinfor.class);
			
			}
		});
		 
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
			}
			
		});
		// Set a listener to be invoked when the list should be refreshed.
		
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
				SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
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
			JSONObject object=new JSONObject();
			try {
				object.put("e_Name", LoginActivity.name);
				if(SelectServer(object))
				{
					JSONArray JobInfo=new JSONArray(result);
					int size=JobInfo.length();
					for(int i=0;i<size;i++)
					{
						JSONObject one=JobInfo.optJSONObject(i);
						CompanyMsg msg=new CompanyMsg();
						msg.setCompany_name(one.getString("e_Name"));
						msg.setCompany_property(one.getString("property"));
						msg.setCompany_size(one.getString("scope"));
						msg.setExperience(one.getString("acaQualification"));
						msg.setJob_name(one.getString("jobName"));
						msg.setSalary(one.getInt("salary")+"");
						msg.setSend_date(one.getString("sendTime"));
						msg.setSite(one.getString("city"));
						list.add(msg);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<CompanyMsg> result) {
			
			adapter = new CompanyAdapter(CompanyApplyActivity.this,result);
			mPullRefreshListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			if(result.size()==0)
				Toast.makeText(getApplicationContext(), "暂时没有招聘信息！", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
			 
	}

	//加载更多，每上拉一次加载十条数据
	private class GetMoreDateTask extends AsyncTask<Void, Void, List<CompanyMsg>>{
		
		@Override
		protected List<CompanyMsg> doInBackground(Void... params) {
			//数据库获取
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
