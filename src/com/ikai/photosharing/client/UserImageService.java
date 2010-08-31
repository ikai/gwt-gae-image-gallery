package com.ikai.photosharing.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ikai.photosharing.shared.UploadedImage;

@RemoteServiceRelativePath("images")
public interface UserImageService extends RemoteService  {
	
	public String getBlobstoreUploadUrl();
	public UploadedImage get(String key);

}
