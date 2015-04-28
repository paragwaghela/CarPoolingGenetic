package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.carpool.data.SharedData;
import com.carpool.thread.LoginThread;
import com.carpool.thread.LoginThread.LoginThreadInterface;
import com.carpool.utils.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Login extends BaseActivity implements LoginThreadInterface
{

	private static String TAG="Login";
	
	EditText etUsername;
	EditText etPassword;
	
	String uname="";
	String password="";

	@Override
	public int getLayoutXML() {
		return R.layout.login;
	}
	
	String goToWhere;
	Spinner spRole;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setVisibility(View.GONE);

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setText("Register");
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Login.this,choose_registration.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		etUsername=(EditText)findViewById(R.id.etUName);
		etPassword=(EditText)findViewById(R.id.etPasswd);
		
		spRole=(Spinner)findViewById(R.id.spRole);
		
		String[] role={"Passenger","Driver"};		
		List<String> items = new ArrayList<String>(Arrays.asList(role));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, items);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spRole.setAdapter(adapter);
		Button btnReset=(Button)findViewById(R.id.btnReset);
		btnReset.setOnClickListener(new OnClickListener() {

			//Action on btnReset
			@Override
			public void onClick(View arg0) {
				etUsername.setText("");
				etPassword.setText("");
			}
		});

//		Button btnForgetPassword=(Button)findViewById(R.id.btnForgetPassword);
//		btnForgetPassword.setOnClickListener(new OnClickListener() {
//
//			//Action on btnForgetPassword
//			@Override
//			public void onClick(View arg0) {
//
//				Intent i=new Intent(Login.this,ForgetPassword.class);
//				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(i);
//			}
//		});
		Log.d("ServerAd",SharedData.SERVER_URL);
		Button btnLogin=(Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
				String role="";
				uname=etUsername.getText().toString();
				password=etPassword.getText().toString();
				role =spRole.getSelectedItem().toString();
				//if uName is blank then check condition
				if(uname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Username");
					etUsername.setText("");
					etUsername.requestFocus();
				}
				//if password is blank then check condition
				else if(password.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Password.");
					etPassword.setText("");
					etPassword.requestFocus();
				}
				else
				{
					//To check email-Id Validation
						showWait("Logging In...");
						/*SharedData.mMySharedPref.setUserName(uname);
						SharedData.mMySharedPref.setPassword(password);
						Intent i=new Intent(Login.this,UserHome.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();*/
						new LoginThread(Login.this,uname,password,role).start();
				}			
			}
		});
	}

	private final static int LOGIN_DATA_TRUE=1;
	private final static int LOGIN_DATA_FALSE=2;
	private final static int LOGIN_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case LOGIN_DATA_TRUE:
			{
				SharedData.mMySharedPref.setUserName(uname);
				SharedData.mMySharedPref.setPassword(password);
				Intent i=new Intent(Login.this,UserHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			break;
			case LOGIN_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!", "Login Failed : "+msg.obj.toString());
			}
			break;

			case LOGIN_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};

	@Override
	public void onLoginThreadDataReturned(Boolean isSuccess,String msg) {
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(LOGIN_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(LOGIN_DATA_FALSE,msg));
	}

	@Override
	public void onLoginThreadErrorReturned() {
		mHandler.sendMessage(mHandler.obtainMessage(LOGIN_ERROR,null));
	}
}

