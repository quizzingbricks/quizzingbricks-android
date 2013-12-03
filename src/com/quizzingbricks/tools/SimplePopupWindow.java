package com.quizzingbricks.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class SimplePopupWindow {
	
	private Context context;
	private AlertDialog.Builder dialogBuilder;
	
	public SimplePopupWindow(Context context)	{
		this.context = context;
		this.dialogBuilder = new AlertDialog.Builder(context);
	}
	
	public void createPopupWindow(String title ,String message)	{
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
	
	public void createPopupWindowWithActivityChange(String title ,String message, final Intent intent)	{
    	dialogBuilder.setTitle(title);
    	dialogBuilder.setMessage(message);
    	final Context innerContext = this.context;
    	dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				innerContext.startActivity(intent);
			}
		});
    	dialogBuilder.show();
	}
}
