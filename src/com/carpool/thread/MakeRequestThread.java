package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class MakeRequestThread extends Thread {
	
	private static String TAG="MakeRequestThread";
	
	public interface MakeRequestThreadInterface
	{
		public void onMakeRequestThreadDataReturned(Boolean isSuccess,String msg);
		public void onMakeRequestThreadErrorReturned();
	}
	private MakeRequestThreadInterface mMakeRequestThreadInterface;
	private String mSource,mDestination,mPickUpTime;
			
	public MakeRequestThread(MakeRequestThreadInterface n, final String source, final String destnation,final String pickUpTime)
	{
		mMakeRequestThreadInterface=n;
		mSource=source;
		mDestination=destnation;
		mPickUpTime=pickUpTime;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("source",mSource);
			obj.put("destination",mDestination);
			obj.put("pickup_time",mPickUpTime);
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("device_id",SharedData.mMySharedPref.getDeviceId());
			
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"CarpoolRequestServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			
			msg=obj.getString("msg");
			mMakeRequestThreadInterface.onMakeRequestThreadDataReturned(resultVal,msg);
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mMakeRequestThreadInterface.onMakeRequestThreadErrorReturned();
		}
	}

}
