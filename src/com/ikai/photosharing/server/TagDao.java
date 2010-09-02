package com.ikai.photosharing.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.ikai.photosharing.shared.Tag;

public class TagDao {

	DatastoreService datastore;
	
	public TagDao() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	
	/**
	 * @param tag The Tag object we want persisted
	 * @return a String encoded Key for the saved object
	 */
	public String put(Tag tag) {
		Entity tagEntity = new Entity(Tag.class.getSimpleName());
		
		// TODO: Make this part of an Entity Group with UploadedImage as a parent
		tagEntity.setProperty("createdAt", tag.getCreatedAt());
		tagEntity.setProperty("taggerId", tag.getTaggerId());
		tagEntity.setProperty("photoKey", tag.getPhotoKey());
		
		tagEntity.setUnindexedProperty("body", tag.getBody());
		tagEntity.setUnindexedProperty("x", tag.getX());
		tagEntity.setUnindexedProperty("y", tag.getY());
		
		Key key = datastore.put(tagEntity);
		String encodedKey = KeyFactory.keyToString(key);
		return encodedKey;
	}
	
}
