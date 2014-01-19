package com.example.crimereportsbd;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataContent {
		
	public class Inc_location{
		
		double longitude;
		double latitude;
		ArrayList<String> location_name=new ArrayList<String>();
		
		public Inc_location(){
			
			
		}
		
	}
	IncidentPerson person;
	int incident_verified;
	String incident_description;
	String incident_title;
	String incident_dateadd;
	int incident_mode;
	ArrayList<String> incident_cats;
	String locale;
	
	ArrayList<IncidentLocation> locations;
	ArrayList<IncidentMedia> media;
	
	ArrayList<IncidentComment> comments;
	String incident_date;
	String incident_datemodify;
	int location_id;
	
	String incident_dateadd_gmt;
	int id;
	int incident_active;
	int incident_zoom;
	int user_id;
	int incident_alert_status;
	int form_id;
	String catagory_title;
	
	public DataContent(JSONObject j) throws JSONException{
		
		
		/*this.incident_verified=j.getInt("incident_verified");
		this.incident_description=j.getString("incident_description");
		this.incident_title=j.getString("incident_title");
		this.incident_dateadd=j.getString("incident_dateadd");
		this.incident_mode=j.getInt("incident_mode");
		this.incident_category=j.getJSONObject("incident_category");
		this.locale=j.getString("locale");
		this.incident_person=j.getString("incident_person");
		this.incident_media=j.getJSONObject("incident_media");
		this.incident_date=j.getString("incident_date");
		this.incident_datemodify=j.getString("incident_datemodify");
		this.location_id=j.getInt("location_id");
		this.incident_location=j.getJSONObject("incident_location");
		this.incident_dateadd_gmt=j.getString("incident_dateadd_gmt");
		this.id=j.getInt("id");
		this.incident_active=j.getInt("incident_active");
		this.incident_zoom=j.getInt("incident_zoom");
		this.user_id=j.getInt("user_id");
		this.incident_alert_status=j.getInt("incident_alert_status");
		this.form_id=j.getInt("form_id");*/
		
		//Log.d("incident_title", incident_title);
	//	Log.d("incident_description", incident_description);
		//	Log.d("incident_category", incident_category.getString("category_title"));
			//Log.d("incident_location: longitude", incident_location.getString("longitude"));
			//Log.d("incident_location: latitude", incident_location.getString("latitude"));
		
		
		
		
	}

}




