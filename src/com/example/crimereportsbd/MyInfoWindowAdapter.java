package com.example.crimereportsbd;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter {

	LayoutInflater inflater = null;

    // Context is most likely the map hosting activity. 
    // We need this so we get access to the Asset Manager
    Context context = null;
    Activity a;

    public MyInfoWindowAdapter(LayoutInflater inflater, Context context,Activity a) {
        this.inflater = inflater;
        this.context = context;
        this.a=a;
    }
	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		View popup = inflater.inflate(R.layout.info_test, null);

        TextView tv = (TextView) popup.findViewById(R.id.info_title);

        

        // We load a typeface by using the static createFromAssets method
        // and provide the asset manager 
        // and a path to the file within the assets folder
        Typeface typeben = Typeface.createFromAsset(a.getAssets(),"fonts/solaimanlipinormal.ttf");
	    SpannableString str = AndroidCustomFontSupport.getCorrectedBengaliFormat(arg0.getTitle(),typeben, (float) 1);
       
        // then set the TextViews typeface
        tv.setText(str);

        return (popup);
	}

}
