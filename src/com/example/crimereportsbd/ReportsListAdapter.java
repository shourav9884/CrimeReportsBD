package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReportsListAdapter extends BaseAdapter {
	
	Activity activity;
	LayoutInflater inflater = null;
	ArrayList<DataContent> data;
	
	public ReportsListAdapter(Activity activity,ArrayList<DataContent> data){
		this.activity = activity;
		this.data=data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView reportTitle,catText,dateText;
		if(convertView == null){
			
//			System.out.println(position+" "+StaticData.data_content.get(position).incident_title+" "+StaticData.data_content.size());
			LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.reports_list_row, null);
			
			
		        
		}
		if(data.get(position).incident_verified==0)
		{
			convertView.setBackgroundResource(R.drawable.report_list_back_unverified);
		}
		else
		{
			convertView.setBackgroundResource(R.drawable.report_list_back_verified);
		}
		
		reportTitle=(TextView)convertView.findViewById(R.id.text_report_title);
		catText=(TextView)convertView.findViewById(R.id.text_report_category);
		
		Typeface typeben = Typeface.createFromAsset(activity.getAssets(),"fonts/solaimanlipinormal.ttf");
		
	    SpannableString str;
	    
	    str= AndroidCustomFontSupport.getCorrectedBengaliFormat(data.get(position).incident_title,typeben, (float) 0.9);
	    reportTitle.setText(str);
	    
	    catText.setText(data.get(position).incident_cats.get(0));
	    
	    
	    dateText=(TextView)convertView.findViewById(R.id.text_report_date);
	    
	    String date=data.get(position).incident_date;
	    StringTokenizer tok=new StringTokenizer(date);
	    while(tok.hasMoreElements())
	    {
	    	date=tok.nextElement().toString();
	    	break;
	    }
	    dateText.setText(date);
		
		
		return convertView;
	}

}
