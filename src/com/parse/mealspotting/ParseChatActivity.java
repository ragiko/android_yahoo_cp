package com.parse.mealspotting;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

public class ParseChatActivity extends Activity {
	public static final String USER_NAME_KEY = "userName";

	private static final String TAG = ParseChatActivity.class.getName();
	
	// TODO: fix the layout to be able to put 100 here
	private static final int MAX_CHAT_MESSAGES_TO_SHOW = 5;

	private static String username;
	private static String fromUserId;
	private static String dealId;

	private EditText txtMessage;
	private Button btnSend;
	private ListView chatListView;
	private ArrayAdapter<String> adapter;

	private BroadcastReceiver pushReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Just received a push. Let's update the messages");
			receiveMessage();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_hello_world);
		
		Intent intent = getIntent();
		username = intent.getStringExtra("user");
		dealId = intent.getStringExtra("deal_id");
		
		setupUI();

		// http://mycode.snow69it.net/390/
		// あなたのアプリケーションはparseサーバーに通知の準備が出来たことを知らせます
		// チャンネル = グループ名
		// channelの参考: https://www.parse.com/questions/for-push-unique-channel-name-per-user-not-allowed
		PushService.subscribe(this, "room_1" + dealId, ParseChatActivity.class);
		PushService.setDefaultPushCallback(this, ParseChatActivity.class);
		
		receiveMessage();
		registerReceiver(pushReceiver, new IntentFilter("MyAction")); // IntentFilter("MyAction") 他の通知拒否
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (pushReceiver != null) {
			unregisterReceiver(pushReceiver);
		}
	}

	// UIとアダプターのセット
	public void setupUI() {
		txtMessage = (EditText) findViewById(R.id.etMensaje);
		btnSend = (Button) findViewById(R.id.btnSend);
		chatListView = (ListView) findViewById(R.id.chatList);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		chatListView.setAdapter(adapter);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String data = txtMessage.getText().toString();
				ParseObject message = new ParseObject("Message");
				message.put(USER_NAME_KEY, username);
				message.put("dealId", dealId);
				message.put("message", data);
				message.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// メッセージをUIに更新
						receiveMessage();
					}
				});
				createPushNotifications(data);
				txtMessage.setText("");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_meal_list, menu);
		return true;
	}

	// Pushの通知を作成
	// 他のユーザに更新を通知用
	public void createPushNotifications(String message) {
		JSONObject object = new JSONObject();
		try {
			object.put("alert", message);
			object.put("title", "Chat");
			object.put("action", "MyAction");
			
			ParseQuery query = ParseInstallation.getQuery();
			query.whereNotEqualTo(USER_NAME_KEY, username);
			query.whereEqualTo("channels", "room_1" + dealId);
			
			ParsePush pushNotification = new ParsePush();
			pushNotification.setQuery(query);
			pushNotification.setData(object);
			pushNotification.sendInBackground();
		} catch (JSONException e) {
			Log.e(TAG, "Could not parse the push notification", e);
		}
	}

	// DBをメッセージをlistviewに追加
	private void receiveMessage() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.whereEqualTo("dealId", dealId);
		query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					adapter.clear();
					
					StringBuilder builder = new StringBuilder();
					
					for (int i = messages.size() - 1; i >= 0; i--) {
						// 名前: メッセージのstringを作成
						builder.append(messages.get(i).getString(USER_NAME_KEY)
								+ ": " + messages.get(i).getString("message") + "\n");
					}
					addItemstoListView(builder.toString());
				} else {
					Log.d("message", "Error: " + e.getMessage());
				}
			}
		});
	}
	
	public void addItemstoListView(String message) {
		adapter.add(message);
        adapter.notifyDataSetChanged(); // Listviewの更新 (http://d.hatena.ne.jp/tomorrowkey/20100612/1276341096)
        chatListView.invalidate();
    }
}
