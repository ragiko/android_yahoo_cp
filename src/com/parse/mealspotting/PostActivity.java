package com.parse.mealspotting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		meal = new Meal();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		// Begin with main data entry view,
		// NewMealFragment
		setContentView(R.layout.activity_new_meal);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer2);

		if (fragment == null) {
			fragment = new NewMealFragment();
			manager.beginTransaction().add(R.id.fragmentContainer2, fragment)
					.commit();
		}

		// ボタンのViewを取得
		Button changeUnivButton = (Button)findViewById(R.id.button_change_university);
		Button changeDepButton = (Button)findViewById(R.id.button_change_department);
		Button searchButton = (Button)findViewById(R.id.button_search_post);
		Button barcodeButton = (Button)findViewById(R.id.button_barcode_post);
		Button cameraButton = (Button)findViewById(R.id.button_camera_post);
		Button galleryButton = (Button)findViewById(R.id.button_gallery_post);
		Button submitButton = (Button)findViewById(R.id.button_submit_post);

		// テキストビューのViewを取得
		TextView univTextView = (TextView)findViewById(R.id.textview_university);
		TextView depTextView = (TextView)findViewById(R.id.textview_department);

		// エディットテキストのViewを取得
		EditText searchEditText = (EditText)findViewById(R.id.edittext_search_post);
		EditText detailEditText = (EditText)findViewById(R.id.edittext_detail_post);
		EditText priceEditText = (EditText)findViewById(R.id.edittext_price_post);

		// スピナーのViewを取得
		Spinner yearSpinner = (Spinner)findViewById(R.id.spinner_year_post);

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

      }
    });

		galleryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // ギャラリーから写真を選択してアップする

      }
    });

		submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 投稿する

      }
    });
	}

	public Meal getCurrentMeal() {
		return meal;
	}

}
