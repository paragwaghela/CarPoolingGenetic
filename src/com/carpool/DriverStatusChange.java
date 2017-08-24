package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.ChangePasswordThread;
import com.carpool.thread.DriverStatusChangeThread;
import com.carpool.thread.DriverStatusChangeThread.DriverStatusChangeThreadInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class DriverStatusChange extends BaseActivity implements DriverStatusChangeThreadInterface{

	@Override
	public int getLayoutXML() {
		return R.layout.driver_status_change;
	}
	String flag ="false";
	ToggleButton toggle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);
		toggle = (ToggleButton) findViewById(R.id.switchToggle);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	flag="true";
		        	/*showWait();
					new DriverStatusChangeThread(DriverStatusChange.this,flag).start();
		            */// The toggle is enabled
		       
		        } else {
		        	flag="false";
		        	/*showWait();
					new DriverStatusChangeThread(DriverStatusChange.this,flag).start();
		        	*/// The toggle is disabled
		        
		        }
		    }
		    
		});
		Button btnChangeStatus=(Button)findViewById(R.id.btnChangeStatus);
		btnChangeStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showWait();
				new DriverStatusChangeThread(DriverStatusChange.this,flag).start();
				
			}
		});

		Button btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }

    
    private final static int DRIV_STATUS_DATA_TRUE=1;
	private final static int DRIV_STATUS_DATA_FALSE=2;
	private final static int DRIV_STATUS_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case DRIV_STATUS_DATA_TRUE:
			{				
				//SharedData.mMySharedPref.setPassword(newPass);
				
				final Builder builder = new AlertDialog.Builder(DriverStatusChange.this);
				builder.setTitle("Alert!");
				builder.setMessage(msg.obj.toString());
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i=new Intent(DriverStatusChange.this,UserHome.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
						
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			case DRIV_STATUS_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!", msg.obj.toString());
			}
			break;
			case DRIV_STATUS_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};
	@Override
	public void onDriverStatusChangeThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(DRIV_STATUS_DATA_TRUE,msg));
		else
			mHandler.sendMessage(mHandler.obtainMessage(DRIV_STATUS_DATA_FALSE,msg));
	}

	@Override
	public void onDriverStatusChangeThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(DRIV_STATUS_ERROR,null));
	}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_status_change, menu);
        return true;
    }
*/
	@Override
	public void onStart(){
	    super.onStart();
	    
	    toggle.setChecked(SharedData.DriverStatus);
	}


	@Override
	public void onStop(){
	    super.onStop();
	    SharedData.DriverStatus =toggle.isChecked();
	    //setDefaults("DriverStatus", toggle.isChecked(), DriverStatusChange.this);
	}
}
