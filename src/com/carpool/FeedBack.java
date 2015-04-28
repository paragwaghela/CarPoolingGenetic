package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.FeedBackThread;
import com.carpool.thread.FeedBackThread.FeedBackThreadInterface;
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
import android.widget.RatingBar;
import android.widget.Spinner;

public class FeedBack extends BaseActivity implements FeedBackThreadInterface{

	RatingBar ratingbar1,ratingbar2;  
	@Override
	public int getLayoutXML() {
		return R.layout.feed_back;
	}
	
	Spinner spOverAllScore,spComfortscore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.feed_back);
        Button btnBack=(Button)findViewById(R.id.btnBack);
        ratingbar1=(RatingBar)findViewById(R.id.ratingBar1);
		ratingbar2=(RatingBar)findViewById(R.id.ratingBar2);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
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
				
				Intent i=new Intent(FeedBack.this,Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		/*spComfortscore=(Spinner)findViewById(R.id.spComfortscore);
		
		String[] comfortScore={"1","2","3","4","5"};		
		List<String> items1 = new ArrayList<String>(Arrays.asList(comfortScore));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(FeedBack.this, android.R.layout.simple_spinner_item, items1);
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spComfortscore.setAdapter(adapter1);*/
        
	   /* spOverAllScore=(Spinner)findViewById(R.id.spOverAllScore);
	    String[] allScore={"1","2","3","4","5"};		
		List<String> items2 = new ArrayList<String>(Arrays.asList(allScore));
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FeedBack.this, android.R.layout.simple_spinner_item, items2);
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spOverAllScore.setAdapter(adapter1);*/
	    
	    Button btnSave=(Button)findViewById(R.id.btnSubmit);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comfrot_Score="",over_all_score="",status="",passenger_id="",driver_id="",request_id="";
				
				Bundle b =getIntent().getExtras();
				passenger_id = b.getString("passenger_id");
				driver_id= b.getString("driver_id");
				request_id= b.getString("request_id");
				/*comfrot_Score=spComfortscore.getSelectedItem().toString();
				comfrot_Score=spOverAllScore.getSelectedItem().toString();*/
				comfrot_Score=String.valueOf(ratingbar1.getRating());  
				over_all_score=String.valueOf(ratingbar2.getRating());
				status="close";
				showWait();
				new FeedBackThread(FeedBack.this,driver_id,passenger_id,comfrot_Score,over_all_score,status,request_id).start();	
				
			}
		});
    }
    
    private final static int FEED_BACK_DATA_TRUE=1;
	private final static int FEED_BACK_DATA_FALSE=2;
	private final static int FEED_BACK_ERROR=3;
	
	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case FEED_BACK_DATA_TRUE:
			{
				Intent i=new Intent(FeedBack.this,UserHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
			break;
			case FEED_BACK_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(FeedBack.this);
				builder.setTitle("Alert!");
				builder.setMessage(msg.obj.toString());
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			case FEED_BACK_ERROR:
			{
				hideWait();
				final Builder builder = new AlertDialog.Builder(FeedBack.this);
				builder.setTitle("Alert!");
				builder.setMessage("App Server not reachable.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			}
		}
	};

	@Override
	public void onFeedBackThreadDataReturned(boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(FEED_BACK_DATA_FALSE,msg));
	}

	@Override
	public void onFeedBackThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(FEED_BACK_ERROR,null));
	}

}
