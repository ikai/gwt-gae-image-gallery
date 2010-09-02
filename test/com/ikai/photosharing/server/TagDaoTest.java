package com.ikai.photosharing.server;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.ikai.photosharing.shared.Tag;
import com.ikai.photosharing.shared.UploadedImage;

public class TagDaoTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	DatastoreService datastore;
	@Before
	public void setUp() {
		helper.setUp();
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	@Test
	public void testPut() {
		
		TagDao dao = new TagDao();
		Tag tag = new Tag();
		tag.setBody("tag body");
		tag.setCreatedAt(new Date());
		tag.setPhotoKey("photoKey");
		tag.setTaggerId("1");
		
		String key = dao.put(tag);
		
		Key rawKey = null;
		
		try {
			rawKey = KeyFactory.stringToKey(key);
		} catch (NullPointerException e) {
			Assert.fail("put() returned null key");
		}
		
		try {
			Entity entity = datastore.get(rawKey);
			
			assertEquals("body not set", tag.getBody(), (String) entity.getProperty("body"));
			assertEquals("createdAt not set", tag.getCreatedAt(), (Date) entity.getProperty("createdAt"));
			assertEquals("taggerId not set", tag.getTaggerId(), (String) entity.getProperty("taggerId"));			
			assertEquals("photoKey not set", tag.getPhotoKey(), (String) entity.getProperty("photoKey"));
		} catch (EntityNotFoundException e) {
			Assert.fail("Entity not saved correctly");
		}
		
		
		
	}
}
