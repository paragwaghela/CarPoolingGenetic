package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class DrivReqDetailData {
public String source,destination,pickuptime,status,driver_id,driver_name,driver_contact,Car_no,car_model,request_id;
	
	public void doParseJSONData(final JSONObject obj){
		/*source = JSONUtils.getString(obj, "source");
		destination = JSONUtils.getString(obj, "destination");
		pickuptime = JSONUtils.getString(obj, "pickuptime");
		status= JSONUtils.getString(obj, "status");*/
		driver_id= JSONUtils.getString(obj, "driver_id");
		driver_name= JSONUtils.getString(obj, "driver_name");
		driver_contact= JSONUtils.getString(obj, "contact");
		Car_no= JSONUtils.getString(obj, "car_no");
		car_model= JSONUtils.getString(obj, "car_model");
		//request_id= JSONUtils.getString(obj, "request_id");
	}
}
