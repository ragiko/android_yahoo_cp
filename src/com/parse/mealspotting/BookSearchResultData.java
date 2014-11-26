package com.parse.mealspotting;


public class BookSearchResultData {
    private String titleData_;
    private String authorData_;
    private String publisherData_;

    public void setTitleData(String text) {
    	titleData_ = text;
    }

    public String getTitleData() {
        return titleData_;
    }

    public void setAuthorData(String text) {
    	authorData_ = text;
    }

    public String getAuthorData() {
        return authorData_;
    }

    public void setPublisherData(String text) {
      publisherData_ = text;
    }

    public String getPublisherData() {
        return publisherData_;
    }
}
