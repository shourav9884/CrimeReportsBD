package com.example.crimereportsbd;


import java.util.ArrayList;

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
import androidbangladesh.bengali.support.BengaliUnicodeString;

public class List_view_adapter extends BaseAdapter{

	 private static LayoutInflater inflater=null;
	 private Activity activity;
	 private Context context;
	 private ArrayList<String> cats;
	 public List_view_adapter(Activity a, Context context,ArrayList<String> cat)
	 {
		 this.activity=a;
		 this.context=context;
		 this.cats=cat;
		 inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 ///.imageArray=array;
	 }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cats.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View vi=convertView;
		if(convertView==null)
        {
            
			vi = inflater.inflate(R.layout.list_textview, null);
        }
		TextView tv;
		tv=(TextView)vi.findViewById(R.id.list_text);
		String s=cats.get(position);
		
	    Typeface typeben = Typeface.createFromAsset(activity.getAssets(),"fonts/solaimanlipinormal.ttf");
	       SpannableString str = AndroidCustomFontSupport.getCorrectedBengaliFormat(s,typeben, (float) 0.9);
	        tv.setText(str );
	        
	        
	       
		  

		return vi;
	}

}
