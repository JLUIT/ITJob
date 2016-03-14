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
	private String Info;//ɸѡ����
	private String result="";//ɸѡ���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		init();
		Intent intent=getIntent();
		Info=intent.getStringExtra("content");//��ʼʱ��ʾɸѡ�����Ƹ��Ϣ�б�5��������ˢ�£������ټ���5����
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
            	Toast.makeText(getApplicationContext(), "û�з���Ҫ�����Ƹ��Ϣ��������ɸѡ��", Toast.LENGTH_SHORT).show();
            	finish();
            	break;
            case 1://�ɹ�����ɸѡ���
            	try {
					JSONArray JobInfo=new JSONArray(result);
					int size=JobInfo.length();
					for(int i=0;i<size;i++)
					{
						final JSONObject object=JobInfo.optJSONObject(i);
						String value=object.getString("jobName")+" "+object.getString("e_Name")+" "+object.getInt("salary")+" "
								+object.getString("province")+" "+object.getString("city");//value����Ҫ��ʾ����Ϣ
						//������дְλ�б���룬��value��Ϣ�����ȥ�����������б�����������
						CompanyMsg item = new CompanyMsg();
						item.setJob_name(object.getString("jobName"));
						item.setSalary("��"+object.getInt("salary"));
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
						//	����д����ˢ�µ�����
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
						//����д�������ظ��������
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
	
	//����ˢ��ʱ����������Ƹ��Ϣ		
		private class GetDataTask extends AsyncTask<Void, Void, List<CompanyMsg>> {

			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				list.clear();
				//�����ݿ��ȡ���µ���Ƹ��Ϣ
				for(int i=0; i<list_num; i++){
					CompanyMsg item = new CompanyMsg("Android��������ʦ", "����Ͱ�", "����", 
							"�㶫ʡ������", "20~90��", "��6k-12k", "����", "2010��6��12��");
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

		//���ظ��࣬ÿ����һ�μ���ʮ������ 
		private class GetMoreDateTask extends AsyncTask<Void, Void, List<CompanyMsg>>{
			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				//���ݿ��ȡ�����ʮ������
				for(int i=0; i<list_num; i++){
					CompanyMsg item = new CompanyMsg("Android��������ʦ", "����Ͱ�", "����", 
							"�㶫ʡ������", "20~90��", "��6k-12k", "����", "2010��6��12��");
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
