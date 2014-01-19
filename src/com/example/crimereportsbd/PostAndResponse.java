package com.example.crimereportsbd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class PostAndResponse {
	String link;
	HttpClient httpclient;
	HttpPost httpPost;
	ArrayList<NameValuePair> nameValuePairs;
	HttpResponse response;
	String responseStr;
	
	public PostAndResponse(String link)
	{
		this.link=link;	
		httpclient = new DefaultHttpClient();
		httpPost = new HttpPost(this.link);
		
//		httpPost.setEntity(se);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
		System.out.println(this.link);
	}
	public void getData(ArrayList<NameValuePair> pairs)
	{
		nameValuePairs=pairs;
	}
	
	public String getResponse()
	{
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httpPost);
			
			responseStr=EntityUtils.toString(response.getEntity());
			System.out.println("Response "+responseStr);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return responseStr;
		
	}

}
