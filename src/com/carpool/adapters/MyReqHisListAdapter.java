package com.carpool.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carpool.R;


public class MyReqHisListAdapter extends ArrayAdapter<HashMap<String, Object>>
{
	ArrayList<HashMap<String, Object>> myData;
	Context con;
	public MyReqHisListAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String, Object>> abc) {

		//let android do the initializing :)
		super(context, textViewResourceId, abc);
		myData=abc;
		con=context;
		Log.d("MyData",""+myData);
	}


	//class for caching the views in a row  
	private class ViewHolder
	{
		TextView tvHReqId,tvHSource,tvHDestination,tvHPickUpTime,tvHStatus;
	}
	
	ViewHolder viewHolder;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
		if(convertView==null)
		{        
			//inflate the custom layout   
			LayoutInflater inflater = LayoutInflater.from(con);
			convertView=inflater.inflate(R.layout.my_request_history, null);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.tvHReqId =(TextView)convertView.findViewById(R.id.tvHReqId);
			viewHolder.tvHSource=(TextView) convertView.findViewById(R.id.tvHSource);
			viewHolder.tvHDestination=(TextView) convertView.findViewById(R.id.tvHDestination);
			viewHolder.tvHPickUpTime=(TextView) convertView.findViewById(R.id.tvHPickUpTime);	
			viewHolder.tvHStatus=(TextView)convertView.findViewById(R.id.tvHStatus);
			//link the cached views to the convertview
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		//set the data to be displayed
		viewHolder.tvHReqId.setText(myData.get(position).get("request_id").toString());
		viewHolder.tvHSource.setText(myData.get(position).get("source").toString());
		viewHolder.tvHDestination.setText(myData.get(position).get("destination").toString());
		viewHolder.tvHPickUpTime.setText(myData.get(position).get("pickuptime").toString());
		viewHolder.tvHStatus.setText(myData.get(position).get("status").toString());
		
		//return the view to be displayed
		return convertView;
	}
}