package com.parse.mealspotting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseQueryAdapter;

public class BookAdapter extends ParseQueryAdapter<Book>  {
	
	ImageLoader imageLoader;

	public BookAdapter(Context context, QueryFactory<Book> query) {
		super(context, query);
		imageLoader = new ImageLoader(context);
	}

	@Override
	public View getItemView(Book book, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_books, null);
		}

		super.getItemView(book, v, parent);

//		ParseImageView bookImage = (ParseImageView) v.findViewById(R.id.text_list_thumb);
//		ParseFile photoFile = book.getParseFile("text_thumb");
//		if (photoFile != null) {
//			bookImage.setParseFile(photoFile);
//			bookImage.loadInBackground(new GetDataCallback() {
//				@Override
//				public void done(byte[] data, ParseException e) {
//					// nothing to do
//				}
//			});
//		}
		
		// Locate the ImageView in singleitemview.xml
        ImageView imgflag = (ImageView) v.findViewById(R.id.text_list_url_thumb);
        imageLoader.DisplayImage(book.getBookThumbUrl(), imgflag);

		TextView titleTextView = (TextView) v.findViewById(R.id.text_list_title);
		titleTextView.setText(book.getTitle());
		
		TextView authorTextView = (TextView) v.findViewById(R.id.text_list_author);
		authorTextView.setText(book.getAuthor());
		
		TextView pablisherTextView = (TextView) v.findViewById(R.id.text_list_publisher);
		pablisherTextView.setText(book.getPublisher());
		
		TextView departmentTextView = (TextView) v.findViewById(R.id.text_list_department);
		departmentTextView.setText(book.getDepertment());
		
		TextView priceTextView = (TextView) v.findViewById(R.id.text_list_price);
		priceTextView.setText(String.valueOf(book.getPrice()));
		
		return v;
	}

}
