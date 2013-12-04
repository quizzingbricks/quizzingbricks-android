package com.quizzingbricks.tools;

import android.app.Activity;
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
    	setTitleAndMessage(title, message);
    	dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
    	dialogBuilder.show();
	}
	
	public void createPopupWindow(String title ,String message, final Intent intent)	{
    	setTitleAndMessage(title, message);
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
	
	public void createPopupWindowWithResult(String title, String message, final int result, final Intent intent)	{
		setTitleAndMessage(title, message);
    	final Activity innerContext = (Activity) this.context;
    	dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				innerContext.setResult(result, intent);
				innerContext.finish();
			}
		});
    	dialogBuilder.show();
	}
	
	private void setTitleAndMessage(String title, String message)	{
		dialogBuilder.setTitle(title);
    	dialogBuilder.setMessage(message);
	}
}
