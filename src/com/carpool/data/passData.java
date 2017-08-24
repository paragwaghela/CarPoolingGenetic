package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class passData {
public String pname,pdest,psource,pcontact;
	
	public void doParseJSONData(final JSONObject obj){
		pname = JSONUtils.getString(obj, "pname");
		pdest = JSONUtils.getString(obj, "pdest");
		psource = JSONUtils.getString(obj, "psource");
		pcontact= JSONUtils.getString(obj, "pcontact");
	}
}
