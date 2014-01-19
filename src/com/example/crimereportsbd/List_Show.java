package com.example.crimereportsbd;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class List_Show extends Activity {
    /** Called when the activity is first created. */
     public static String previous_page;
	 TextView content1;
		FrameLayout parent;
		private ListView Content_listView;
		  ArrayList<String> Content_List;
		  Activity a=this;
		    
		  public  static int Content_selected_index;
		    

	

	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
	        
	        setContentView(R.layout.activity_reports_list);
	        
	        
	        
	        Content_listView = (ListView) findViewById(R.id.reports_list);
	        
	      
	        
	        
	 Content_List=new ArrayList<String>();
	 
	  for(DataContent D: StaticData.data_content)
      {
      	
      	Content_List.add(D.incident_title);
      	
      }

	        
	       List_view_adapter l=new List_view_adapter(a, getApplicationContext(), Content_List);
	        
	      
	     Content_listView.setAdapter(l);
	  

	        
	        
	      
	      Content_listView.setOnItemClickListener(new OnItemClickListener() {
	        	          
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							
							Content_selected_index=arg2;
						
						
						}
	        	    });

	        
	   
			
			
	      
	        
	     
	         
	      
	    }

}