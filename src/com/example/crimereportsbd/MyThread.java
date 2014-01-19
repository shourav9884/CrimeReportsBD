package com.example.crimereportsbd;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.NameValueTable;

public class MyThread extends Thread {
	Handler handler;
	ArrayList<NameValuePair> nameValue;
	PostAndResponse postObj;
	String response;
	String link;
	public MyThread(Handler handler,ArrayList<NameValuePair> nameValue,String link)
	{
		this.handler=handler;
		this.nameValue=nameValue;
		this.link=link;
		postObj=new PostAndResponse(this.link);
	}
	public void run()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postObj.getData(nameValue);
		
		response=postObj.getResponse();
//		System.out.println(response);
		Message msg=Message.obtain();
		Bundle b=new Bundle();
		b.putString("response", response);
		msg.setData(b);
		
		
		
		handler.sendMessage(msg);
	}
}
