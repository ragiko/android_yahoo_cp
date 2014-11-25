package com.parse.mealspotting;

import java.io.InputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 *
 * 出品情報投稿画面のアクティビティ
 */
public class PostActivity extends Activity {

	private Meal meal;

	//インテントが返って来た時の判別コード
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CAMERA = 1;

	// View
	private Button changeUnivButton, changeDepButton, searchButton, barcodeButton;
  private Button cameraButton, galleryButton, submitButton;
  private TextView univTextView, depTextView;
  private EditText searchEditText, detailEditText, priceEditText;
  private NumberPicker yearPicker;

  //日時・時刻を取得するためのインスタンス
  private Calendar obj_cd = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//meal = new Meal();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_post);
		findViews();

		// ボタンにクリックリスナーを登録
		changeUnivButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 大学名を変更

      }
    });

		changeDepButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 学部を変更

      }
    });

		searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 書籍を検索
        String keyword = searchEditText.getText().toString();

        // APIのURLを生成
        String endpoint = (String)getText(R.string.amazon_api_endpoint);
        String service = (String)getText(R.string.amazon_api_service);
        String key = (String)getText(R.string.amazon_api_key);
        String version = (String)getText(R.string.amazon_api_version);
        String operation = (String)getText(R.string.amazon_api_operation);
        String searchIndex = (String)getText(R.string.amazon_api_searchindex);
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http");
        uriBuilder.encodedAuthority(endpoint);
        uriBuilder.appendQueryParameter("Service", service);
        uriBuilder.appendQueryParameter("AWSAccessKeyId", key);
        uriBuilder.appendQueryParameter("Operation", operation);
        uriBuilder.appendQueryParameter("SearchIndex", searchIndex);
        uriBuilder.appendQueryParameter("Title", keyword);
        String uriStr = uriBuilder.toString();
        Log.d("OK", uriStr);
/*
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
                  String dataStr = result.getString("data");
                  JSONObject dataJSON = new JSONObject(dataStr);
                  youtubeID = dataJSON.getString("youtube_id");
                  artist = dataJSON.getString("artist");
                  title = dataJSON.getString("title");
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
          */
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
        // 投稿する
        String detail = detailEditText.getText().toString();
        String price = priceEditText.getText().toString();
        int year = yearPicker.getValue();
      }
    });

		// NumberPickerの設定
		int presentYear = obj_cd.get(Calendar.YEAR);
		int startYear = presentYear - 10;
		Log.d("OK", Integer.toString(presentYear));
		yearPicker.setMaxValue(presentYear);
		yearPicker.setMinValue(startYear);
	}

	// Viewの取得
	private void findViews() {
	  // ボタンのViewを取得
    changeUnivButton = (Button)findViewById(R.id.button_change_university);
    changeDepButton = (Button)findViewById(R.id.button_change_department);
    searchButton = (Button)findViewById(R.id.button_search_post);
    barcodeButton = (Button)findViewById(R.id.button_barcode_post);
    cameraButton = (Button)findViewById(R.id.button_camera_post);
    galleryButton = (Button)findViewById(R.id.button_gallery_post);
    submitButton = (Button)findViewById(R.id.button_submit_post);

    // テキストビューのViewを取得
    univTextView = (TextView)findViewById(R.id.textview_university);
    depTextView = (TextView)findViewById(R.id.textview_department);

    // エディットテキストのViewを取得
    searchEditText = (EditText)findViewById(R.id.edittext_search_post);
    detailEditText = (EditText)findViewById(R.id.edittext_detail_post);
    priceEditText = (EditText)findViewById(R.id.edittext_price_post);

    // ナンバーピッカーのViewを取得
    yearPicker = (NumberPicker)findViewById(R.id.numpicker_year_post);
	}

	//インテント先から結果が返ってきたときの処理
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK) {
       ImageView imgView = (ImageView)findViewById(R.id.image_selected);
       if(requestCode == REQUEST_GALLERY) {  // ギャラリーで画像が選択された
       try {
         InputStream in = getContentResolver().openInputStream(data.getData());
         Bitmap img = BitmapFactory.decodeStream(in);
         in.close();
         imgView.setImageBitmap(img);
       } catch(Exception e) {

       }
     } else if(requestCode == REQUEST_CAMERA) {  // カメラで写真が撮影された
         Bitmap img = (Bitmap)data.getExtras().get("data");  // カメラで撮影された写真を取得
         imgView.setImageBitmap(img);
       }
    }
  }

	public Meal getCurrentMeal() {
		return meal;
	}

}
