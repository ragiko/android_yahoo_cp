package com.parse.mealspotting;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class TextActivity extends Activity {
	// TODO: idからbackgroundでゲットしてくる
	
	private ParseObject text;
	private String textId;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of todos in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Text");
			query.whereEqualTo("objectId", textId);

			try {
				text = query.getFirst();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.d("title", (String) text.get("title"));
			TextView titleTextView = (TextView) findViewById(R.id.title);
			titleTextView.setText((String) text.get("title"));
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);	
		
		Intent i = getIntent();
		textId = i.getStringExtra("text_id");
		
		
		
		new RemoteDataTask().execute();
	}
}
