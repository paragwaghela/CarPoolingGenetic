package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.data.CarData;
//import com.carpool.thread.GetProfileInfoThread.GetCarInfoThreadInterface;
import com.carpool.utils.HttpCalls;

public class GetCarInfoThread extends Thread {
	
	private static String TAG="GetProfileInfoThread";
	
	public interface GetCarInfoThreadInterface
	{
		public void onGetCarInfoThreadDataReturned(Boolean isSuccess,String msg);
		public void onGetCarInfoThreadErrorReturned();
	}
	private GetCarInfoThreadInterface mGetCarInfoThreadInterface;
	
	public GetCarInfoThread(GetCarInfoThreadInterface n)
	{
		mGetCarInfoThreadInterface=n;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("driver_id",SharedData.mMySharedPref.getUserId());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"CarInfoServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				SharedData.mCarData=new CarData();
				String dataObject = obj.getString("data");
				SharedData.mCarData.doParseJSONData(new JSONObject(dataObject));
				mGetCarInfoThreadInterface.onGetCarInfoThreadDataReturned(resultVal,null);
			}
			else
			{
				msg=obj.getString("msg");
				mGetCarInfoThreadInterface.onGetCarInfoThreadDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetCarInfoThreadInterface.onGetCarInfoThreadErrorReturned();
		}
	}

}
