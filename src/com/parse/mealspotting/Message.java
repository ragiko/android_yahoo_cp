package com.parse.mealspotting;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Contact")
public class Message extends ParseObject {

	public Message() {
		// A default constructor is required.
	}

	public String getId() {
		return getObjectId();
	}

	public void setId(String id) {
		put("id", id);
	}

	public ParseUser getFromUser() {
		return getParseUser("fromUser");
	}

	public void setFromUser(ParseUser user) {
		put("fromUser", user);
	}
	
	public ParseUser getToUser() {
		return getParseUser("toUser");
	}

	public void setToUser(ParseUser user) {
		put("toUser", user);
	}
	
	public ParseObject getBook() {
		return getParseObject("textBook");
	}

	public void setBook(ParseObject book) {
		put("textBook", book);
	}

	public String getMessage() {
		return getString("message");
	}

	public void setMessage(String message) {
		put("message", message);
	}

}
