package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import com.example.crimereportsbd.CommentsDialogFragment.CommentHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewReportActivity extends FragmentActivity implements CommentHandler {
	final ViewReportActivity activity = this;
	Button buttonComment;
	IncidentComment comment;
	LinearLayout newsLin,videoLin;
	TextView textTitle,textDesc,textCat,textDate,newsLinkText,videoLinkText;
	int credibilityCount = 0;
	private GoogleMap map;
	int id;
	ArrayList<NameValuePair> nameValue;
	private ProgressDialog pd;
	CommentsDialogFragment dialog ;
	ImageView isVerified;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_report);
		
		
		Bundle b=getIntent().getExtras();
		id=b.getInt("pos");
		
//		getActionBar().hide();
		initiateMap();
		buttonComment = (Button)findViewById(R.id.button_view_comments);
		
		buttonComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showCommentDialog();			
			
				
			}
		});
		
		textTitle=(TextView)findViewById(R.id.text_view_report_title);
		textDesc=(TextView)findViewById(R.id.text_view_report_description);
		textCat=(TextView)findViewById(R.id.text_view_report_category);
		textDate=(TextView)findViewById(R.id.text_view_report_date);
		newsLinkText=(TextView)findViewById(R.id.hypertext_news_link);
		videoLinkText=(TextView)findViewById(R.id.hypertext_video_link);
		newsLin=(LinearLayout)findViewById(R.id.news_link_holder);
		videoLin=(LinearLayout)findViewById(R.id.video_link_holder);
		isVerified=(ImageView)findViewById(R.id.image_view_verification);
		
		Typeface typeben = Typeface.createFromAsset(getAssets(),"fonts/solaimanlipinormal.ttf");
		
	    SpannableString str;
	    
	    str= AndroidCustomFontSupport.getCorrectedBengaliFormat(StaticData.data_content.get(id).incident_title,typeben, (float) 1.5);
	    textTitle.setText(str);
	    
	    
	    
	    str= AndroidCustomFontSupport.getCorrectedBengaliFormat(StaticData.data_content.get(id).incident_description,typeben, (float) 1);
	    textDesc.setText(str);
	    
	    textCat.setText(StaticData.data_content.get(id).incident_cats.get(0));
	    String date=StaticData.data_content.get(id).incident_date; 
	    StringTokenizer tok=new StringTokenizer(date);
	    while(tok.hasMoreElements())
	    {
	    	date=tok.nextElement().toString();
	    	break;
	    }
	    textDate.setText(date);
	    buttonComment.setText("Comments ("+StaticData.data_content.get(id).comments.size()+")");
	    System.out.println(StaticData.data_content.get(id).media);
	    if(StaticData.data_content.get(id).media!=null)
	    {
	    	ArrayList<IncidentMedia> medias=StaticData.data_content.get(id).media;
	    	System.out.println("Media size"+medias.size());
	    	for(int i=0;i<medias.size();i++)
	    	{
	    		System.out.println("Type: "+medias.get(i).getMedia_type());
	    		if(medias.get(i).getMedia_type()==4)
	    		{
	    			View v=(View)findViewById(R.id.news_link_border);
	    			v.setVisibility(View.VISIBLE);
	    			newsLin.setVisibility(View.VISIBLE);
	    			newsLinkText.setText(medias.get(i).getMedia_content());
	    		}
	    		else if(medias.get(i).getMedia_type()==2)
	    		{
	    			View v=(View)findViewById(R.id.video_link_border);
	    			v.setVisibility(View.VISIBLE);
	    			videoLin.setVisibility(View.VISIBLE);
	    			videoLinkText.setText(medias.get(i).getMedia_content());
	    		}
	    	}
	    }
	    if(StaticData.data_content.get(id).incident_verified==0)
	    {
	    	isVerified.setVisibility(View.GONE);
	    }
		
		
	}
	
	public void showCommentDialog(){
		dialog= new CommentsDialogFragment(this,StaticData.data_content.get(id).comments);
		dialog.show(getSupportFragmentManager(), "Comments");
	}
	
	public void initiateMap()
	{
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map_view_report)).getMap();
		
		if(map==null)
		{
			Toast.makeText(getApplicationContext(), "Maps not shown", Toast.LENGTH_LONG).show();
		}
		else
		{
			LatLng testLatLng=new LatLng(StaticData.data_content.get(id).locations.get(0).getLatitude(), StaticData.data_content.get(id).locations.get(0).getLongitude());
			
			MarkerOptions testOPtion=new MarkerOptions()
				.position(testLatLng)
				.title(StaticData.data_content.get(id).locations.get(0).getLocationName())
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.gmap_pin));
			Marker hamburg = map.addMarker(testOPtion);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(testLatLng, 10));
			
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
			
//			
		}
	}

	@Override
	public void getAddedComment(String name,String email,String desc) {
		// TODO Auto-generated method stub
		pd=ProgressDialog.show(ViewReportActivity.this,"","Adding Comment...",true,false);
		nameValue=new ArrayList<NameValuePair>(4); 
		nameValue.add(new BasicNameValuePair("id", StaticData.data_content.get(id).id+""));
		nameValue.add(new BasicNameValuePair("name", name));
		nameValue.add(new BasicNameValuePair("email", email));
		nameValue.add(new BasicNameValuePair("content", desc));
		MyThread threadObj=new MyThread(handler, nameValue,Settings.URL+"addComment.php");
		threadObj.start();
		
	}
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			JSONObject responseObj=null; 
			pd.dismiss();
			Bundle b=msg.getData();
			System.out.println(b.toString());
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewReportActivity.this);
			builder.setTitle("Success!");
			builder.setMessage("Your Comment has been sent.\n It will be added after verified by Admin");
			// Add the buttons
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			        	   dialog.dismiss();
			        	   
			           }
			       });
			
			

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	};

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.view_report, menu);
//		return true;
//	}

}
