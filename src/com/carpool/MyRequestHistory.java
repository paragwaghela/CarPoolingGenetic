package com.carpool;

import java.util.ArrayList;
import java.util.HashMap;

import com.carpool.adapters.MyReqHisListAdapter;
import com.carpool.data.SharedData;
import com.carpool.thread.GetRequestHistoryThread;
import com.carpool.thread.GetRequestHistoryThread.GetRequestHistoryInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyRequestHistory extends BaseActivity implements GetRequestHistoryInterface{

	
	public int getLayoutXML() {
		return R.layout.my_request_history_list;
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
				
				Intent i=new Intent(MyRequestHistory.this,Login.class);
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
		new GetRequestHistoryThread(MyRequestHistory.this).start();
	}
	
	private final static int REQ_HIS_DATA_TRUE=1;
	private final static int REQ_HIS_DATA_FALSE=2;
	private final static int REQ_HIS_ERROR=3;
	
	ListView lvHistory;
	ArrayList<HashMap<String, Object>> mHistory;
	
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
					mHistory = new ArrayList<HashMap<String, Object>>();
					for(int i=0;i<SharedData.mReqData.size();i++)
					{
						HashMap<String, Object> temp=new HashMap<String, Object>();
						if(!SharedData.mReqData.get(i).source.equals("null") && !SharedData.mReqData.get(i).destination.equals("null") &&
								!SharedData.mReqData.get(i).pickuptime.equals("null") && !SharedData.mReqData.get(i).status.equals("null"))
						{
							try
							{	temp.put("request_id", SharedData.mReqData.get(i).request_id);
								temp.put("source",SharedData.mReqData.get(i).source);
								temp.put("destination",SharedData.mReqData.get(i).destination);
								temp.put("pickuptime",SharedData.mReqData.get(i).pickuptime);
								temp.put("status",SharedData.mReqData.get(i).status);
								mHistory.add(temp);
							}
							catch(NullPointerException e){
								e.printStackTrace();
							}
						}
				    }
				
					MyReqHisListAdapter mAdapter=new MyReqHisListAdapter(MyRequestHistory.this, android.R.layout.simple_list_item_1,mHistory);
					lvHistory=(ListView)findViewById(R.id.lvHistory);
					lvHistory.setAdapter(mAdapter);
					lvHistory.setOnItemClickListener(new OnItemClickListener() {
			            public void onItemClick(AdapterView<?> parent, View view, int position,
			                    long id) {
			            	TextView requestId = (TextView) view.findViewById(R.id.tvHReqId);
			            	Intent i=new Intent(MyRequestHistory.this,DriverReqDetail.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							
							i.putExtra("request_id", requestId.getText().toString());
							//i.putExtra("passaner_id", SharedData.mMySharedPref.getUserId());
							startActivity(i);
			            	// Toast.makeText(getBaseContext(), "item", Toast.LENGTH_LONG).show();
			               
			                
			            }
			        });
					//ShowSimpleDialog("Alert!","Data Obtain");
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
	public void onGetRequestHistoryDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess && msg == null)
			mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_DATA_FALSE,msg));
	}

	@Override
	public void onGetRequestHistoryErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(REQ_HIS_ERROR, null));
	}
}
