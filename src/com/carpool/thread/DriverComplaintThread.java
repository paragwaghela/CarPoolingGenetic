package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;

import com.carpool.utils.HttpCalls;

public class DriverComplaintThread extends Thread {
	
	private static String TAG="DriverComplaintThread";
	
	public interface DriverComplaintThreadInterface
	{
		public void onDriverComplaintThreadDataReturned(Boolean isSuccess,String msg);
		public void onDriverComplaintThreadErrorReturned();
	}
	private DriverComplaintThreadInterface mDriverComplaintThreadInterface;
	private String mPassengerName,mComplaint;
	
	public DriverComplaintThread(DriverComplaintThreadInterface n, final String passengername,String Complaint)
	{
		mDriverComplaintThreadInterface=n;
		mPassengerName=passengername;
		mComplaint=Complaint;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role", SharedData.mMySharedPref.getUserType());
			obj.put("passenger_name",mPassengerName);
			obj.put("complaint",mComplaint);
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"ComplaintServlet"+urlParams);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg=obj.getString("msg");
			mDriverComplaintThreadInterface.onDriverComplaintThreadDataReturned(resultVal,msg);
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mDriverComplaintThreadInterface.onDriverComplaintThreadErrorReturned();
		}
	}

}
