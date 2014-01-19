package com.example.crimereportsbd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;




import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JsonParsing extends Activity {
	public Activity a; 

	Context c=this;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	a=this;
     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
   
    
          
				
		// WebServer Request URL
		String serverURL = "http://www.crimereportsbd.com/web-service/get.php";
		
		ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = mgr.getActiveNetworkInfo();
		if(netInfo==null)
		{
			AlertDialog.Builder dialogue = new AlertDialog.Builder(JsonParsing.this);
			dialogue.setTitle("Error");
			dialogue.setMessage("Internet not available");
			dialogue.show();	
		}
		else
		{
			if(netInfo.isConnected())
			{
				new LongOperation().execute(serverURL);
			}
			else
			{
				AlertDialog.Builder dialogue = new AlertDialog.Builder(JsonParsing.this);
				dialogue.setTitle("Error");
				dialogue.setMessage("Network malfunctioning");
				dialogue.show();
			}
		}
		
		// Use AsyncTask execute Method To Prevent ANR Problem
        
			    
         
    }
     
     
    // Class with extends AsyncTask class
    private class LongOperation  extends AsyncTask<String, Void, Void> {
         
    	// Required initialization
    	
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(JsonParsing.this);
        
        String data =""; 
        int sizeData = 0;  
    
        
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //Start Progress Dialog (Message)
        	  Dialog.setMessage("Please wait..");
              Dialog.show();
           
            try{
            	// Set Request parameter
                data +="&" + URLEncoder.encode("from_mobile", "UTF-8") + "="+"true";
	            	
            } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            
        }
 
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
        	
        	/************ Make Post Call To Web Server ***********/
        	BufferedReader reader=null;
   
	             // Send data 
	            try
	            { 
	              
	               // Defined URL  where to send data
	               URL url = new URL(urls[0]);
	                 
	              // Send POST data request
	   
	              URLConnection conn = url.openConnection(); 
	              conn.setDoOutput(true); 
	              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	              wr.write( data ); 
	              wr.flush(); 
	          
	              // Get the server response 
	               
	              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	              StringBuilder sb = new StringBuilder();
	              String line = null;
	            
		            // Read Server Response
		            while((line = reader.readLine()) != null)
		                {
		                       // Append server response in string
		                       sb.append(line + "\n");
		                }
	                
	                // Append Server Response To Content String 
	               Content = sb.toString();
	            }
	            catch(Exception ex)
	            {
	            	Error = ex.getMessage();
	            }
	            finally
	            {
	                try
	                {
	     
	                    reader.close();
	                }
	   
	                catch(Exception ex) {}
	            }
        	
            /*****************************************************/
            return null;
        }
         
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
             
            // Close progress dialog
        	  
        
            if (Error != null) {
          
            } else {
              
            	
            	
             /****************** Start Parse Response JSON Data *************/
            	
            	String OutputData = "";
                JSONObject jsonResponse;
           
              //  ArrayList<>
                
                
                      
                try {
                      
                     /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                     jsonResponse = new JSONObject(Content); 
                      
                     /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
           
                   String res=jsonResponse.getString("response");
                   String Count=jsonResponse.getString("data_count"); 
                   JSONArray cats=jsonResponse.getJSONArray("cats");
                   System.out.println(cats.toString());
                   for(int i=0;i<cats.length();i++)
                   {
                	   StaticData.CATAGORIES.add(cats.getJSONObject(i).getString("category_title"));
                   }
                   
                   String id = null;
                     
                     
                     JSONArray json =jsonResponse.getJSONArray("data_content");
                     
             			for (int i = 0; i < json.length(); i++) {
     				JSONObject j = json.getJSONObject(i);
     			//	Contents content=new Contents(j);
     				DataContent datacontent=new DataContent(j);
     				//datacontent.incident_verified=j.getInt("incident_verified"); 
//     				Log.d("incident_title", j.toString());
     				datacontent.id=j.getInt("id");
     				datacontent.incident_description=j.getString("incident_description");
     				datacontent.incident_title=j.getString("incident_title");
     				datacontent.incident_dateadd=j.getString("incident_dateadd");
     				datacontent.incident_mode=j.getInt("incident_mode");
     			    datacontent.locale=j.getString("locale");
     			    datacontent.incident_verified=j.getInt("incident_verified");
//     				datacontent.incident_person=j.getString("incident_person");
     				//datacontent.incident_media=j.getJSONObject("incident_media");
     				datacontent.incident_date=j.getString("incident_date");
     				datacontent.incident_datemodify=j.getString("incident_datemodify");
     				datacontent.location_id=j.getInt("location_id");
//     				datacontent.incident_location=j.getJSONArray("incident_location").;
     				
     				JSONObject locs=j.getJSONObject("incident_location");
     				ArrayList<IncidentLocation> inciLocs=new ArrayList<IncidentLocation>();
     				
 					IncidentLocation locTest=new IncidentLocation();
 					locTest.setLatitude(locs.getDouble("latitude"));
 					locTest.setLongitude(locs.getDouble("longitude"));
 					locTest.setLocationName(locs.getString("location_name"));
 					
 					inciLocs.add(locTest);
 					
 					ArrayList<IncidentMedia> media=new ArrayList<IncidentMedia>();
 					JSONArray mediArr=j.getJSONArray("incident_media");
 					for(int m=0;m<mediArr.length();m++)
 					{
 						IncidentMedia me=new IncidentMedia();
 						me.setMedia_type(mediArr.getJSONObject(m).getInt("media_type"));
 						me.setMedia_content(mediArr.getJSONObject(m).getString("media_link"));
 						media.add(me);
 					}
     				datacontent.media=media;
 					
 					JSONArray in_cats=j.getJSONArray("incident_catagory");
 					ArrayList<String> final_cats=new ArrayList<String>();
 					for(int m=0;m<in_cats.length();m++)
 					{
 						String cat=in_cats.getJSONObject(m).getString("category_title");
 						final_cats.add(cat);
 					}
 					
 					JSONArray in_comments=j.getJSONArray("incident_comments");
 					System.out.println(in_comments.toString());
 					ArrayList<IncidentComment> tmpComments=new ArrayList<IncidentComment>();
 					for(int m=0;m<in_comments.length();m++)
 					{
 						IncidentComment c=new IncidentComment();
 						c.setAuthor(in_comments.getJSONObject(m).getString("comment_author"));
 						c.setDescription(in_comments.getJSONObject(m).getString("comment_description"));
 						c.setDate(in_comments.getJSONObject(m).getString("comment_date"));
 						
 						tmpComments.add(c);
 						
 						
 					}
 					
 					datacontent.comments=tmpComments;
 						
     				
     				datacontent.incident_cats=final_cats;
     				datacontent.locations=inciLocs;
     				
     				datacontent.incident_dateadd_gmt=j.getString("incident_dateadd_gmt");
     				datacontent.id=j.getInt("id");
     				datacontent.incident_active=j.getInt("incident_active");
     				datacontent.incident_zoom=j.getInt("incident_zoom");
     			//	datacontent.user_id=j.getInt("user_id");
     				datacontent.incident_alert_status=j.getInt("incident_alert_status");
     				datacontent.form_id=j.getInt("form_id");
     				
     				
     				
     				StaticData.data_content.add(datacontent);
     					
     				Log.d("incident_title", datacontent.incident_title);
     				
     			
             			}

                     
                  /*********** Process each JSON Node ************/
         			Dialog.dismiss();	
             			
                    Intent intent=new Intent(JsonParsing.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    a.startActivity(intent);
                    a.finish();
                     
                      
                 } catch (JSONException e) {
          
                     e.printStackTrace();
                 }
  
                 
             }
        }
         
    }
	
}
