package com.parse.mealspotting;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class DealListActivity extends ListActivity {

	private DealAdapter dealAdapter;

	// Adapter for the Todos Parse Query
	private ParseQueryAdapter<Deal> mDealAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getListView().setClickable(false);
		getListView().setDividerHeight(4);

		// TODO: 要リファクタリング
	    final String bookId = getIntent().getStringExtra("user_book");

	    if (bookId != null) {
	    	// profile activityからインテント
	    	// 教科書についてのコメントの一覧を表示
	    	// Bookを取得して、取引を検索
	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Textbook");
	    	query.getInBackground(bookId, new GetCallback<ParseObject>() {
				@Override
				public void done(final ParseObject book, com.parse.ParseException e) {
					if (e == null) {

						dealAdapter = new DealAdapter(DealListActivity.this,
								new ParseQueryAdapter.QueryFactory<Deal>() {
									public ParseQuery<Deal> create() {
										ParseQuery query = new ParseQuery("Contact");

										query.whereEqualTo("textBook", book);
										query.whereEqualTo("toUser", ParseUser.getCurrentUser());

										query.orderByDescending("createdAt");

										// 後々fetchのときにつかえるかも
										// query.include("Textbook");

										return query;
									}
								});

						mDealAdapter = dealAdapter;
						setListAdapter(mDealAdapter);

					} else {
						Log.d("texterror", e.getMessage());
					}
				}
			});
	    } else {
	    	// 普通の場合
			dealAdapter = new DealAdapter(this,
					new ParseQueryAdapter.QueryFactory<Deal>() {
						public ParseQuery<Deal> create() {

							// 参考:
							// http://murayama.hatenablog.com/entry/2013/11/30/093741
							List<ParseQuery<Deal>> queries = new ArrayList<ParseQuery<Deal>>();

							queries.add((new ParseQuery("Contact"))
									.whereEqualTo("toUser",
											ParseUser.getCurrentUser()));
							queries.add((new ParseQuery("Contact"))
									.whereEqualTo("fromUser",
											ParseUser.getCurrentUser()));

							ParseQuery<Deal> query = ParseQuery.or(queries);

							query.orderByDescending("createdAt");

							// 後々fetchのときにつかえるかも
							// query.include("Textbook");

							return query;
						}
					});

			mDealAdapter = dealAdapter; // アダプターをクリックリスナーで使用
			setListAdapter(mDealAdapter);
	    }


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_meal_list, menu);
		return true;
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
		Deal deal = mDealAdapter.getItem(position);

		Intent intent = new Intent(this, ParseChatActivity.class);
		intent.putExtra("deal_id", deal.getId());
		intent.putExtra("user", ParseUser.getCurrentUser().getUsername());
		this.startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  // アプリアイコンをタップで戻る
	  if (item.getItemId() == android.R.id.home) {
          finish();
          return true;
	  }
	  return super.onOptionsItemSelected(item);
	}
}
