package com.ikai.photosharing.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.ikai.photosharing.shared.Tag;
import com.ikai.photosharing.shared.UploadedImage;

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
	
	public List<Tag> getForImage(UploadedImage image) {
		Query query = new Query(Tag.class.getSimpleName());
		query.addFilter("photoKey", FilterOperator.EQUAL, image.getKey());
		
		List<Tag> results = new ArrayList<Tag>();
		
		for(Entity entity : datastore.prepare(query).asIterable(FetchOptions.Builder.withDefaults())) {
			Tag tag = new Tag();
			tag.setPhotoKey((String) entity.getProperty("photoKey"));
			tag.setBody((String) entity.getProperty("body"));
			tag.setCreatedAt((Date) entity.getProperty("createdAt"));
			tag.setTaggerId((String) entity.getProperty("taggerId"));
			tag.setX((Long) entity.getProperty("x"));
			tag.setY((Long) entity.getProperty("y"));
			results.add(tag);
		}
		
		return results;
	}
	
}
