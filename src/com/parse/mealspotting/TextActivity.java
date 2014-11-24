package com.parse.mealspotting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TextActivity extends Activity {
	// TODO: idからbackgroundでゲットしてくる
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);	
		
		Intent i = getIntent();
		String textId = i.getStringExtra("text_id");
		Log.d("a", textId);
	}
}
