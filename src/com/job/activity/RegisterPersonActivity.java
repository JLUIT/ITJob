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
import org.json.JSONException;
import org.json.JSONObject;
import com.job.R;
import com.job.base.BaseActivity;

public class RegisterPersonActivity extends BaseActivity {

	private EditText username,email,password,D_password;
	private Button finish_btn;
	private String phoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_register);
		init();
		
	}

	private void init() {
		
		username = (EditText) findViewById(R.id.name1);
		email = (EditText) findViewById(R.id.email1);
		password = (EditText) findViewById(R.id.password1);
		D_password=(EditText)findViewById(R.id.password_confirm);
		finish_btn = (Button) findViewById(R.id.register_complete1);
		Intent intent = getIntent();
		phoneNumber = intent.getStringExtra("phoneNumber");
		finish_btn.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Thread registThread = new Thread(new RegistThread());
                registThread.start();
			}
		});
		
	}

	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
                Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();  
                Intent intent = new Intent();  
                intent.setClass(RegisterPersonActivity.this, LoginActivity.class);  
                startActivity(intent);  
                finish();  
                break;  
            case -1:  
                Toast.makeText(getApplicationContext(), "注册信息不能为空", Toast.LENGTH_SHORT).show();  
                break;  
            case -2:
            	Toast.makeText(getApplicationContext(), "网络连接失败",Toast.LENGTH_SHORT).show();
            	break;
            case -3:
            	Toast.makeText(getApplicationContext(), "两次密码不一致！",Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	Toast.makeText(getApplicationContext(), "用户名已被注册",Toast.LENGTH_SHORT).show();
            	break;
            case 2:
            	Toast.makeText(getApplicationContext(), "密码长度6-12位",Toast.LENGTH_SHORT).show();
            	break;
            case 5:
            	Toast.makeText(getApplicationContext(), "邮箱格式不正确",Toast.LENGTH_SHORT).show();
            	break;
            case 6:
            	Toast.makeText(getApplicationContext(), "邮箱已被注册",Toast.LENGTH_SHORT).show();
            	break;
            case 7:
            	Toast.makeText(getApplicationContext(), "注册失败",Toast.LENGTH_SHORT).show();
            }  
              
        }  
    }; 
    
    class RegistThread implements Runnable  
    {  
        @Override  
        public void run() {  
        	String name = username.getText().toString().trim();  
            String pass = password.getText().toString().trim();
            String D_pass=D_password.getText().toString().trim();
            String mail=email.getText().toString().trim();
            Message msg = handler.obtainMessage();
            if(name.equals("")||pass.equals("")||D_pass.equals("")||phoneNumber.equals("")||mail.equals(""))
            {
            	msg.what=-1;
            	handler.sendMessage(msg);
            }
            else if(!pass.equals(D_pass))
            {
            	msg.what=-3;
            	handler.sendMessage(msg);
            }
            else{
            	   JSONObject object=new JSONObject();
            	   try {
            		   object.put("username", name);
            		   object.put("password", pass);
	            	   object.put("email", mail);
	            	   object.put("phoneNumber", phoneNumber);
	            	   object.put("type", "register");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       int result=registServer(object);  
			       msg.what =result;  
		           handler.sendMessage(msg);  
                }
        }  
          
    } 
    
    private int registServer(JSONObject object)  
    {  
    	
    	 String path=LoginActivity.URL+"P_Regist";  
		 
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
	 		
	         e.printStackTrace();  
	         
	     } catch (IOException e) {  
	    	 
	    	e.printStackTrace();  
	    	
	     }  
	     return -2; 
    }  

}
