package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class DriverReqdata {
	public int requestId;
	public int passenger_id;
public String source,destination,pickuptime,status;
	
	public void doParseJSONData(final JSONObject obj){
		source = JSONUtils.getString(obj, "source");
		destination = JSONUtils.getString(obj, "destination");
		pickuptime = JSONUtils.getString(obj, "pickup_time");
		status= JSONUtils.getString(obj, "status");
		passenger_id = JSONUtils.getInt(obj, "passenger_id");
		requestId = JSONUtils.getInt(obj, "request_id");
	}
}
