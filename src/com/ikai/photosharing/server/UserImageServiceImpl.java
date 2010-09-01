package com.ikai.photosharing.server;

import java.util.List;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ikai.photosharing.client.services.UserImageService;
import com.ikai.photosharing.shared.UploadedImage;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends RemoteServiceServlet implements
		UserImageService {

	@Override
	public String getBlobstoreUploadUrl() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		return blobstoreService.createUploadUrl("/upload");
	}

	@Override
	public UploadedImage get(String key) {
		UploadedImageDao dao = new UploadedImageDao();
		UploadedImage image = dao.get(key);
		return image;
	}

	@Override
	public List<UploadedImage> getRecentlyUploaded() {
		UploadedImageDao dao = new UploadedImageDao();
		List<UploadedImage> images = dao.getRecent(); 
		return images;
	}

}
