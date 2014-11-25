package com.parse.mealspotting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQueryAdapter;

public class BookAdapter extends ParseQueryAdapter<Book>  {

	private Context mContext;
	private Book mBook;

	public BookAdapter(Context context, QueryFactory<Book> query) {
		super(context, query);

		// コンテキストを保存
		mContext = context;
	}

	@Override
	public View getItemView(Book book, View v, ViewGroup parent) {

		// テキストオブジェクトを保存
		mBook = book;

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_books, null);
		}

		super.getItemView(book, v, parent);

		ParseImageView bookImage = (ParseImageView) v.findViewById(R.id.text_thumb);
		ParseFile photoFile = book.getParseFile("text_thumb");
		if (photoFile != null) {
			bookImage.setParseFile(photoFile);
			bookImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text_title);
		titleTextView.setText(book.getTitle());
		return v;
	}

}
