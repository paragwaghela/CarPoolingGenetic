package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.PasseReqDetailData;
import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class GetPassengerReqInfoThread extends Thread {
	
	private static String TAG="GetProfileInfoThread";
	
	public interface GetPassengerReqInfoThreadInterface
	{
		public void onGetPassengerReqInfoThreadDataReturned(Boolean isSuccess,String msg);
		public void onGetPassengerReqInfoThreadErrorReturned();
	}
	private GetPassengerReqInfoThreadInterface mGetPassengerReqInfoThreadInterface;
	
	public GetPassengerReqInfoThread(GetPassengerReqInfoThreadInterface n)
	{
		mGetPassengerReqInfoThreadInterface=n;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			obj.put("role", SharedData.mMySharedPref.getUserType());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"RequestInfoServlet"+urlParams);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				/*String Pdata = obj.getString("data");
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
				*/
				
				SharedData.mPassReqDetailData=new PasseReqDetailData();
				String dataObject = obj.getString("data");
				SharedData.mCarData.doParseJSONData(new JSONObject(dataObject));
				
					mGetPassengerReqInfoThreadInterface.onGetPassengerReqInfoThreadDataReturned(resultVal,null);
				/*}
				else
					mGetPassengerReqInfoThreadInterface.onGetPassengerReqInfoThreadDataReturned(resultVal,"No Request data available.");
					*/
			}
			else
			{
				msg=obj.getString("msg");
				mGetPassengerReqInfoThreadInterface.onGetPassengerReqInfoThreadDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetPassengerReqInfoThreadInterface.onGetPassengerReqInfoThreadErrorReturned();
		}
	}

}
