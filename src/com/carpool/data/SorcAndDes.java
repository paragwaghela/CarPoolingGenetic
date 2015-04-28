package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class SorcAndDes {

	String SourceAndDest;
	public void doParseJSONData(final JSONObject obj){
		SourceAndDest = JSONUtils.getString(obj, "SourceAndDest");
	}
}
