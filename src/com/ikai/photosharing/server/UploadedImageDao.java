package com.ikai.photosharing.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.ikai.photosharing.shared.UploadedImage;

public class UploadedImageDao {

	DatastoreService datastore;

	public UploadedImageDao() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UploadedImage get(String encodedKey) {
		Key key = KeyFactory.stringToKey(encodedKey);
		try {
			Entity result = datastore.get(key);
			UploadedImage image = fromEntity(result);
			image.setKey(encodedKey);
			return image;
		} catch (EntityNotFoundException e) {
			return null;
		}

	}

	public List<UploadedImage> getRecent() {
		Query query = new Query("UploadedImage");
		query.addSort(UploadedImage.CREATED_AT, SortDirection.DESCENDING);
		FetchOptions options = FetchOptions.Builder.withLimit(25);

		ArrayList<UploadedImage> results = new ArrayList<UploadedImage>();
		for (Entity result : datastore.prepare(query).asIterable(options)) {
			UploadedImage image = fromEntity(result);
			results.add(image);
		}
		return results;
	}
	
	public void delete(String encodedKey) {
		Key key = KeyFactory.stringToKey(encodedKey);
		datastore.delete(key);
	}

	private UploadedImage fromEntity(Entity result) {
		UploadedImage image = new UploadedImage();
		image.setCreatedAt((Date) result.getProperty(UploadedImage.CREATED_AT));
		image.setServingUrl((String) result
				.getProperty(UploadedImage.SERVING_URL));
		
		image.setOwnerId((String) result.getProperty(UploadedImage.OWNER_ID));

		if (image.getKey() == null) {
			String encodedKey = KeyFactory.keyToString(result.getKey());
			image.setKey(encodedKey);
		}

		return image;
	}
	
	

}
