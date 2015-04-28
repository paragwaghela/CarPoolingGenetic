package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class CarData {
	
	public int id,seats,insurance_yes1_no0;
	public String model,car_no,comfort, insurance;
	
	public void doParseJSONData(final JSONObject obj){
		id = Integer.parseInt((JSONUtils.getString(obj, "car_id")));
		model = JSONUtils.getString(obj, "model");
		car_no = JSONUtils.getString(obj, "car_number");
		comfort = JSONUtils.getString(obj, "comfort");
		seats= Integer.parseInt((JSONUtils.getString(obj, "seats")));
		insurance = JSONUtils.getString(obj, "insurance");
		if(insurance.equals("yes"));
			insurance_yes1_no0 = 1;
		if(insurance.equals("no"))
			insurance_yes1_no0 = 0;
	}
}
