package com.job.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import com.job.R;
import com.job.base.BaseActivity;
public class JobInforAcitivity extends BaseActivity implements OnClickListener{

	private Button company_job;
    private Button company_style;
    private TextView job_name;
    private TextView company_name;
    private TextView send_date;
    private TextView site;
    private TextView salary;
    private TextView apply_num;
    private TextView stu_exp;
    private TextView company_natural;
    private TextView company_size;
    private CheckBox insurance;
    private CheckBox jiaotong;
    private CheckBox butie;
    private CheckBox peixun;
    private CheckBox jiangjing;
    private CheckBox lvyou;
    private TextView job_detail1;
    private Button consult;
    private Button collect;
    private Button apply_job;
    
    private String content="";
    private JSONObject object;
    private int resumeId;
    private String E_Name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_information);
		initView();
	}

	private void initView() {
        company_job = (Button) findViewById(R.id.company_job);
        company_style = (Button) findViewById(R.id.company_style);
        job_name = (TextView) findViewById(R.id.job_name);
        company_name = (TextView) findViewById(R.id.company_name);
        send_date = (TextView) findViewById(R.id.send_date);
        site = (TextView) findViewById(R.id.site);
        salary = (TextView) findViewById(R.id.salary);
        apply_num = (TextView) findViewById(R.id.apply_num);
        stu_exp = (TextView) findViewById(R.id.stu_exp);
        company_natural = (TextView) findViewById(R.id.company_natural);
        company_size = (TextView) findViewById(R.id.company_size);
        insurance = (CheckBox) findViewById(R.id.insurance);
        jiaotong = (CheckBox) findViewById(R.id.jiaotong);
        butie = (CheckBox) findViewById(R.id.butie);
        peixun = (CheckBox) findViewById(R.id.peixun);
        jiangjing = (CheckBox) findViewById(R.id.jiangjing);
        lvyou = (CheckBox) findViewById(R.id.lvyou);
        job_detail1 = (TextView) findViewById(R.id.job_detail1);
        consult = (Button) findViewById(R.id.consult);
        collect = (Button) findViewById(R.id.collect);
        apply_job = (Button) findViewById(R.id.apply_job);

        company_job.setOnClickListener(this);
        company_style.setOnClickListener(this);
        consult.setOnClickListener(this);
        collect.setOnClickListener(this);
        apply_job.setOnClickListener(this);
        
        Initial();//初始化显示信息
    }

	public void Initial()
	{
		Intent intent=getIntent();
		content=intent.getStringExtra("content");
		try {
			object=new JSONObject(content);
			resumeId=object.getInt("resumeId");
			E_Name=object.getString("e_Name");
			job_name.setText(object.getString("jobName"));
			company_name.setText(object.getString("e_Name"));
			send_date.setText(object.getString("sendTime"));
			site.setText(object.getString("city"));
			salary.setText(object.getString("salary"));
			apply_num.setText(object.getString("jobNeedNumber"));
			stu_exp.setText(object.getString("acaQualification"));
			company_natural.setText(object.getString("property"));
			company_size.setText(object.getString("scope"));
			job_detail1.setText(object.getString("jobDescription"));
			if(object.getInt("benefit1")==1)
			{
				insurance.setChecked(true);
				insurance.setClickable(false);
			}
			else insurance.setClickable(false);
			if(object.getInt("benefit2")==1)
			{
				jiaotong.setChecked(true);
				jiaotong.setClickable(false);
			}
			else jiaotong.setClickable(false);
			if(object.getInt("benefit3")==1)
			{
				butie.setChecked(true);
				butie.setClickable(false);
			}
			else butie.setClickable(false);
			if(object.getInt("benefit4")==1)
			{
				peixun.setChecked(true);
				peixun.setClickable(false);
			}
			else peixun.setClickable(false);
			if(object.getInt("benefit5")==1)
			{
				jiangjing.setChecked(true);
				jiangjing.setClickable(false);
			}
			else jiangjing.setClickable(false);
			if(object.getInt("benefit6")==1)
			{
				lvyou.setChecked(true);
				lvyou.setClickable(false);
			}
			else lvyou.setClickable(false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.company_job:
            	Intent intent=new Intent();
				intent.setClass(JobInforAcitivity.this, MessageActivity.class);
				intent.putExtra("e_Name", E_Name);
				intent.putExtra("type", "alljob");
				startActivity(intent);
                break;
            case R.id.company_style:

                break;
            case R.id.consult:

                break;
            case R.id.collect:
            	Thread collectThread=new Thread(new CollectThread());
            	collectThread.start();
                break;
            case R.id.apply_job:
            	Thread applyJobThread=new Thread(new ApplyJobThread());
            	applyJobThread.start();
                break;
        }
    }
    
    Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
            	Toast.makeText(getApplicationContext(), "咨询不能为空！", Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	Toast.makeText(getApplicationContext(), "咨询成功！请等待回复··", Toast.LENGTH_SHORT).show();
            	//consultText.setText("");
            	break;
            case 2:
            	Toast.makeText(getApplicationContext(), "咨询失败！请重试！", Toast.LENGTH_SHORT).show();
            	break;
            case 3:
            	Toast.makeText(getApplicationContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
            	break;
            case 4:
            	Toast.makeText(getApplicationContext(), "不能重复收藏！", Toast.LENGTH_SHORT).show();
            	break;
            case 5:
            	Toast.makeText(getApplicationContext(), "收藏失败！", Toast.LENGTH_SHORT).show();
            	break;
            case 6:
            	Toast.makeText(getApplicationContext(), "申请出错，请重试！", Toast.LENGTH_SHORT).show();
            	break;
            case 7:
            	AlertDialog.Builder builder=new AlertDialog.Builder(JobInforAcitivity.this);
            	builder.setTitle("系统提示")//设置对话框标题  
       		  
        	     .setMessage("您还没有简历！去创建？")//设置显示的内容  
        	  
        	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
        	         @Override  
        	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
        	        	 	Intent intent=new Intent();
        	        	 	intent.setClass(JobInforAcitivity.this, MyResumeActivity.class);
        	        	 	startActivity(intent);
        	         }  
        	}).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加取消按钮  
                @Override  
                public void onClick(DialogInterface dialog, int which) {//响应事件  
                		
                }  
         
            }).show();//在按键响应事件中显示此对话框  
            	break;
            case 8:
            	AlertDialog.Builder build=new AlertDialog.Builder(JobInforAcitivity.this);
            	build.setTitle("系统提示")//设置对话框标题  
       		  
        	     .setMessage("使用已有简历？")//设置显示的内容  
        	  
        	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
        	         @Override  
        	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
        	        	 	Thread thread=new Thread(new ApplyThread());
        	        	 	thread.start();
        	         }  
        	}).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加取消按钮  
                @Override  
                public void onClick(DialogInterface dialog, int which) {//响应事件  
                		
                }  
         
            }).show();//在按键响应事件中显示此对话框  
            	break;
            case 9:
            	Toast.makeText(getApplicationContext(), "申请成功！等待回复", Toast.LENGTH_SHORT).show();
            	break;
            case 10:
            	Toast.makeText(getApplicationContext(), "不能重复申请！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    class CollectThread implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String type="job";//表示收藏公司；job 表示收藏职位
			JSONObject object=new JSONObject();
			Message msg = handler.obtainMessage();
			try {
				object.put("type", type);
				object.put("username", LoginActivity.name);
				object.put("resumeId", resumeId);
				int sign=CollectServer(object);
				msg.what=sign;
	            handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private int CollectServer(JSONObject object)  
	  {  
	  	String path=LoginActivity.URL+"Collection";
	  	try{
	  		URL url=new URL(path);
	  		String content = String.valueOf(object);
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
	      		String result="";
	      		result = in.readLine();
	      		return Integer.parseInt(result);
	      	}
	      	else return 5;
	  	}catch (MalformedURLException e) {  
	          // TODO Auto-generated catch block  
	          e.printStackTrace();  
	      } catch (IOException e) {  
	          // TODO Auto-generated catch block  
	          e.printStackTrace();  
	     }  
	      return 5;
	}  
	
	class ApplyJobThread implements Runnable
	{

		Message msg = handler.obtainMessage();
		String path=LoginActivity.URL+"ApplyJob";
		@Override
		public void run() {
			// TODO Auto-generated method stub
	        JSONObject object=new JSONObject();
	        try {
				object.put("username", LoginActivity.name);
				object.put("type", "read");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				URL url=new URL(path);
	    		String content = String.valueOf(object);
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
			        if(code==200)  
		           {  
			        	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        	String result=rd.readLine();
			        	rd.close();
			        	msg.what=Integer.parseInt(result);
			        	handler.sendMessage(msg);
			         }  
			        else 
			        {
			        	msg.what=6;
			        	handler.sendMessage(msg);
			        }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	class ApplyThread implements Runnable
	{

		Message msg = handler.obtainMessage();
		String path=LoginActivity.URL+"ApplyJob";
		@Override
		public void run() {
			// TODO Auto-generated method stub
	        JSONObject object=new JSONObject();
	        try {
				object.put("username", LoginActivity.name);
				object.put("resumeId", resumeId);
				object.put("e_Name", E_Name);
				object.put("type", "write");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				URL url=new URL(path);
	    		String content = String.valueOf(object);
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
			        if(code==200)  
		           {  
			        	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        	String result=rd.readLine();
			        	rd.close();
			        	msg.what=Integer.parseInt(result);
			        	handler.sendMessage(msg);
			         }  
			        else 
			        {
			        	msg.what=6;
			        	handler.sendMessage(msg);
			        }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
}
