package com.carpool.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.carpool.R;

import android.content.Context;
//import android.provider.Telephony.Mms.Rate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//define your custom adapter
public class SOSListAdapter extends ArrayAdapter<HashMap<String, Object>>
{
	ArrayList<HashMap<String, Object>> myData;
	Context con;
	public SOSListAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String, Object>> abc) {

		//let android do the initializing :)
		super(context, textViewResourceId, abc);
		myData=abc;
		con=context;
	}


	//class for caching the views in a row  
	private class ViewHolder
	{
		TextView tvName,tvContact,tvRelation,tvemail;
	}
	
	ViewHolder viewHolder;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
		if(convertView==null)
		{        
			//inflate the custom layout   
			LayoutInflater inflater = LayoutInflater.from(con);
			convertView=inflater.inflate(R.layout.sos_list_item, null);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.tvName=(TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvRelation=(TextView) convertView.findViewById(R.id.tvRelation);
			viewHolder.tvemail=(TextView) convertView.findViewById(R.id.tvEMail);	
			viewHolder.tvContact=(TextView)convertView.findViewById(R.id.tvContact);
			//link the cached views to the convertview
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		//set the data to be displayed
		viewHolder.tvName.setText(myData.get(position).get("name").toString());
		viewHolder.tvRelation.setText(myData.get(position).get("rel").toString());
		viewHolder.tvemail.setText(myData.get(position).get("email").toString());
		viewHolder.tvContact.setText(myData.get(position).get("mob").toString());
		
		//return the view to be displayed
		return convertView;
	}
}