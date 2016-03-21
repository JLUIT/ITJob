package com.job.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class CompanyChangePassWordActivity extends BaseActivity {

	private EditText edit_old_pwd, edit_new_pwd, edit_confirm_pwd;
	private Button finish_btn;
	private String oldPass,newPass,confirmPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_change_password);
		init();
	}

	private void init() {
		
		edit_old_pwd = (EditText) findViewById(R.id.old_password);
		edit_new_pwd = (EditText) findViewById(R.id.new_password);
		edit_confirm_pwd = (EditText) findViewById(R.id.password_again);
		finish_btn = (Button) findViewById(R.id.finish_btn);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save_pwd();
				
			}
		});
	}

	protected void save_pwd() {
		oldPass=edit_old_pwd.getText().toString().trim();
		newPass=edit_new_pwd.getText().toString().trim();
		confirmPass=edit_confirm_pwd.getText().toString().trim();
		Message msg = handler.obtainMessage();
		if(oldPass.equals("")||newPass.equals("")||confirmPass.equals(""))
		{
			msg.what=1;
			handler.sendMessage(msg);
		}
		else if(oldPass.equals(newPass))
		{
			msg.what=2;
			handler.sendMessage(msg);
		}
		else if(!confirmPass.equals(newPass))
		{
			msg.what=3;
			handler.sendMessage(msg);
		}
		else if(newPass.length()>12||newPass.length()<6)
		{
			msg.what=7;
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
            	Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            	break;
            case 2:
            	Toast.makeText(getApplicationContext(), "旧密码和新密码一致", Toast.LENGTH_SHORT).show();
            	break;
            case 3:
            	Toast.makeText(getApplicationContext(), "新密码和确认密码不一致", Toast.LENGTH_SHORT).show();
            	break;
            case 4:
            	Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
            	intent2Activity(PersonMessageActivity.class);
            	break;
            case 5:
            	Toast.makeText(getApplicationContext(), "旧密码错误", Toast.LENGTH_SHORT).show();
            	break;
            case 6:
            	Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
            	break;
            case 7:
            	Toast.makeText(getApplicationContext(), "密码长度6~12位", Toast.LENGTH_SHORT).show();
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
				object.put("type", true);//非密码修改
				object.put("P_E", "P");//个人
				object.put("which", "password");//修改电话
				object.put("name", LoginActivity.name);
				object.put("oldPassword", oldPass);
				object.put("newPassword", newPass);
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
