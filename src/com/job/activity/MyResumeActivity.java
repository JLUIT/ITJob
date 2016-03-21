package com.job.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.job.R;
import com.job.base.BaseActivity;
import com.job.util.DateTimePickDialogUtil;

public class MyResumeActivity extends BaseActivity implements OnClickListener {

	private EditText name;
	private EditText sex;
	private TextView birthdate;
	private RelativeLayout birthday;
	private EditText emai;
	private EditText birth_address;
	private EditText stu_exp;
	private EditText phone;
	private EditText work_exp;
	private EditText city;
	private TextView save;
	private RelativeLayout introduction;
	private LinearLayout main;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_resume);
		initView();
	}

	private void initView() {
		name = (EditText) findViewById(R.id.name);
		sex = (EditText) findViewById(R.id.sex);
		birthdate = (TextView) findViewById(R.id.birthdate);
		birthday = (RelativeLayout) findViewById(R.id.birthday);
		emai = (EditText) findViewById(R.id.emai);
		birth_address = (EditText) findViewById(R.id.birth_address);
		stu_exp = (EditText) findViewById(R.id.stu_exp);
		phone = (EditText) findViewById(R.id.phone);
		work_exp = (EditText) findViewById(R.id.work_exp);
		city = (EditText) findViewById(R.id.city);
		introduction = (RelativeLayout) findViewById(R.id.introduction);
		main = (LinearLayout) findViewById(R.id.main);
		save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(this);
		birthday.setOnClickListener(this);
		introduction.setOnClickListener(this);
		main.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (name.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(name, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
				}
				if (sex.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(sex, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(sex.getWindowToken(), 0);
				}
				if (emai.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(emai, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
				}
				if (birth_address.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(birth_address, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(birth_address.getWindowToken(),
							0);
				}
				if (stu_exp.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(stu_exp, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(stu_exp.getWindowToken(), 0);
				}
				if (phone.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(phone, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);
				}
				if (work_exp.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(work_exp, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(work_exp.getWindowToken(), 0);
				}
				if (city.isFocused()) {
					// 设置编辑框不可见
					setEditTextEditable(city, false);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(city.getWindowToken(), 0);
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.birthday:
			select_birth();
			break;
		case R.id.introduction:
			intent2Activity(IntroductionActivity.class);
			break;
		case R.id.save:
			submit();
			break;
		default:
			break;

		}
	}

	private void select_birth() {

		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				this, birthdate.getText().toString());
		dateTimePicKDialog.dateTimePicKDialog(birthdate);
		
	}

	private void setEditTextEditable(EditText editText, boolean value) {
		if (value) {
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
		} else {
			editText.setFocusableInTouchMode(false);
			editText.clearFocus();
		}
	}
	
	private void submit() {
		// 验证处理
		String name1 = name.getText().toString().trim();
		if (TextUtils.isEmpty(name1)) {
			Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String sex1 = sex.getText().toString().trim();
		if (TextUtils.isEmpty(sex1)) {
			Toast.makeText(this, "sex不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String emai1 = emai.getText().toString().trim();
		if (TextUtils.isEmpty(emai1)) {
			Toast.makeText(this, "emai不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String address1 = birth_address.getText().toString().trim();
		if (TextUtils.isEmpty(address1)) {
			Toast.makeText(this, "address不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String exp1 = stu_exp.getText().toString().trim();
		if (TextUtils.isEmpty(exp1)) {
			Toast.makeText(this, "exp不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String phone1 = phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone1)) {
			Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String exp = work_exp.getText().toString().trim();
		if (TextUtils.isEmpty(exp)) {
			Toast.makeText(this, "exp不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String city1 = city.getText().toString().trim();
		if (TextUtils.isEmpty(city1)) {
			Toast.makeText(this, "city不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		// TODO validate success, do something

	}

}
