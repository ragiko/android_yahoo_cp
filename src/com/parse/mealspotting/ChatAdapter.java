package com.parse.mealspotting;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<Chat> {
	private LayoutInflater layoutInflater_;

	public ChatAdapter(Context context, int textViewResourceId,
			List<Chat> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		// 特定の行(position)のデータを得る
		Chat item = (Chat) getItem(position);

		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.item_list_messages,
					null);
		}

		TextView textView1 = (TextView) convertView
				.findViewById(R.id.message_user_name);
		textView1.setText(item.getUserName());
		TextView textView2 = (TextView) convertView
				.findViewById(R.id.message_body);
		textView2.setText(item.getMessage());

		return convertView;
	}
}
