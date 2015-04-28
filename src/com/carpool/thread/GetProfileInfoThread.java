package com.carpool.thread;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.carpool.data.CarData;
import com.carpool.data.DriverData;
import com.carpool.data.SharedData;
import com.carpool.data.UserData;
import com.carpool.utils.HttpCalls;
import android.util.Log;

public class GetProfileInfoThread extends Thread {
	
	private static String TAG="GetProfileInfoThread";
	
	public interface GetProfileInfoThreadInterface
	{
		public void onGetProfileInfoThreadDataReturned(Boolean isSuccess,String msg);
		public void onGetProfileInfoThreadErrorReturned();
	}
	private GetProfileInfoThreadInterface mGetProfileInfoThreadInterface;
	
	public GetProfileInfoThread(GetProfileInfoThreadInterface n)
	{
		mGetProfileInfoThreadInterface=n;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role",SharedData.mMySharedPref.getUserType());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"ViewProfileServlet"+urlParams);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				SharedData.mDriverData=new DriverData();
				String dataObject = obj.getString("data");
				SharedData.mDriverData.doParseJSONData(new JSONObject(dataObject));
				//SharedData.mUserData.doParseJSONData(obj.getJSONObject("data"));
				//SharedData.mUserData.base_url=obj.getString("base_pic_url");					
				mGetProfileInfoThreadInterface.onGetProfileInfoThreadDataReturned(resultVal,null);
			}
			else
			{
				msg=obj.getString("msg");
				mGetProfileInfoThreadInterface.onGetProfileInfoThreadDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetProfileInfoThreadInterface.onGetProfileInfoThreadErrorReturned();
		}
	}

}
