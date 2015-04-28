package com.carpool;

import java.util.ArrayList;
import java.util.HashMap;

import com.carpool.adapters.DriverReqListAdapter;
import com.carpool.data.SharedData;
import com.carpool.thread.GetDriverRequestHistoryThread;
import com.carpool.thread.GetDriverRequestHistoryThread.GetDriverRequestHistoryInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class DriverReqList  extends BaseActivity implements GetDriverRequestHistoryInterface{

    
    public int getLayoutXML() {
		return R.layout.driver_req_list;
	}
       
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
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
				
				Intent i=new Intent(DriverReqList.this,Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
    }
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showWait();
		new GetDriverRequestHistoryThread(DriverReqList.this).start();
	}
	
	private final static int REQ_HIS_DATA_TRUE=1;
	private final static int REQ_HIS_DATA_FALSE=2;
	private final static int REQ_HIS_ERROR=3;
	
	ListView lvDrivReq;
	ArrayList<HashMap<String, Object>> mDrivReqHistory;
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			hideWait();
			switch (msg.what)
			{
				case REQ_HIS_DATA_TRUE:
				{
					mDrivReqHistory = new ArrayList<HashMap<String, Object>>();
					for(int i=0;i<SharedData.mDriverReqData.size();i++)
					{
						HashMap<String, Object> temp=new HashMap<String, Object>();
						if(!SharedData.mDriverReqData.get(i).source.equals("null") && !SharedData.mDriverReqData.get(i).destination.equals("null") &&
								!SharedData.mDriverReqData.get(i).pickuptime.equals("null") && !SharedData.mDriverReqData.get(i).status.equals("null"))
						{
							try
							{
								temp.put("source",SharedData.mDriverReqData.get(i).source);
								temp.put("destination",SharedData.mDriverReqData.get(i).destination);
								temp.put("pickuptime",SharedData.mDriverReqData.get(i).pickuptime);
								temp.put("status",SharedData.mDriverReqData.get(i).status);
								mDrivReqHistory.add(temp);
							}
							catch(NullPointerException e){
								e.printStackTrace();
							}
						}
				    }
				
					DriverReqListAdapter mAdapter=new DriverReqListAdapter(DriverReqList.this, android.R.layout.simple_list_item_1,mDrivReqHistory);
					lvDrivReq=(ListView)findViewById(R.id.lvDrivReq);
					lvDrivReq.setAdapter(mAdapter);
					ShowSimpleDialog("Alert!","Data Obtain");
				}
				break;
				case REQ_HIS_DATA_FALSE:
				{
					ShowSimpleDialog("Alert!",msg.obj.toString());
				}
				break;
				case REQ_HIS_ERROR:
				{
					ShowSimpleDialog("Alert!", "App Server not reachable.");
				}
				break;
			}
		}
	};

	@Override
	public void onGetDriverRequestHistoryDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_DATA_FALSE,msg));
	}

	@Override
	public void onGetDriverRequestHistoryErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_ERROR, null));
	}    
}
