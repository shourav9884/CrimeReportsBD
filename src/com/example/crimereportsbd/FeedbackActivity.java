package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends Activity {
	
	Button sendFeedBack;
	EditText nameText,emailText,phoneText,subjectText,messageText;
	String name,email,phone,subject,message;
	ArrayList<NameValuePair> nameValue;
	private ProgressDialog pd;
	
	MyThread threadObj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		init();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.feedback, menu);
//		return true;
//	}
	
	public void init()
	{
		nameText=(EditText)findViewById(R.id.editText_feedback_name);
		emailText=(EditText)findViewById(R.id.editText_feedback_email);
		phoneText=(EditText)findViewById(R.id.editText_feedback_phone);
		subjectText=(EditText)findViewById(R.id.editText_feedback_subject);
		messageText=(EditText)findViewById(R.id.editText_feedback_message);
		
		sendFeedBack=(Button)findViewById(R.id.button_feedback_send);
		sendFeedBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDataFromView();
				
				if(validate().equals("OK"))
				{
					sendFeedBack();
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
					builder.setTitle("Error!");
					builder.setMessage(validate());
					// Add the buttons
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User clicked OK button
					        	   
					           }
					       });
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User cancelled the dialog
					           }
					       });
					

					// Create the AlertDialog
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				
			}
		});
		
	}
	public void getDataFromView()
	{
		name=nameText.getText().toString();
		email=emailText.getText().toString();
		phone=phoneText.getText().toString();
		subject=subjectText.getText().toString();
		message=messageText.getText().toString();
	}
	String validate()
	{
		String errorMess="";
		if(name.equals("")||email.equals("")||phone.equals("")||subject.equals("")||message.equals(""))
		{
			if(name.equals(""))
			{
				errorMess+="Name ";
			}
			if(email.equals(""))
			{
				errorMess+="Email ";
			}
			if(phone.equals(""))
			{
				errorMess+="Phone Number ";
			}
			if(subject.equals(""))
			{
				errorMess+="Subject ";
			}
			if(message.equals(""))
			{
				errorMess+="Message ";
			}
			errorMess+="can not be Empty.";
		}
		if(!isEmailValid(email)&&!email.equals(""))
		{
			errorMess+="\n\rInvalid Email Address.";
		}
		if(errorMess.equals(""))
		{
			return "OK";
		}
		else 
		{
			return errorMess;
		}
		
	}
	
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	
	public void sendFeedBack()
	{
		nameValue=new ArrayList<NameValuePair>(6);
		nameValue.add(new BasicNameValuePair("is_from_android", "true"));
		nameValue.add(new BasicNameValuePair("name", name));
		nameValue.add(new BasicNameValuePair("email", email));
		nameValue.add(new BasicNameValuePair("phone", phone));
		nameValue.add(new BasicNameValuePair("subject", subject));
		nameValue.add(new BasicNameValuePair("message", message));
		
		threadObj=new MyThread(handler, nameValue, "http://www.crimereportsbd.com/web-service/sendFeedback.php");
		
		pd=ProgressDialog.show(FeedbackActivity.this,"","Sending Feedback...",true,false);
		threadObj.start(); 
		
		
	}
	
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			JSONObject responseObj=null;
			AlertDialog.Builder builder=new AlertDialog.Builder(FeedbackActivity.this);
			pd.dismiss();
			Bundle b=msg.getData();
			 
			try {
				responseObj=new JSONObject(b.getString("response"));
				if(responseObj.getString("response_type").equals("Success"))
				{
					builder.setTitle("Success");
					builder.setMessage(responseObj.getString("response"));
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(FeedbackActivity.this,MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	};
	
	

}
