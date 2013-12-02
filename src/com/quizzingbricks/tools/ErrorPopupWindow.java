package com.quizzingbricks.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorPopupWindow {
	
	private Context context;
	
	public ErrorPopupWindow(Context context)	{
		this.context = context;
	}
	
	public void createErrorPopupWindow(String title ,String message)	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    	dialogBuilder.setTitle(title);
    	dialogBuilder.setMessage(message);
    	dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
    	dialogBuilder.show();
	}
}
