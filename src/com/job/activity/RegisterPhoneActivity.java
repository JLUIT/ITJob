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

public class RegisterPhoneActivity extends BaseActivity {

	private EditText phone;
	private Button next_btn;
	private String type;
	private String phoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_register);
		Intent intent = getIntent();
		type = intent.getStringExtra("register_type");
		phone = (EditText) findViewById(R.id.et_phoneNumber);
		next_btn = (Button) findViewById(R.id.btn_next);
		next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Thread thread =new Thread(new CheckThread());
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
            case -1:  
                Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();  
                break;
            case 0:
            	if(type.equals("person")){
					Intent i = new Intent(RegisterPhoneActivity.this, RegisterPersonActivity.class);
					i.putExtra("phoneNumber", phoneNumber);
					startActivity(i);
				}else{
					Intent i = new Intent(RegisterPhoneActivity.this, RegisterPasswordActivity.class);
					i.putExtra("phoneNumber", phoneNumber);
					startActivity(i);
				}
            	break;
            case 1:
            	 Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT).show();  
                 break;
            case 2:
            	 Toast.makeText(getApplicationContext(), "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();  
                 break;
            case 3:
            	Toast.makeText(getApplicationContext(), "电话号码已经被使用！", Toast.LENGTH_SHORT).show();  
                break;
            }  
              
        }  
    }; 

    class CheckThread implements Runnable
    {

		@Override
		public void run() {
			
			phoneNumber=phone.getText().toString().trim();
			Message msg = handler.obtainMessage();
			if(phoneNumber.equals(""))
			{
				msg.what=-1;
				handler.sendMessage(msg);
			}
			else
			{
				JSONObject object=new JSONObject();
				try {
					object.put("phoneNumber", phoneNumber);
					object.put("type", "checkPhone");//表示去服务器检查电话号码的正确性
					object.put("PE", type);
					msg.what=CheckServer(object);
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}
    	
    }
    
    private int CheckServer(JSONObject object)  
    {  
    	String path="http://49.140.60.236:8080/IT/P_Regist";
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
        	else return 1;
    	}catch (MalformedURLException e) {  
            e.printStackTrace();
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return 1;
    }
}
