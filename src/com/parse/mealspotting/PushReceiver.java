package com.parse.mealspotting;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		String message = extras != null ? extras.getString("com.parse.Data")
				: "";
		JSONObject jObject;
		try {
			jObject = new JSONObject(message);
			Toast toast = Toast.makeText(context, jObject.getString("alert")
					+ jObject.getString("title"), 3000);
			toast.show();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
