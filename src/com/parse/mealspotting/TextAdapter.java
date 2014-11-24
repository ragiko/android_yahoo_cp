package com.parse.mealspotting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class TextAdapter extends ParseQueryAdapter<Text>  {
	
	private Context mContext;
	private Text mText;

	public TextAdapter(Context context, QueryFactory<Text> query) {
		super(context, query);
		
		// コンテキストを保存
		mContext = context;
	}
	
	@Override
	public View getItemView(Text text, View v, ViewGroup parent) {
		
		// テキストオブジェクトを保存
		mText = text;

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
//		TextView bodyTextView = (TextView) v
//				.findViewById(R.id.text_body);
//		bodyTextView.setText(text.getBody());
		
		// Listen for ListView Item Click
//        v.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//            	
//                // Send single item click data to SingleItemView Class
//                Intent intent = new Intent(mContext, TextActivity.class);
//                // Pass all data rank
//                intent.putExtra("text_id", mText.getId());
//                // Start SingleItemView Class
//                mContext.startActivity(intent);
//            }
//        });
		
		return v;
	}

}
