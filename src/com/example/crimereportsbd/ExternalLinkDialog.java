package com.example.crimereportsbd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class ExternalLinkDialog extends DialogFragment{
	
	LinkHandler linkHandler;
	CharSequence link;
	EditText linkText;
	int linkType=0;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View v=inflater.inflate(R.layout.link_dialog, null);
	    builder.setView(v).setTitle("External Link");
	    linkText = (EditText)v.findViewById(R.id.editText_link);
	    
	    // Add action buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   link = linkText.getText().toString();
//	                   System.out.println(link+" edit "+linkText+linkHandler);
	                   linkHandler.setLink((String)link,linkType);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   ExternalLinkDialog.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
	public interface LinkHandler{
		public void setLink(String link,int type);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if(activity instanceof LinkHandler){
			linkHandler = (LinkHandler)activity;
		}
		else {
		      throw new ClassCastException(activity.getClass()
		          + " must implemenet MyListFragment.OnItemSelectedListener");
		    }
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		linkHandler = null;
	}
	
	

}
