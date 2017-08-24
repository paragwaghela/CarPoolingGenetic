package com.carpool;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.carpool.thread.PassangerRegistrationThread;
import com.carpool.thread.PassangerRegistrationThread.PassangerRegistrationThreadInterface;
import com.carpool.utils.BaseActivity;


public class Registration_passanger extends BaseActivity implements PassangerRegistrationThreadInterface{


	private static String TAG="Register";

	@Override
	public int getLayoutXML() {
		return R.layout.registration_passanger;
	}
	
	Spinner spGender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);
		
			
		spGender=(Spinner)findViewById(R.id.spGender);
		
		String[] gender={"Male","Female"};	
		List<String> items = new ArrayList<String>(Arrays.asList(gender));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registration_passanger.this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGender.setAdapter(adapter);

		Button btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText etFName=(EditText)findViewById(R.id.editFName);
				EditText etLName=(EditText)findViewById(R.id.editLName);
				EditText etUserName=(EditText)findViewById(R.id.editUserName);
				EditText etContact=(EditText)findViewById(R.id.editContact);
				EditText etEmail=(EditText)findViewById(R.id.editMail);
				EditText etPassword=(EditText)findViewById(R.id.editPassword);
				EditText editAddress=(EditText)findViewById(R.id.editAddr);
				EditText etAadhar=(EditText)findViewById(R.id.etAadhar);
				
				String fname="",username="",lname="",gender="",dob="",contact="",email="",passwd="",address="",aadhar="";//,photo="",license="";
				fname=etFName.getText().toString();
				lname=etLName.getText().toString();
				username=etUserName.getText().toString();
				gender=spGender.getSelectedItem().toString();
				contact=etContact.getText().toString();
				email=etEmail.getText().toString();
				passwd=etPassword.getText().toString();
				address=editAddress.getText().toString();
				aadhar=etAadhar.getText().toString();
				
				if(fname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter First Name.");	
					etFName.setText("");
					etFName.requestFocus();
				}
				else if(lname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Last Name.");	
					etLName.setText("");
					etLName.requestFocus();
				}
				
				else if(username.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter User Name.");	
					etUserName.setText("");
					etUserName.requestFocus();
				}
				else if(contact.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Contact Number.");	
					etContact.setText("");
					etContact.requestFocus();
				}
				else if(contact.length()!=10)
				{
					ShowSimpleDialog("Alert!", "Please enter valid Contact Number.");	
					etContact.setText("");
					etContact.requestFocus();
				}
				else if(email.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter email address.");	
					etEmail.setText("");
					etEmail.requestFocus();
				}
				else if(passwd.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter password.");	
					etPassword.setText("");
					etPassword.requestFocus();
				}
				else if(address.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter address.");	
					editAddress.setText("");
					editAddress.requestFocus();
				}
				else if(aadhar.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter your aadhar Id number.");	
					etAadhar.setText("");
					etAadhar.requestFocus();
				}
				else
				{
					Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
					Matcher matcher = pattern.matcher(email);
					if(!matcher.matches())
					{
						ShowSimpleDialog("Alert!", "Please Enter Valid Username as Mail Id.");
						etEmail.setText("");
						etEmail.requestFocus();
					}
					else
					{
						showWait("Registering...");
						new PassangerRegistrationThread(Registration_passanger.this,fname,username,lname,gender,contact,email,address,aadhar,passwd).start();
					}
				}
			}
		});
	}
	

	private final static int REGISTER_DATA_TRUE=1;
	private final static int REGISTER_DATA_FALSE=2;
	private final static int REGISTER_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case REGISTER_DATA_TRUE:
			{
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
			case REGISTER_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!",msg.obj.toString());
			}
			break;
			case REGISTER_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};

		@Override
		public void onPassangerRegistrationThreadDataReturned(boolean isSuccess,String msg) {
			if(isSuccess)
				mHandler.sendMessage(mHandler.obtainMessage(REGISTER_DATA_TRUE,msg));
			else
				mHandler.sendMessage(mHandler.obtainMessage(REGISTER_DATA_FALSE,msg));
		}
	
		@Override
		public void onPassangerRegistrationThreadErrorReturned() {	
			mHandler.sendMessage(mHandler.obtainMessage(REGISTER_ERROR,null));
		}

}
