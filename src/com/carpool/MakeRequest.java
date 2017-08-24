package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carpool.data.SharedData;
import com.carpool.thread.MakeRequestThread;
import com.carpool.thread.MakeRequestThread.MakeRequestThreadInterface;
import com.carpool.thread.SourceDestinationThread;
import com.carpool.thread.SourceDestinationThread.SourceDestinationThreadInterface;
import com.carpool.utils.BaseActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MakeRequest extends BaseActivity implements MakeRequestThreadInterface,SourceDestinationThreadInterface {

	
	EditText etSource;
	EditText etDestination;
	String source="",destination="",pickUpTime="";
	Spinner spDest,spSource;
	TextView pick_up_time;
	private int pHour;
    private int pMinute;
    static final int TIME_DIALOG_ID = 0;
    
	@Override
	public int getLayoutXML() {
		return R.layout.make_request;
	}
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
		btnNext.setText("Logout");
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				SharedData.mMySharedPref.setUserId(0);
				SharedData.mMySharedPref.setUserName("");
				SharedData.mMySharedPref.setPassword("");
				SharedData.mMySharedPref.setUserType("");
				
				Intent i=new Intent(MakeRequest.this,Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});

		//etSource=(EditText)findViewById(R.id.editSource);
		//etDestination=(EditText)findViewById(R.id.editDest);
		spSource=(Spinner)findViewById(R.id.spMSource);
		spDest=(Spinner)findViewById(R.id.spMDest);
		
		/*String[] sourceList={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};		
		List<String> items = new ArrayList<String>(Arrays.asList(sourceList));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MakeRequest.this, android.R.layout.simple_spinner_item, items);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spSource.setAdapter(adapter);
	    
	    String[] DestList={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};		
		
	    List<String> items1 = new ArrayList<String>(Arrays.asList(DestList));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MakeRequest.this, android.R.layout.simple_spinner_item, items1);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spSource.setAdapter(adapter1);*/
	    
	    showWait("Request sending...");
		new SourceDestinationThread(MakeRequest.this).start();
		
		Button btnMakeRequest=(Button)findViewById(R.id.btnMakeReq);
		btnMakeRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					// TODO Auto-generated method stub
				//source=etSource.getText().toString();
				//destination=etDestination.getText().toString();
				source=spSource.getSelectedItem().toString();
				destination=spDest.getSelectedItem().toString();
				pickUpTime=pick_up_time.getText().toString();
				
				if(source.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Source");
					etSource.setText("");
					etSource.requestFocus();
				}
				//if password is blank then check condition
				else if(destination.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Destination.");
					etDestination.setText("");
					etDestination.requestFocus();
				}else if(destination.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Destination.");
					etDestination.setText("");
					etDestination.requestFocus();
				}
				else
				{
					//To check email-Id Validation
						showWait("Request sending...");
						new MakeRequestThread(MakeRequest.this,source,destination,pickUpTime).start();
				}			
			}
			
		});
		pick_up_time =(TextView)findViewById(R.id.etSourceTime);
		Button btnSouceTime = (Button)findViewById(R.id.btnSurceTimer);
		btnSouceTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showDialog(TIME_DIALOG_ID);
			}
		});
    }
    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pHour = hourOfDay;
                pMinute = minute;
                updateDisplay();
                
            }
        };
     
    /** Updates the time in the TextView */
    private void updateDisplay() {
    	String timeSet="";
    	if(pHour>12){
    		pHour -=12;
    		timeSet="PM";
    	}else if(pHour == 0){
    		pHour +=12;
    		timeSet = "AM";
    	}else if(pHour == 12){
    		timeSet="PM";
    	}else
    		timeSet="AM";
    	pick_up_time.setText(
            new StringBuilder()
                    .append(pad(pHour)).append("-")
                    .append(pad(pMinute)).append(timeSet));
    }
     
     
    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
   
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, pHour, pMinute, false);
        }
        return null;
    } 
		private final static int REQUEST_DATA_TRUE=1;
		private final static int REQUEST_DATA_FALSE=2;
		private final static int REQUEST_ERROR=3;
		private final static int SOURCE_DEST_DATA=4;
		private final static int SOURCE_DEST_ERROR=5;

		final Handler mHandler=new Handler()
		{
			public void handleMessage(android.os.Message msg) {
				hideWait();
				switch (msg.what) {
				case REQUEST_DATA_TRUE:
				{
					Toast.makeText(MakeRequest.this,"Add request successful ", Toast.LENGTH_SHORT).show();
					//ShowSimpleDialog("Alert!", "Add request successful ");
					Intent i=new Intent(MakeRequest.this,UserHome.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
					
				}
				break;
				case REQUEST_DATA_FALSE:
				{
					ShowSimpleDialog("Alert!", "Request Failed : "+msg.obj.toString());
				}
				break;

				case REQUEST_ERROR:
				{
					ShowSimpleDialog("Alert!", "App Server not reachable.");
				}
				break;
				
				case SOURCE_DEST_DATA:
				{
					//String[] sourceList={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};		
					List<String> items = new ArrayList<String>(Arrays.asList(SharedData.Source));
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MakeRequest.this, android.R.layout.simple_spinner_item, items);
				    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    spSource.setAdapter(adapter);
				    
				    //String[] DestList={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};		
					List<String> items1 = new ArrayList<String>(Arrays.asList(SharedData.destination));
					ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MakeRequest.this, android.R.layout.simple_spinner_item, items1);
				    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    spDest.setAdapter(adapter1);
				}
					break;
					
				case SOURCE_DEST_ERROR:
				{
					ShowSimpleDialog("Alert!", "App Server not reachable.");
				}
					break;
				}
			}
		};

		
	@Override
	public void onMakeRequestThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(REQUEST_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(REQUEST_DATA_FALSE,msg));
		
	}
	@Override
	public void onMakeRequestThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(REQUEST_ERROR,null));
	}
	@Override
	public void onSourceDestinationThreadDataReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(SOURCE_DEST_DATA, null));
		
	}
	@Override
	public void onSourceDestinationThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(SOURCE_DEST_ERROR,null));
		
	}

    
}
