package com.parse.mealspotting;

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

public class SubDealAdapter extends ParseQueryAdapter<Message>  {

	public SubDealAdapter(Context context, QueryFactory<Message> query) {
		super(context, query);
	}

	@Override
	public View getItemView(Message deal, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_deals, null);
		}

		super.getItemView(deal, v, parent);

		final TextView titleTextView = (TextView) v.findViewById(R.id.deal_book);
		final TextView fromTextView = (TextView) v.findViewById(R.id.deal_from_user);
		final TextView toTextView = (TextView) v.findViewById(R.id.deal_to_user);
		
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
					String s = book.getString("title");
					titleTextView.setText(book.getString("title"));
				} else {
					// something went wrong
					Log.d("texterror", e.getMessage());
				}

			}
		});
		
		// 購買者
		ParseQuery<ParseUser> fromUserQuery = ParseUser.getQuery();
		String fromUserId = deal.getFromUser().getObjectId();
		
		fromUserQuery.getInBackground(fromUserId, new GetCallback<ParseUser>() {
			@Override
			public void done(ParseUser user, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// object will be your game score
					String s = user.getUsername();
					fromTextView.setText(user.getUsername());
				} else {
					// something went wrong
					Log.d("error", e.getMessage());
				}

			}
		});
		
		// 購買者
		ParseQuery<ParseUser> toUserQuery = ParseUser.getQuery();
		String toUserId = deal.getToUser().getObjectId();
		
		toUserQuery.getInBackground(toUserId, new GetCallback<ParseUser>() {
			@Override
			public void done(ParseUser user, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// object will be your game score
					String s = user.getUsername();
					toTextView.setText(user.getUsername());
				} else {
					// something went wrong
					Log.d("error", e.getMessage());
				}

			}
		});

		return v;
	}

}
