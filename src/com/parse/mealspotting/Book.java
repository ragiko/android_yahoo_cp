package com.parse.mealspotting;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Textbook")
public class Book extends ParseObject {

	public Book() {
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

	public String getPublisher() {
    return getString("publisher");
  }

  public void setPublisher(String publisher) {
    put("publisher", publisher);
  }

	public ParseFile getBookThumb() {
		return getParseFile("text_thumb");
	}

	public void setBookThumb(ParseFile file) {
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

	public String getDepartment() {
		return getString("department");
	}

	public void setDepartment(String department) {
		put("department", department);
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

	public String getBookThumbUrl() {
		return getString("text_thumb_url");
	}

	public void setBookThumbUrl(String url) {
		put("text_thumb_url", url);
	}

}
