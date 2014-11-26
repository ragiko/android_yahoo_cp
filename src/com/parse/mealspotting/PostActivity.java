package com.parse.mealspotting;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 *
 * 出品情報投稿画面のアクティビティ
 */
public class PostActivity extends Activity {
	//インテントが返って来た時の判別コード
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CAMERA = 1;
	private static final int REQUEST_BOOKLIST = 2;

	// View
	private Spinner changeDepSpinner;
	private Button searchButton, barcodeButton;
  private Button cameraButton, galleryButton, submitButton;
  private TextView univTextView;
  private EditText searchEditText, detailEditText, priceEditText;
  private NumberPicker yearPicker;
  private Dialog progressDialog;

  //日時・時刻を取得するためのインスタンス
  private Calendar obj_cd = Calendar.getInstance();

  private Bitmap img;
  private ParseUser user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//meal = new Meal();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_post);
		findViews();

		// ボタンにクリックリスナーを登録
		searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 書籍を検索
        String keyword = searchEditText.getText().toString();

        // APIのURLを生成
        String endpoint = (String)getText(R.string.rakuten_api_endpoint);
        String appId = (String)getText(R.string.rakuten_api_appid);
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.encodedAuthority(endpoint);
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("title", keyword);
        uriBuilder.appendQueryParameter("applicationId", appId);
        String uriStr = uriBuilder.toString();
        Log.d("OK", uriStr);

        HttpGetTask task = new HttpGetTask(
            PostActivity.this,
            uriStr,

            // タスク完了時に呼ばれるUIのハンドラ
            new HttpGetHandler() {
              @Override
              public void onGetCompleted(String response) {
                // JSONをパース
                try {
                  JSONObject result = new JSONObject(response);
                  JSONArray itemArray = result.getJSONArray("Items");
                  int itemNum = itemArray.length();
                  if(itemNum != 0) {
                    String[] titleArray = new String[itemNum];
                    String[] authorArray = new String[itemNum];
                    String[] publisherArray = new String[itemNum];
                    String[] imageUrlArray = new String[itemNum];
                    for(int i=0; i<itemNum; i++) {
                      JSONObject jsonObject = itemArray.getJSONObject(i);
                      JSONObject jsonObjectItem = jsonObject.getJSONObject("Item");
                      titleArray[i] = jsonObjectItem.getString("title");
                      authorArray[i] = jsonObjectItem.getString("author");
                      publisherArray[i] = jsonObjectItem.getString("publisherName");
                      imageUrlArray[i] = jsonObjectItem.getString("mediumImageUrl");
                    }
                    // 書籍選択画面にインテント
                    Intent newIntent = new Intent(getApplicationContext(), BookSearchResultList.class);
                    newIntent.putExtra("title", titleArray);
                    newIntent.putExtra("author", authorArray);
                    newIntent.putExtra("publisher", publisherArray);
                    newIntent.putExtra("imagel", imageUrlArray);
                    startActivityForResult(newIntent, REQUEST_BOOKLIST);
                  } else {
                    // 一件もヒットしなかった場合はメッセージを表示
                    Toast.makeText(PostActivity.this, "書籍が見つかりませんでした。", Toast.LENGTH_LONG).show();
                  }
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }

              @Override
              public void onGetFailed(String response) {
                Toast.makeText(
                    getApplicationContext(),
                    "エラーが発生しました。",
                    Toast.LENGTH_LONG
                    ).show();
              }
            }
          );
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      }
		});

		barcodeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // バーコードから書籍を検索

      }
    });

		cameraButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // カメラで撮影して写真をアップする
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent2, REQUEST_CAMERA);   // カメラへインテントを飛ばす
      }
    });

		galleryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // ギャラリーから写真を選択してアップする
        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.setType("image/*");
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent1, REQUEST_GALLERY);   // ギャラリーへインテントを飛ばす
      }
    });

		submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new RemoteDataTask().execute();
      }
		});

		// NumberPickerの設定
		int presentYear = obj_cd.get(Calendar.YEAR);
		int startYear = presentYear - 10;
		yearPicker.setMaxValue(presentYear);
		yearPicker.setMinValue(startYear);
	}

	// Viewの取得
	private void findViews() {
	  // ボタンのViewを取得
    changeDepSpinner = (Spinner)findViewById(R.id.spinner_dep_post);
    searchButton = (Button)findViewById(R.id.button_search_post);
    barcodeButton = (Button)findViewById(R.id.button_barcode_post);
    cameraButton = (Button)findViewById(R.id.button_camera_post);
    galleryButton = (Button)findViewById(R.id.button_gallery_post);
    submitButton = (Button)findViewById(R.id.button_submit_post);

    // テキストビューのViewを取得
    univTextView = (TextView)findViewById(R.id.textview_university);

    // エディットテキストのViewを取得
    searchEditText = (EditText)findViewById(R.id.edittext_search_post);
    detailEditText = (EditText)findViewById(R.id.edittext_detail_post);
    priceEditText = (EditText)findViewById(R.id.edittext_price_post);

    // ナンバーピッカーのViewを取得
    yearPicker = (NumberPicker)findViewById(R.id.numpicker_year_post);
	}

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
    // Override this method to do custom remote calls
    protected Void doInBackground(Void... params) {

      // EditTextから情報を取得する
      String detail = detailEditText.getText().toString();
      int price = Integer.parseInt(priceEditText.getText().toString());
      int year = yearPicker.getValue();

      // Parseに保存するために画像をbyte[]に変換する
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      img.compress(Bitmap.CompressFormat.JPEG, 100, bos);
      byte[] byteData = bos.toByteArray();
      ParseFile photoFile = new ParseFile("meal_photo.jpg", byteData);

      // Parseに保存する
      user = ParseUser.getCurrentUser();
      Book book = new Book();
      book.setUser(user);
      book.setUniversity("岐阜大学");
      book.setDepertment("工学部");
      book.setTitle("線形代数");
      book.setAuthor("A");
      book.setBody(detail);
      book.setBookThumb(photoFile);
      book.setPicture(photoFile);
      book.setPrice(price);
      book.setYear(year);

      try {
        book.save();
      } catch(ParseException e) {
      }

      return null;
    }

    @Override
    protected void onPreExecute() {
      PostActivity.this.progressDialog = ProgressDialog.show(PostActivity.this, "", "投稿中...", true);
      super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void result) {
      Log.d("OK", "post完了");

      // プログレスダイアログを消す
      PostActivity.this.progressDialog.dismiss();
    }
  }

	//インテント先から結果が返ってきたときの処理
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK) {
       ImageView imgView = (ImageView)findViewById(R.id.image_selected);
       if(requestCode == REQUEST_GALLERY) {  // ギャラリーで画像が選択された
         try {
           InputStream in = getContentResolver().openInputStream(data.getData());
           img = BitmapFactory.decodeStream(in);
           in.close();
           imgView.setImageBitmap(img);
         } catch(Exception e) {

         }
       } else if(requestCode == REQUEST_CAMERA) {  // カメラで写真が撮影された
         img = (Bitmap)data.getExtras().get("data");  // カメラで撮影された写真を取得
         imgView.setImageBitmap(img);
       } else if(requestCode == REQUEST_BOOKLIST) {
         int selectedItemId = data.getIntExtra("selectedItemId", 0);
       }
    }
  }
}
