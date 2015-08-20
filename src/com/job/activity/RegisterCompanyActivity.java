package com.job.activity;


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
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.job.R;
import com.job.base.BaseActivity;

public class RegisterCompanyActivity extends BaseActivity {

	private EditText company_name, email, lisence;
	private Button finish_btn;
	private String phoneNumber,password;
	private ArrayList<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_register);
		init();
	}

	private void init() {
		
		company_name = (EditText) findViewById(R.id.name2);
		email = (EditText) findViewById(R.id.email2);
		lisence = (EditText) findViewById(R.id.license);
		finish_btn = (Button) findViewById(R.id.register_complete2);
		Intent intent=getIntent();
		list=intent.getStringArrayListExtra("message");
		phoneNumber=list.get(0);
		password=list.get(1);
		finish_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Thread thread =new Thread(new RegisterThread());
				thread.start();
			}
		});
		
	}

	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case -2:  
                Toast.makeText(getApplicationContext(), "注册失败，请稍后重试！", Toast.LENGTH_SHORT).show();  
                break;
            case -1:
            	 Toast.makeText(getApplicationContext(), "注册信息不能为空！", Toast.LENGTH_SHORT).show();  
                 break;
            case 0:
            	Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();  
                Intent intent = new Intent();  
                intent.setClass(RegisterCompanyActivity.this, CompanyActivity.class);  
                startActivity(intent);
                break;
            case 1:
            	Toast.makeText(getApplicationContext(), "邮箱格式不正确！", Toast.LENGTH_SHORT).show();  
                break;
            case 2:
            	Toast.makeText(getApplicationContext(), "邮箱已被注册！", Toast.LENGTH_SHORT).show();  
                break;
            case 3:
            	Toast.makeText(getApplicationContext(), "公司名已被注册！", Toast.LENGTH_SHORT).show();  
                break;
            case 4:
            	Toast.makeText(getApplicationContext(), "请填写正确的营业执照号！", Toast.LENGTH_SHORT).show();  
                break;
            case 5:
            	Toast.makeText(getApplicationContext(), "营业执照号已被注册", Toast.LENGTH_SHORT).show();  
            }  
              
        }  
    }; 
	
    class RegisterThread implements Runnable
    {

    	Message msg = handler.obtainMessage();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String E_Name=company_name.getText().toString().trim();
			String Email=email.getText().toString().trim();
			String E_Number=lisence.getText().toString().trim();
			if(E_Name.equals("")||Email.equals("")||E_Number.equals(""))
			{
				msg.what=-1;
				handler.sendMessage(msg);
			}
			else
			{
				JSONObject object=new JSONObject();
				try {
					object.put("phoneNumber", phoneNumber);
					object.put("password", password);
					object.put("e_Name", E_Name);
					object.put("email", Email);
					object.put("e_Number", E_Number);
					msg.what=RegisterServer(object);
					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
    }
    
    private int RegisterServer(JSONObject object)  
    {  
    	String path="http://49.140.60.236:8080/IT/E_Regist";
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
        	else return -2;
    	}catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return -2;
    }
    
}
