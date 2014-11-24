package com.parse.mealspotting;

import java.util.Arrays;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class MealListActivity extends ListActivity implements OnItemClickListener  {

	private ParseQueryAdapter<Meal> mainAdapter;
	private FavoriteMealAdapter favoritesAdapter;
	private TextAdapter textAdapter;
	private int SEARCH_REQUEST_CODE = 1;
	private String departmentStr;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);

		mainAdapter = new ParseQueryAdapter<Meal>(this, Meal.class);
		mainAdapter.setTextKey("title");
		mainAdapter.setImageKey("photo");

		// Subclass of ParseQueryAdapter
		favoritesAdapter = new FavoriteMealAdapter(this);
		
		textAdapter = new TextAdapter(this,
				new ParseQueryAdapter.QueryFactory<Text>() {
					public ParseQuery<Text> create() {
						// Here we can configure a ParseQuery to display
						// only top-rated meals.
						ParseQuery query = new ParseQuery("Text");
						query.whereContainedIn("department", Arrays.asList("工学部"));
						// query.orderByDescending("rating");
						return query;
					}
				});

		// Default view is all meals
		setListAdapter(textAdapter);
		
//		ListView lv = getListView();
//		lv.setTextFilterEnabled(true);
//		lv.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_meal_list, menu);
		return true;
	}

	/*
	 * Posting meals and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_text: {
			textMeal();
			break;
		}

		case R.id.action_search: {
			searchMeal();
			break;
		}
		
		case R.id.action_refresh: {
			updateMealList();
			break;
		}

		case R.id.action_favorites: {
			showFavorites();
			break;
		}

		case R.id.action_new: {
			newMeal();
			break;
		}
		
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateMealList() {
		mainAdapter.loadObjects();
		setListAdapter(mainAdapter);
	}
	

	private void showFavorites() {
		favoritesAdapter.loadObjects();
		setListAdapter(favoritesAdapter);
	}

	private void newMeal() {
		Intent i = new Intent(this, NewMealActivity.class);
		startActivityForResult(i, 0);
	}
	
	private void searchMeal() {
		Intent i = new Intent(this, SearchActivity.class);
		startActivityForResult(i, SEARCH_REQUEST_CODE);
	}
	
	private void textMeal() {
		Intent i = new Intent(this, TextActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SEARCH_REQUEST_CODE && resultCode == RESULT_OK) {
			
			Bundle bundle = data.getExtras();
			departmentStr = bundle.getString("department");
			
			textAdapter = new TextAdapter(this,
					new ParseQueryAdapter.QueryFactory<Text>() {
						public ParseQuery<Text> create() {
							// Here we can configure a ParseQuery to display
							// only top-rated meals.
							ParseQuery query = new ParseQuery("Text");
							query.whereContainedIn("department", Arrays.asList(departmentStr));
							// query.orderByDescending("rating");
							return query;
						}
					});

			// Default view is all meals
			setListAdapter(textAdapter);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, final long id) {
		// TODO Auto-generated method stub
		Log.d("debag", String.valueOf(position));
		
	}
}
