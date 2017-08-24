package com.carpool.thread;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.DriverReqdata;
import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;
import com.carpool.utils.JSONUtils;

public class GetDriverRequestHistoryThread extends Thread {
	private static String TAG="GetDriverRequestHistory";
	public interface GetDriverRequestHistoryInterface
	{
		public void onGetDriverRequestHistoryDataReturned(Boolean isSuccess,String msg);
		public void onGetDriverRequestHistoryErrorReturned();
	}
	private GetDriverRequestHistoryInterface mGetDriverRequestHistoryInterface;
	
	public GetDriverRequestHistoryThread(GetDriverRequestHistoryInterface n)
	{
		mGetDriverRequestHistoryInterface=n;
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
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"RequestHistoryServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				//parse the data
				String Pdata = obj.getString("data");
				JSONArray arr= new JSONArray(Pdata);
				if(arr.length()>0)
				{
					SharedData.mDriverReqData=new ArrayList<DriverReqdata>();
					
					for(int i=0;i<arr.length();i++)
					{
						DriverReqdata data=new DriverReqdata();
						JSONObject object=arr.getJSONObject(i);
						try
						{
							data.doParseJSONData(object);
							SharedData.mDriverReqData.add(data);
						}
						catch(NullPointerException e)
						{
							e.printStackTrace();
						}
						
					}					
					mGetDriverRequestHistoryInterface.onGetDriverRequestHistoryDataReturned(resultVal,null);
				}
				else		
					mGetDriverRequestHistoryInterface.onGetDriverRequestHistoryDataReturned(!resultVal,"No Request data available.");
			}
			else
			{
				msg=obj.getString("msg");
				mGetDriverRequestHistoryInterface.onGetDriverRequestHistoryDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetDriverRequestHistoryInterface.onGetDriverRequestHistoryErrorReturned();
		}
	}
}
