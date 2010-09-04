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

	Long x;
	Long y;

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

	public Long getX() {
		return x;
	}

	public void setX(Number x) {
		this.x = x.longValue();
	}

	public Long getY() {
		return y;
	}

	public void setY(Number y) {
		this.y = y.longValue();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((photoKey == null) ? 0 : photoKey.hashCode());
		result = prime * result
				+ ((taggerId == null) ? 0 : taggerId.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tag other = (Tag) obj;
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		if (createdAt == null) {
			if (other.createdAt != null) {
				return false;
			}
		} else if (!createdAt.equals(other.createdAt)) {
			return false;
		}
		if (photoKey == null) {
			if (other.photoKey != null) {
				return false;
			}
		} else if (!photoKey.equals(other.photoKey)) {
			return false;
		}
		if (taggerId == null) {
			if (other.taggerId != null) {
				return false;
			}
		} else if (!taggerId.equals(other.taggerId)) {
			return false;
		}
		if (x == null) {
			if (other.x != null) {
				return false;
			}
		} else if (!x.equals(other.x)) {
			return false;
		}
		if (y == null) {
			if (other.y != null) {
				return false;
			}
		} else if (!y.equals(other.y)) {
			return false;
		}
		return true;
	}

	

}
