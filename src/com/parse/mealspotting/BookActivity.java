package com.parse.mealspotting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class BookActivity extends Activity  implements OnClickListener {
	
	private ParseObject book;
	private String bookId;
	private Button contactButton;
	private ParseUser toUser;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of todos in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Textbook");
			query.whereEqualTo("objectId", bookId);

			try {
				book = query.getFirst();
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
			TextView titleTextView = (TextView) findViewById(R.id.title);
			titleTextView.setText((String) book.get("title"));
			
			// データの送信時に使用する
			toUser = book.getParseUser("user");
			// TODO: 送信できるタイミングでボタンをイネーブル
			contactButton.setEnabled(true);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);	
		
		new RemoteDataTask().execute();
		
		Intent i = getIntent();
		bookId = i.getStringExtra("text_id");
		
		contactButton = (Button) findViewById(R.id.contact_button);
		contactButton.setOnClickListener(this);
		contactButton.setEnabled(false);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final CharSequence[] items = {
				"購入を検討したいと考えています。購入可能かご相談させてください。",
				"品物の状態をより詳しく知りたいです。教えていただけないでしょうか ",
				"品物の受け渡し方法を相談したいです。ご相談できますでしょうか。"
		};
		
		final boolean[] checkedItems = {
				false,
				false,
				false
		};
		
		// チェックボックスのダイアログ
        new AlertDialog.Builder(BookActivity.this)
		.setTitle("test") // メッセージを設定
		.setMultiChoiceItems(items, checkedItems, 
				new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// TODO Auto-generated method stub
						// checkedItemsの内容を更新
						checkedItems[which] = isChecked;
						
						
					}
				})
		.setPositiveButton( // Positiveボタン、リスナーを設定
			"送信",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String body = "";
					
					for (int i = 0; i < items.length; i++) {
						body += checkedItems[i] ? items[i] + "\n" : "";
					}
					
					putContact(body);
				}
			})
		// Negativeボタン、リスナーを設定
		.setNegativeButton(
			"キャンセル",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
	}
	
	// 取引を追加
	// ユーザへのメッセージを追加
	public void putContact(String body) {
		if (toUser != null) {
			
			final String mBody = body;
			
			// Gets the current list of todos in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"Contact");
			query.whereEqualTo("fromUser", ParseUser.getCurrentUser());
			query.whereEqualTo("toUser", toUser);
			query.whereEqualTo("textBook", book);

			try {
				ParseObject deal = query.getFirst(); // 存在しているかどうか？
				Log.d("book", "contact is exist");
				putMessage(deal.getObjectId(), mBody);
			} catch (ParseException e) {
				// 結果がないときにエラーを吐く
				if (e.getMessage() == "no results found for query") {
					final Deal deal = new Deal();
					deal.setToUser(toUser);
					deal.setFromUser(ParseUser.getCurrentUser());
					deal.setBook(book);
					deal.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// success
								putMessage(deal.getObjectId(), mBody);
							} else {
							}
						}
					});
					Log.d("book", e.getMessage());
				}
			}
		}
	}
	
	public void putMessage(String dealId, String body) {
		// Make a new post
		ParseObject message = new ParseObject("Message");
		message.put("dealId", dealId);
		message.put("message", body);
		message.put("userName", ParseUser.getCurrentUser().getUsername());
		message.saveInBackground();
	}
}
