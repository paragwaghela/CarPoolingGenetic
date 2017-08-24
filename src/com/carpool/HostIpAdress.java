package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.utils.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class HostIpAdress extends  BaseActivity {

	@Override
	public int getLayoutXML() {
		return R.layout.host_ip_adress;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);
		
        Button btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);
        
       
        
        Button btnSet=(Button)findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String serverAddress="";
				EditText hostId=(EditText)findViewById(R.id.etHostId);
				serverAddress = hostId.getText().toString();
				SharedData.SERVER_URL="http://"+serverAddress+":8081/GeneticCarpooling/";
				
				Intent i=new Intent(HostIpAdress.this,SplashScreen.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			};
		});
      
    }

    
}
