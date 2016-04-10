package com.job.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.job.activity.CompanyBindPhoneActivity.SubmitThread;
import com.job.base.BaseActivity;

public class CompanyBindEmailActivity extends BaseActivity {

	private EditText emailText;
	private TextView save;
	private String Email="",P_E;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.binding_email);
		init();
	}

	private void init() {
		
		emailText = (EditText) findViewById(R.id.et_email);
		save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save_email();
			}
		});
	}
	
	protected void save_email() {
		Intent intent=getIntent();
		String oldEmail=intent.getStringExtra("email");
		P_E=intent.getStringExtra("P_E");
		Email=emailText.getText().toString().trim();
		Message msg = handler.obtainMessage();
		if(Email.equals(""))
		{
			msg.what=1;
			handler.sendMessage(msg);
		}
		else if(Email.equals(oldEmail))
		{
			msg.what=5;
			handler.sendMessage(msg);
		}
		else
		{
			Thread submit=new Thread(new SubmitThread());
			submit.start();
		}
	}
	
	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
            	Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	Toast.makeText(getApplicationContext(), "邮箱不能为空", Toast.LENGTH_SHORT).show();
            	break;
            case 2:
            	Toast.makeText(getApplicationContext(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            	break;
            case 3:
            	Toast.makeText(getApplicationContext(), "该邮箱已被使用", Toast.LENGTH_SHORT).show();
            	break;
            case 4:
            	Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
            	if(P_E.equals("P"))
            		intent2Activity(PersonMessageActivity.class);
            	else 
            		intent2Activity(CompanyMessageActivity.class);
            	break;
            case 5:
            	Toast.makeText(getApplicationContext(), "与原邮箱相同", Toast.LENGTH_SHORT).show();
            	break;
            case 6:
            	Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
            }  
        }  
    };  
    
    class SubmitThread implements Runnable
    {
    	Message msg = handler.obtainMessage();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			JSONObject object=new JSONObject();
			try {
				object.put("type", false);//非密码修改
				object.put("P_E", "E");//公司
				object.put("which", "email");//修改邮箱
				object.put("name", LoginActivity.name);
				object.put("email", Email);
				msg.what=Server(object);
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    public int Server(JSONObject object)
    {
    	String path=LoginActivity.URL+"SetInfoManager";
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
        		in.close();
        		return Integer.parseInt(result);
        	}
        	else return 0;
    	}catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
       } 
    	return 0;
    }
}
