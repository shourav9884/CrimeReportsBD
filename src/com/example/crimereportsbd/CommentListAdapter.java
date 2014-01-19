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

public class CommentListAdapter extends BaseAdapter{
	
	private ArrayList<IncidentComment> commentData;
	private Activity activity;
	
	public CommentListAdapter(Activity activity,ArrayList<IncidentComment> c){
		commentData = c;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentData.size();
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
		TextView nameText,dateText,descText;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.comment_list_row, null);
			
		}
		
		nameText=(TextView)convertView.findViewById(R.id.text_view_comment_name);
		dateText=(TextView)convertView.findViewById(R.id.text_view_report_date);
		descText=(TextView)convertView.findViewById(R.id.text_view_comment_comment);
		
		String date=commentData.get(position).getDate();
	    StringTokenizer tok=new StringTokenizer(date);
	    while(tok.hasMoreElements())
	    {
	    	date=tok.nextElement().toString();
	    	break;
	    }
        Typeface typeben = Typeface.createFromAsset(activity.getAssets(),"fonts/solaimanlipinormal.ttf");
		
	    SpannableString str;
	    
	    str= AndroidCustomFontSupport.getCorrectedBengaliFormat(commentData.get(position).getAuthor(),typeben, (float) 1.2);
	    nameText.setText(str);
	    str= AndroidCustomFontSupport.getCorrectedBengaliFormat(commentData.get(position).getDescription(),typeben, (float) 1);
	    descText.setText(str);
		return convertView;
	}
	
	public class CommentData{
		public String name;
		public String comment;
	}

}
