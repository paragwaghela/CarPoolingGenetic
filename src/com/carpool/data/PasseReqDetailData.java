package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class PasseReqDetailData {
public  String passName,dest,source,contact,passanger_id,pickuptime,status,email;
	
	public void doParseJSONData(final JSONObject obj){
		/*
		dest = JSONUtils.getString(obj, "pdest");
		source = JSONUtils.getString(obj, "psource");
		*/
		passanger_id= JSONUtils.getString(obj, "passanger_id");
		passName = JSONUtils.getString(obj, "passenger_name");
		contact= JSONUtils.getString(obj, "contact");
//		status= JSONUtils.getString(obj, "status");
		email= JSONUtils.getString(obj, "email");
	}
}
