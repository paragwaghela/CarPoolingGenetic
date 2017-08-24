package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.data.UserData;
import com.carpool.utils.HttpCalls;
import com.carpool.utils.JSONUtils;

public class GetPassangerProfileInfoThread extends Thread {
	
	private static String TAG="GetProfileInfoThread";
	
	public interface GetPassangerProfileInfoThreadInterface
	{
		public void onGetPassangerProfileInfoThreadDataReturned(Boolean isSuccess,String msg);
		public void onGetPassangerProfileInfoThreadErrorReturned();
	}
	private GetPassangerProfileInfoThreadInterface mGetPassangerProfileInfoThreadInterface;
	
	public GetPassangerProfileInfoThread(GetPassangerProfileInfoThreadInterface n)
	{
		mGetPassangerProfileInfoThreadInterface=n;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role", SharedData.mMySharedPref.getUserType());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"ViewProfileServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				SharedData.mUserData=new UserData();
				String dataObject = obj.getString("data");
				SharedData.mUserData.doParseJSONData(new JSONObject(dataObject));
				//SharedData.mUserData.base_url=obj.getString("base_pic_url");					
				mGetPassangerProfileInfoThreadInterface.onGetPassangerProfileInfoThreadDataReturned(resultVal,null);
			}
			else
			{
				msg=obj.getString("msg");
				mGetPassangerProfileInfoThreadInterface.onGetPassangerProfileInfoThreadDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetPassangerProfileInfoThreadInterface.onGetPassangerProfileInfoThreadErrorReturned();
		}
	}

}
