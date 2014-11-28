package com.parse.mealspotting;

import android.app.Application;
import android.net.ParseException;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MealSpottingApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		/*
		 * In this tutorial, we'll subclass ParseObject for convenience to
		 * create and modify Meal objects
		 */
		ParseObject.registerSubclass(Meal.class);
		ParseObject.registerSubclass(Book.class);
		ParseObject.registerSubclass(Deal.class);

		/*
		 * Fill in this section with your Parse credentials
		 */
		Parse.initialize(this, "b5KZBDCrwsDrRyYhGitOz9sXNOhzIqbBchdUBWWc", "RPiFbq86gt2PmGwYqtGIiwct2behPH1IAPzcBiOa");

		
//		ParseUser.logInInBackground("tag", "hy6ju7ki",
//				new LogInCallback() {
//					@Override
//					public void done(ParseUser user, com.parse.ParseException e) {
//						// TODO Auto-generated method stub
//						if (user != null) {
//							// Hooray! The user is logged in.
//						} else {
//							// Signup failed. Look at the ParseException to see
//							// what happened.
//						}
//					}
//			});
		

//	    ParseACL defaultACL = new ParseACL();
//	    ParseACL.setDefaultACL(defaultACL, true);
//	    
//	    ParseUser.enableAutomaticUser();
//	    ParseUser.getCurrentUser().increment("RunCount");
//	    ParseUser.getCurrentUser().saveInBackground();
		
	}

}
