package com.parse.mealspotting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchActivity extends Activity {
	
	private Spinner departments;
	private Spinner years;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);	
		
		// 学部
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.departments,
						android.R.layout.simple_spinner_dropdown_item);
		departments = (Spinner) findViewById(R.id.department_spinner);
		departments.setAdapter(spinnerAdapter);
		
		// 年度
		ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter
				.createFromResource(this, R.array.years,
						android.R.layout.simple_spinner_dropdown_item);
		years = (Spinner) findViewById(R.id.year_spinner);
		years.setAdapter(yearAdapter);
		
		// 検索
		Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchActivity.this, MealListActivity.class);
				
				Bundle bundle = new Bundle();
				String departmentStr = departments.getSelectedItem().toString();
				bundle.putString("department", departmentStr);
				
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
}
