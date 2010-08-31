package com.ikai.photosharing.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ikai.photosharing.shared.UploadedImage;

public interface UserImageServiceAsync {

	public void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<UploadedImage> callback);

}
