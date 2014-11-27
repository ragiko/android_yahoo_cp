package com.parse.mealspotting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends Activity {
	
	private Spinner departments;
	private Spinner years;
	// 価格
	private int moreGreaterPrice = 0; // defaultは無料
	private int moreLessPrice = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);	
		
		// 学部
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, DepertmentOfUniversity.list.get("香川大学"),
						android.R.layout.simple_spinner_dropdown_item);
		departments = (Spinner) findViewById(R.id.department_spinner);
		departments.setAdapter(spinnerAdapter);
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.search_price);
		// チェックされているラジオボタンの ID を取得します
        // RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        // ラジオグループのチェック状態が変更された時に呼び出されるコールバックリスナーを登録します
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
                // RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if (checkedId == R.id.radio1) {
                	moreGreaterPrice = 0;
                	moreLessPrice = 500;
                }
            }
        });
		
		// 検索
		Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchActivity.this, MealListActivity.class);
				
				Bundle bundle = new Bundle();
				
				String departmentStr = departments.getSelectedItem().toString();
				bundle.putString("department", departmentStr);
				bundle.putInt("more_greater_price", moreGreaterPrice);
				bundle.putInt("more_less_price", moreLessPrice);
				
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
}
