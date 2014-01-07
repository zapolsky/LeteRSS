package com.zapolsky.leterss;

public class RssFeed {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String title;
	private String url;
	
	@Override
	public String toString() {
		
		return title;
	}
}
