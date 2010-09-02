package com.ikai.photosharing.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ikai.photosharing.shared.Tag;
import com.ikai.photosharing.shared.UploadedImage;

public interface UserImageServiceAsync {

	public void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<UploadedImage> callback);

	void getRecentlyUploaded(AsyncCallback<List<UploadedImage>> callback);

	void deleteImage(String key, AsyncCallback<Void> callback);

	void tagImage(Tag tag,
			AsyncCallback<String> callback);


}
