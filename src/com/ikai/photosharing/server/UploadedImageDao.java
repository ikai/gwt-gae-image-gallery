package com.ikai.photosharing.server;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.ikai.photosharing.shared.UploadedImage;

public class UploadedImageDao {

	DatastoreService datastore;
	
	public UploadedImageDao(){
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	public UploadedImage get(String encodedKey) {
		Key key = KeyFactory.stringToKey(encodedKey);
		try {
			Entity result = datastore.get(key);
			UploadedImage image = new UploadedImage();
			image.setKey(encodedKey);
			image.setCreatedAt((Date) result.getProperty(UploadedImage.CREATED_AT));
			image.setServingUrl((String) result.getProperty(UploadedImage.SERVING_URL));
			return image;
		} catch (EntityNotFoundException e) {
			return null;
		}
			
	}
}

