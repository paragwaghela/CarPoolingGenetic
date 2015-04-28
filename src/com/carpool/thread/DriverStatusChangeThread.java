package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class DriverStatusChangeThread extends Thread {
	
	private static String TAG="DriverStatusChangeThread";
	
	public interface DriverStatusChangeThreadInterface
	{
		public void onDriverStatusChangeThreadDataReturned(Boolean isSuccess,String msg);
		public void onDriverStatusChangeThreadErrorReturned();
	}
	private DriverStatusChangeThreadInterface mDriverStatusChangeThreadInterface;
	private String mFlag;
			
	public DriverStatusChangeThread(DriverStatusChangeThreadInterface n, final String flag)
	{
		mDriverStatusChangeThreadInterface=n;
		mFlag=flag;
		
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("flag",mFlag);
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role",SharedData.mMySharedPref.getUserType());
			
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"DriverStatusServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			
			msg=obj.getString("msg");
			mDriverStatusChangeThreadInterface.onDriverStatusChangeThreadDataReturned(resultVal,msg);
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mDriverStatusChangeThreadInterface.onDriverStatusChangeThreadErrorReturned();
		}
	}

}
