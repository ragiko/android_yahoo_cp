package com.parse.mealspotting;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ProfileListActivity extends ListActivity {

	private BookAdapter textAdapter;
	private int SEARCH_REQUEST_CODE = 1;
	private String departmentStr;

	// Adapter for the Todos Parse Query
	private ParseQueryAdapter<Book> mTexAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);

		textAdapter = new BookAdapter(this,
				new ParseQueryAdapter.QueryFactory<Book>() {
					public ParseQuery<Book> create() {
						ParseQuery query = new ParseQuery("Textbook");
						query.whereEqualTo("user", ParseUser.getCurrentUser());
						query.orderByDescending("createdAt");
						return query;
					}
				});

		mTexAdapter = textAdapter; // アダプターをクリックリスナーで使用
		setListAdapter(textAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	/*
	 * Posting meals and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
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
		
		final Book book = mTexAdapter.getItem(position);
		
		// アイテムをタップでメニューを表示する
		final String[] menu = new String[2];
		menu[0] = ("教科書の詳細を見る");
		menu[1] = ("教科書についてのコメント一覧を見る");

		// ダイアログの生成
		new AlertDialog.Builder(ProfileListActivity.this)
		// アイテムとリスナーを設定
				.setItems(menu, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case (0):
							// 教科書の詳細を見る
							Intent intent = new Intent(ProfileListActivity.this, BookActivity.class);
							intent.putExtra("text_id", book.getId());
							ProfileListActivity.this.startActivity(intent);
							break;
						case (1):
							// 教科書についてのコメント一覧を見る
							Intent i = new Intent(ProfileListActivity.this, DealListActivity.class);
							i.putExtra("user_book", book.getObjectId());
							ProfileListActivity.this.startActivity(i);
							break;
						}
					}
				}).show();

		
	}
}
