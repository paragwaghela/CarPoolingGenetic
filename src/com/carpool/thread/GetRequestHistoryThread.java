package com.carpool.thread;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.ReqData;
import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class GetRequestHistoryThread extends Thread {
	
	private static String TAG="GetRequestHistory";
	
	public interface GetRequestHistoryInterface
	{
		public void onGetRequestHistoryDataReturned(Boolean isSuccess,String msg);
		public void onGetRequestHistoryErrorReturned();
	}
	private GetRequestHistoryInterface mGetRequestHistoryInterface;
	
	public GetRequestHistoryThread(GetRequestHistoryInterface n)
	{
		mGetRequestHistoryInterface=n;
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
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"RequestHistoryServlet"+urlParams);
			//result={"result":true,"Data":"[{\"pname\":\"Ruta Londhe\",\"pdest\":\"Swargate Pune\",\"psource\":\"Fatima Nagar Pune\",\"pcontact\":\"8657736576\"},{\"pname\":\"Yamini Lele\",\"pdest\":\"Deccan Gymkhana Pune\",\"psource\":\"Shivajinagar Pune\",\"pcontact\":\"8657736578\"}]"};
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
					SharedData.mReqData=new ArrayList<ReqData>();
					for(int i=0;i<arr.length();i++)
					{
						ReqData data=new ReqData();
						JSONObject object=arr.getJSONObject(i);
						try
						{
							data.doParseJSONData(object);
							SharedData.mReqData.add(data);
						}
						catch(NullPointerException e)
						{
							e.printStackTrace();
						}
						
					}					
					mGetRequestHistoryInterface.onGetRequestHistoryDataReturned(resultVal,null);
				}
				else		
					mGetRequestHistoryInterface.onGetRequestHistoryDataReturned(!resultVal,"No Req data available.");
			}
			else
			{
				msg=obj.getString("msg");
				mGetRequestHistoryInterface.onGetRequestHistoryDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetRequestHistoryInterface.onGetRequestHistoryErrorReturned();
		}
	}

}
