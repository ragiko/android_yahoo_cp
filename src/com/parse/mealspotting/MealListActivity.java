package com.parse.mealspotting;

import java.util.Arrays;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class MealListActivity extends ListActivity {

	private BookAdapter textAdapter;
	private int SEARCH_REQUEST_CODE = 1;
	private String departmentStr;

	// Adapter for the Todos Parse Query
	private ParseQueryAdapter<Book> mTexAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			String objectId = currentUser.getObjectId();
			Log.d("login Success", objectId);
		  // do stuff with the user
		} else {
		  // show the signup or login screen
			Log.d("login Error", "Fuck");
		}

		textAdapter = new BookAdapter(this,
				new ParseQueryAdapter.QueryFactory<Book>() {
					public ParseQuery<Book> create() {
						// Here we can configure a ParseQuery to display
						// only top-rated meals.
						ParseQuery query = new ParseQuery("Textbook");
						query.whereContainedIn("department", Arrays.asList("工学部"));
						// query.orderByDescending("rating");
						return query;
					}
				});

		mTexAdapter = textAdapter; // アダプターをクリックリスナーで使用
		setListAdapter(textAdapter);
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

		case R.id.action_search: {
			searchMeal();
			break;
		}

		case R.id.action_new: {
			postBook();
			break;
		}
		
		case R.id.action_deal: {
			dealBook();
			break;
		}
		
		case R.id.action_profile: {
			profile();
			break;
		}

		}
		return super.onOptionsItemSelected(item);
	}

	private void searchMeal() {
		Intent i = new Intent(this, SearchActivity.class);
		startActivityForResult(i, SEARCH_REQUEST_CODE);
	}
	
	private void postBook() {
		Intent i = new Intent(this, PostActivity.class);
		startActivityForResult(i, 0);
	}
	
	private void dealBook() {
		Intent i = new Intent(this, DealListActivity.class);
		startActivityForResult(i, 0);
	}
	
	private void profile() {
		Intent i = new Intent(this, ProfileListActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SEARCH_REQUEST_CODE && resultCode == RESULT_OK) {

			Bundle bundle = data.getExtras();
			departmentStr = bundle.getString("department");

			textAdapter = new BookAdapter(this,
					new ParseQueryAdapter.QueryFactory<Book>() {
						public ParseQuery<Book> create() {
							// Here we can configure a ParseQuery to display
							// only top-rated meals.
							ParseQuery query = new ParseQuery("Textbook");
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Book text = mTexAdapter.getItem(position);

		Intent intent = new Intent(this, BookActivity.class);
		intent.putExtra("text_id", text.getId());
		this.startActivity(intent);
	}
}
