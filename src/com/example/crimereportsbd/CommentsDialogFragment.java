package com.example.crimereportsbd;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class CommentsDialogFragment extends DialogFragment{
	
	ListView commentListView;
	Button addComment;
	Button doneComment;
	LinearLayout addCommentSection;
	Activity activity;
	CommentListAdapter commentAdapter;
	EditText nameText,emailText,descText;
	CommentHandler commentHandler;
	ArrayList<IncidentComment> comments;
	LinearLayout noCommentsLayout;
	
	public CommentsDialogFragment(Activity activity,ArrayList<IncidentComment> c){
		this.activity = activity;
		this.comments=c;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View view = inflater.inflate(R.layout.comments_dialog_layout, null);
	    builder.setView(view).setTitle("Comments");
	    
	    nameText=(EditText)view.findViewById(R.id.editText_comment_name);
	    emailText=(EditText)view.findViewById(R.id.editText_comment_email);
	    descText=(EditText)view.findViewById(R.id.editText_comment_description);
	    doneComment=(Button)view.findViewById(R.id.button_comment_done);
	    addCommentSection = (LinearLayout)view.findViewById(R.id.layout_add_comment);
	    noCommentsLayout=(LinearLayout)view.findViewById(R.id.no_comment_layout);
	    addComment = (Button)view.findViewById(R.id.button_add_comment);
	    addComment.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addCommentSection.setVisibility(View.VISIBLE);
			}
		});
	    
	    commentListView = (ListView)view.findViewById(R.id.listView_comments);
	    if(comments.size()>0)
	    {
		    commentAdapter = new CommentListAdapter(activity,comments);
		    commentListView.setAdapter(commentAdapter);
	    }
	    else
	    {
	    	noCommentsLayout.setVisibility(View.VISIBLE);
	    	commentListView.setVisibility(View.GONE);
	    }
	    
	    doneComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commentHandler.getAddedComment(nameText.getText().toString(), emailText.getText().toString(), descText.getText().toString());				
			}
		});
	    
	    // Add action buttons    
	    return builder.create();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if(activity instanceof CommentHandler){
			commentHandler = (CommentHandler)activity;
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		commentHandler = null;
		
	}
	
	public static interface CommentHandler{
		
		public void getAddedComment(String name,String email,String description);
	}
	
	
		
}
