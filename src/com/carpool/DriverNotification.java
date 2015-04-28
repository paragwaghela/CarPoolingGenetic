package com.carpool;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carpool.adapters.PassangerListAdapter;
import com.carpool.data.SharedData;
import com.carpool.data.passData;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class DriverNotification extends BaseActivity{

	private static String TAG="Driver Notification";
	String passangerName=null,contactNo=null,gender=null,DriverData;
	ListView lvpassData;
	
	@Override
	public int getLayoutXML() {
		return R.layout.driver_notification;
	}
	
	ArrayList<HashMap<String, Object>> mpassData;
	
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

		Bundle b=getIntent().getExtras();
		DriverData = b.getString("DriverData");
		Log.d("DATA",DriverData);
		
		JSONArray jArray;
		try {
			SharedData.mpassData = new ArrayList<passData>();
			JSONObject obj = new JSONObject(DriverData);
			String Pdata = obj.getString("passangerData");
			jArray = new JSONArray(Pdata);
			Log.d("jArray",""+jArray);
			//jArray =obj.getJSONArray("passangerData");
			for(int i=0;i<jArray.length();i++)
			{
				passData data=new passData();
				JSONObject object=jArray.getJSONObject(i);
				try
				{
					data.doParseJSONData(object);
					Log.d("data",""+data);
					SharedData.mpassData.add(data);
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
				}
				
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Log.d("mpassData",""+SharedData.mpassData);
		mpassData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<SharedData.mpassData.size();i++)
		{	Log.d("Here" ,""+SharedData.mpassData.size());
			HashMap<String, Object> temp=new HashMap<String, Object>();
			if(!SharedData.mpassData.get(i).pname.equals("null") && !SharedData.mpassData.get(i).pcontact.equals("null") &&
					!SharedData.mpassData.get(i).psource.equals("null") && !SharedData.mpassData.get(i).pdest.equals("null"))
			{
				try
				{	Log.d("pname",SharedData.mpassData.get(i).pname);
					temp.put("pname",SharedData.mpassData.get(i).pname);
					temp.put("pcontact",SharedData.mpassData.get(i).pcontact);
					temp.put("psource",SharedData.mpassData.get(i).psource);
					temp.put("pdest",SharedData.mpassData.get(i).pdest);
					mpassData.add(temp);
				}
				catch(NullPointerException e){
					e.printStackTrace();
				}
			}
	    }
	
		PassangerListAdapter mAdapter=new PassangerListAdapter(DriverNotification.this, android.R.layout.simple_list_item_1,mpassData);
		lvpassData=(ListView)findViewById(R.id.lvPassData);
		lvpassData.setAdapter(mAdapter);
		/*Button btnNext=(Button)findViewById(R.id.btnNext);
		if(lvpassData.size()>=2)
			btnNext.setVisibility(View.GONE);
		else
			btnNext.setVisibility(View.VISIBLE);
		
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(EmergencyContactList.this,EmergencyContactDetails.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("sos_count", mSOS.size());
				startActivity(i);
			}
		});	*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_notification, menu);
        return true;
    }

    
}
