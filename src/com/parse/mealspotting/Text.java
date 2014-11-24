package com.parse.mealspotting;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Meal 
 */

@ParseClassName("Text")
public class Text extends ParseObject {

	public Text() {
		// A default constructor is required.
	}
	
	public String getId() {
		return getObjectId();
	}

	public void setId(String id) {
		put("id", id);
	}

	public String getTitle() {
		return getString("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}
	
	public String getAuthor() {
		return getString("author");
	}

	public void setAuthor(String author) {
		put("author", author);
	}
	
	public String getBody() {
		return getString("body");
	}

	public void setBody(String body) {
		put("body", body);
	}

	public ParseFile getTextThumb() {
		return getParseFile("text_thumb");
	}

	public void setTextThumb(ParseFile file) {
		put("text_thumb", file);
	}
	
	public ParseFile getPicture() {
		return getParseFile("picture");
	}

	public void setPicture(ParseFile file) {
		put("picture", file);
	}
	
	public Integer getPrice() {
		return getInt("price");
	}

	public void setPrice(int price) {
		put("price", price);
	}
	
	public String getUniversity() {
		return getString("university");
	}

	public void setUniversity(String university) {
		put("university", university);
	}
	
	public String getDepertment() {
		return getString("depertment");
	}

	public void setDepertment(String depertment) {
		put("depertment", depertment);
	}
	
	public Integer getYear() {
		return getInt("year");
	}

	public void setYear(int year) {
		put("year", year);
	}
	
	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser user) {
		put("user", user);
	}

}
