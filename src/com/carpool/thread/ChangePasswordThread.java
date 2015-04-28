package com.carpool.thread;

import org.json.JSONObject;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

import android.util.Log;

public class ChangePasswordThread extends Thread {
	
	private static String TAG="ChangePasswordThread";
	
	public interface ChangePasswordThreadInterface
	{
		public void onChangePasswordThreadDataReturned(Boolean isSuccess,String msg);
		public void onChangePasswordThreadErrorReturned();
	}
	private ChangePasswordThreadInterface mChangePasswordThreadInterface;
	private String mPassword;
	
	public ChangePasswordThread(ChangePasswordThreadInterface n, final String password)
	{
		mChangePasswordThreadInterface=n;
		mPassword=password;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role", SharedData.mMySharedPref.getUserType());
			obj.put("new_password",mPassword);
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"ChangePasswordServlet"+urlParams);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg=obj.getString("msg");
			mChangePasswordThreadInterface.onChangePasswordThreadDataReturned(resultVal,msg);
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mChangePasswordThreadInterface.onChangePasswordThreadErrorReturned();
		}
	}

}
