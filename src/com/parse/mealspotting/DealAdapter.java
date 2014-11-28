package com.parse.mealspotting;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class DealAdapter extends ParseQueryAdapter<Deal>  {

	public DealAdapter(Context context, QueryFactory<Deal> query) {
		super(context, query);
	}

	@Override
	public View getItemView(Deal deal, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_deals, null);
		}

		super.getItemView(deal, v, parent);

		final TextView titleTextView = (TextView) v.findViewById(R.id.deal_book);
		final TextView priceTextView = (TextView) v.findViewById(R.id.deal_price);
		final TextView statusTextView = (TextView) v.findViewById(R.id.deal_status);
		final TextView otherUserIdTextView = (TextView) v.findViewById(R.id.deal_other_user);
		
		// TODO: もっとスマートなfetchを考える
		
		// 本のタイトル
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Textbook");
		String bookId = deal.getBook().getObjectId();
		
		query.getInBackground(bookId, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject book, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// object will be your game score
					titleTextView.setText(book.getString("title"));
					priceTextView.setText(String.valueOf(book.getInt("price")));
				} else {
					// something went wrong
					Log.d("texterror", e.getMessage());
				}

			}
		});
		
//		//　買う側
//		ParseQuery<ParseUser> fromUserQuery = ParseUser.getQuery();
//		String fromUserId = deal.getFromUser().getObjectId();
//		
//		fromUserQuery.getInBackground(fromUserId, new GetCallback<ParseUser>() {
//			@Override
//			public void done(ParseUser user, com.parse.ParseException e) {
//				// TODO Auto-generated method stub
//				if (e == null) {
//					// object will be your game score
//					String s = user.getUsername();
//					fromTextView.setText(user.getUsername());
//				} else {
//					// something went wrong
//					Log.d("error", e.getMessage());
//				}
//
//			}
//		});
//		
//		// 売る側
//		ParseQuery<ParseUser> toUserQuery = ParseUser.getQuery();
//		String toUserId = deal.getToUser().getObjectId();
//		
//		toUserQuery.getInBackground(toUserId, new GetCallback<ParseUser>() {
//			@Override
//			public void done(ParseUser user, com.parse.ParseException e) {
//				// TODO Auto-generated method stub
//				if (e == null) {
//					// object will be your game score
//					String s = user.getUsername();
//					toTextView.setText(user.getUsername());
//				} else {
//					// something went wrong
//					Log.d("error", e.getMessage());
//				}
//
//			}
//		});
		
		// 自分とは違う情報の取得
		// 買う側: fromUserId, 売る側: toUserId
		String fromUserId = deal.getFromUser().getObjectId();
		String toUserId = deal.getToUser().getObjectId();
		final Map<String, String> statusAndUserId = new HashMap<String, String>();
		
		Log.d("to", ParseUser.getCurrentUser().getObjectId());
		Log.d("from", fromUserId);
		
		if (ParseUser.getCurrentUser().getObjectId().equals(fromUserId)) { // 売る側
			statusAndUserId.put("status", "出品");
			statusAndUserId.put("user_id", toUserId);
		} 
		else { // 買う側
			statusAndUserId.put("status", "購入");
			statusAndUserId.put("user_id", fromUserId);
		}
		
		ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
		userQuery.getInBackground(statusAndUserId.get("user_id"), new GetCallback<ParseUser>() {
			@Override
			public void done(ParseUser user, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// object will be your game score
					statusTextView.setText(statusAndUserId.get("status"));
					otherUserIdTextView.setText(user.getUsername());
				} else {
					// something went wrong
					Log.d("error", e.getMessage());
				}

			}
		});
		
		// id
		// idTextView.setText(deal.getId());
		
		// 後で試す
		// http://murayama.hatenablog.com/entry/2013/11/30/093741
//		ParseObject object = results.get(0);
//		object.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//		  public void done(ParseObject object, ParseException e) {
//		    // all fields of the object will now be available here.
//		  }
//		});

		return v;
	}

}
