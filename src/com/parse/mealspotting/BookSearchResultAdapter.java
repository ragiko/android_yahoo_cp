package com.parse.mealspotting;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookSearchResultAdapter extends ArrayAdapter<BookSearchResultData> {
	private LayoutInflater layoutInflater_;

	public BookSearchResultAdapter(Context context, int textViewResourceId,
			List<BookSearchResultData> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		BookSearchResultData item = (BookSearchResultData) getItem(position);

		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.book_search_result_row, null);
		}

		TextView titleTextView = (TextView) convertView.findViewById(R.id.book_search_result_title);
		titleTextView.setText(item.getTitleData());
		TextView authorTextView = (TextView) convertView.findViewById(R.id.book_search_result_author);
		authorTextView.setText(item.getAuthorData());
		TextView publisherTextView = (TextView) convertView.findViewById(R.id.book_search_result_publisher);
		publisherTextView.setText(item.getPublisherData());

		return convertView;
	}
}
