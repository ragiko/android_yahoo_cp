package com.parse.mealspotting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
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
	}

	public Meal getCurrentMeal() {
		return meal;
	}

}
