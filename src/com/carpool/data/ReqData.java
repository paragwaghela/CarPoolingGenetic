package com.carpool.data;
import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class ReqData {

	public String source,destination,pickuptime,status,request_id;
	
	public void doParseJSONData(final JSONObject obj){
		source = JSONUtils.getString(obj, "source");
		destination = JSONUtils.getString(obj, "destination");
		pickuptime = JSONUtils.getString(obj, "pickup_time");
		status= JSONUtils.getString(obj, "status");
		request_id= JSONUtils.getString(obj, "request_id");
	}
}
