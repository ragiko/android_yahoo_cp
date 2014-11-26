package com.parse.mealspotting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BookSearchResultList extends Activity implements OnItemClickListener {
  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_search_result);

    // インテントからデータを取り出す
    Intent intent = getIntent();
    String[] title = intent.getStringArrayExtra("title");
    String[] author = intent.getStringArrayExtra("author");
    String[] publisher = intent.getStringArrayExtra("publisher");
    String[] imageUrl = intent.getStringArrayExtra("image");

    // リストビューに表示するデータの作成
    List<BookSearchResultData> objects = new ArrayList<BookSearchResultData>();
    for(int i=0; i<title.length; i++) {
      BookSearchResultData item = new BookSearchResultData();
      item.setTitleData(title[i]);
      item.setAuthorData("著者：" + author[i]);
      item.setPublisherData("出版社：" + publisher[i]);
      objects.add(item);
    }

    // アダプターをセット
    BookSearchResultAdapter customAdapater = new BookSearchResultAdapter(this, 0, objects);
    listView = (ListView)findViewById(R.id.book_search_result_listview);
    listView.setAdapter(customAdapater);

    // イベントリスナーを登録
    listView.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, final long id) {
    // 選択されたアイテムの番号をインテント元に返す
    int selectedItemId = (int)id;
    Intent intent = getIntent();
    intent.putExtra("selectedItemId", selectedItemId);
    setResult(RESULT_OK, intent);
    finish();
  }
}
