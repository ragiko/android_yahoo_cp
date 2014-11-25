package com.parse.mealspotting;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class SubDealListActivity extends ListActivity {

	private SubDealAdapter dealAdapter;

	// Adapter for the Todos Parse Query
	private ParseQueryAdapter<Message> mDealAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);

		dealAdapter = new SubDealAdapter(this,
				new ParseQueryAdapter.QueryFactory<Message>() {
					public ParseQuery<Message> create() {
						ParseQuery query = new ParseQuery("Contact");
						
						// Include the post data with each comment
						// query.include("User");
						
						return query;
					}
				});

		mDealAdapter = dealAdapter; // アダプターをクリックリスナーで使用
		mDealAdapter.setTextKey("toUser");
		mDealAdapter.setTextKey("fromUser");
		setListAdapter(mDealAdapter);
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
//		Message message = mDealAdapter.getItem(position);
//
//		Intent intent = new Intent(this, BookActivity.class);
//		intent.putExtra("text_id", message.getId());
//		this.startActivity(intent);
	}
}
