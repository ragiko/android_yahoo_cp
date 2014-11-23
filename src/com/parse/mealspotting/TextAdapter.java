package com.parse.mealspotting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a 
 * bigger preview image, the meal's rating, and a "favorite"
 * star. 
 */

public class TextAdapter extends ParseQueryAdapter<Text> {

	public TextAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Text>() {
			public ParseQuery<Text> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("Text");
				// query.whereContainedIn("rating", Arrays.asList("5", "4"));
				// query.orderByDescending("rating");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Text text, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_texts, null);
		}

		super.getItemView(text, v, parent);

		ParseImageView textImage = (ParseImageView) v.findViewById(R.id.text_thumb);
		ParseFile photoFile = text.getParseFile("text_thumb");
		if (photoFile != null) {
			textImage.setParseFile(photoFile);
			textImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text_title);
		titleTextView.setText(text.getTitle());
		TextView bodyTextView = (TextView) v
				.findViewById(R.id.text_body);
		bodyTextView.setText(text.getBody());
		return v;
	}

}
