package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carpool.data.SharedData;
import com.carpool.thread.EditPassangerProfileInfoThread;
import com.carpool.thread.EditPassangerProfileInfoThread.EditPassangerProfileInfoThreadInterface;
import com.carpool.thread.EditProfileInfoThread;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditPassangerProfile extends BaseActivity implements EditPassangerProfileInfoThreadInterface{
	
	
	public int getLayoutXML() {
		return R.layout.edit_passanger_profile;
	}
	Spinner spGender;
    @Override
    public void onCreate(Bundle savedInstanceState) {
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

		TextView txtTitle=(TextView)findViewById(R.id.txtTitle);
		txtTitle.setText("Edit Profile");
		
		spGender=(Spinner)findViewById(R.id.spGender);

		String[] gender={"Male","Female"};		
		List<String> items = new ArrayList<String>(Arrays.asList(gender));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditPassangerProfile.this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGender.setAdapter(adapter);

		EditText etFName=(EditText)findViewById(R.id.editFName);
		EditText etLName=(EditText)findViewById(R.id.editLName);
		EditText etUserName=(EditText)findViewById(R.id.editUserName);
		//EditText etPassword =(EditText)findViewById(R.id.editPassword);
		EditText etContact=(EditText)findViewById(R.id.editContact);
		EditText etEmail=(EditText)findViewById(R.id.editMail);
		EditText editAddress=(EditText)findViewById(R.id.editAddr);
		EditText etAadhar=(EditText)findViewById(R.id.etAadhar);
		
		etFName.setText(SharedData.mUserData.fname);
		etLName.setText(SharedData.mUserData.lname);
		etUserName.setText(SharedData.mUserData.uname);
		etContact.setText(SharedData.mUserData.contact);
		etEmail.setText(SharedData.mUserData.email);
		etEmail.setEnabled(false);
		editAddress.setText(SharedData.mUserData.addr);
		etAadhar.setText(SharedData.mUserData.adharId);
		//etPassword.setEnabled(false);
		if(SharedData.mUserData.gender.equals("Male"))
			spGender.setSelection(0, true);
		else
			spGender.setSelection(1, true);
		
		Button btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setText("Save");
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText etFName=(EditText)findViewById(R.id.editFName);
				EditText etLName=(EditText)findViewById(R.id.editLName);
				EditText etUserName=(EditText)findViewById(R.id.editLName);
				EditText etContact=(EditText)findViewById(R.id.editContact);
				EditText editAddress=(EditText)findViewById(R.id.editAddr);
				EditText etAadhar=(EditText)findViewById(R.id.etAadhar);
				EditText etEmail=(EditText)findViewById(R.id.editMail);
				
				String fname="",uname="",lname="",gender="",contact="",address="",aadhar="",email="";
				fname=etFName.getText().toString();
				lname=etLName.getText().toString();
				uname=etUserName.getText().toString();
				gender=spGender.getSelectedItem().toString();
				email=etEmail.getText().toString();
				contact=etContact.getText().toString();
				address=editAddress.getText().toString();
				aadhar=etAadhar.getText().toString();
								
				if(fname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter First Name.");	
					etFName.setText("");
					etFName.requestFocus();
				}
				if(uname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter User Name.");	
					etFName.setText("");
					etFName.requestFocus();
				}
				
				else if(lname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Last Name.");	
					etLName.setText("");
					etLName.requestFocus();
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
					showWait("Updating Profile Data...");
					new EditPassangerProfileInfoThread(EditPassangerProfile.this,fname,uname,lname,gender,contact,address,aadhar,email).start();
				}
			}
		});

    }

    private final static int UPDATE_DATA_TRUE=1;
	private final static int UPDATE_DATA_FALSE=2;
	private final static int UPDATE_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case UPDATE_DATA_TRUE:
			{
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
			case UPDATE_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!",msg.obj.toString());
			}
			break;
			case UPDATE_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};
    
    	@Override
	public void onEditPassangerProfileInfoThreadDataReturned(boolean isSuccess,
			String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DATA_TRUE,msg));
		else
			mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DATA_FALSE,msg));
	}

	@Override
	public void onEditPassangerProfileInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(UPDATE_ERROR,null));
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_passanger_profile, menu);
        return true;
    }


}
