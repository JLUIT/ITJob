package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.job.R;


public class StartActivity extends Activity {

	private static final int FAILURE = 0; // ʧ�� 
    private static final int SUCCESS = 1; // �ɹ� 
    private static final int OFFLINE = 2; // ���֧�������Ķ�����������ģʽ 
    private static final int SHOW_TIME_MIN = 1000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				int result;
				long startTime = System.currentTimeMillis(); 

				result = loadingCache();
				long loadingTime = System.currentTimeMillis() - startTime; 
				 if (loadingTime < SHOW_TIME_MIN) { 
		                try { 
		                    Thread.sleep(SHOW_TIME_MIN - loadingTime); 
		                } catch (InterruptedException e) { 
		                    e.printStackTrace(); 
		                } 
		            } 
		        
				return result;
			}

			@Override
			protected void onPostExecute(Integer result) {
				Intent intent = new Intent(StartActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
			}
			
			
			
		}.execute(new Void[]{});
	}

	protected int loadingCache() {
		 

		return 0;
	}
}

