package com.ikai.photosharing.shared;

import java.io.Serializable;
import java.util.Date;

public class UploadedImage implements Serializable {

	public static final String SERVING_URL = "servingUrl";
	public static final String CREATED_AT = "createdAt";
	
	String key;
	String servingUrl;
	Date createdAt;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getServingUrl() {
		return servingUrl;
	}
	public void setServingUrl(String servingUrl) {
		this.servingUrl = servingUrl;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
