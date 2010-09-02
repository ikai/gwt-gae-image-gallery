package com.ikai.photosharing.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * A single Tag for a given Photo.
 * 
 * @author Ikai Lan
 * 
 */
@SuppressWarnings("serial")
public class Tag implements Serializable {

	String photoKey;

	/**
	 * User ID of the User that created this tag
	 */
	String taggerId;

	String body;

	Date createdAt;

	Integer x;
	Integer y;

	public String getPhotoKey() {
		return photoKey;
	}

	public void setPhotoKey(String photoKey) {
		this.photoKey = photoKey;
	}

	public String getTaggerId() {
		return taggerId;
	}

	public void setTaggerId(String taggerId) {
		this.taggerId = taggerId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
