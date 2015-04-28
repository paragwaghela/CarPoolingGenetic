package com.carpool;

import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class choose_registration extends BaseActivity {

	private static String TAG="Choose Registration";
	
	public int getLayoutXML() {
		return R.layout.activity_choose_registration;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_choose_registration);
        Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
		
		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);
		
		Button btnRegisterDriver=(Button)findViewById(R.id.btnRegisterDriver);
		btnRegisterDriver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(choose_registration.this,Register.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		Button btnRegisterPassanger=(Button)findViewById(R.id.btnRegisterPassanger);
		btnRegisterPassanger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(choose_registration.this,Registration_passanger.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choose_registration, menu);
        return true;
    }
}
