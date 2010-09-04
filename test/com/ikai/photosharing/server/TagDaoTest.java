package com.ikai.photosharing.server;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

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

		tag.setX(100);
		tag.setY(100);

		String key = dao.put(tag);

		Key rawKey = null;

		try {
			rawKey = KeyFactory.stringToKey(key);
		} catch (NullPointerException e) {
			Assert.fail("put() returned null key");
		}

		try {
			Entity entity = datastore.get(rawKey);

			assertEquals("body not set", tag.getBody(),
					(String) entity.getProperty("body"));
			assertEquals("createdAt not set", tag.getCreatedAt(),
					(Date) entity.getProperty("createdAt"));
			assertEquals("taggerId not set", tag.getTaggerId(),
					(String) entity.getProperty("taggerId"));
			assertEquals("photoKey not set", tag.getPhotoKey(),
					(String) entity.getProperty("photoKey"));

			assertEquals("x not set", new Long(100l),
					(Long) entity.getProperty("x"));
			assertEquals("y not set", new Long(100l),
					(Long) entity.getProperty("y"));

		} catch (EntityNotFoundException e) {
			Assert.fail("Entity not saved correctly");
		}

	}
	
	@Test
	public void testGetForPhoto() throws Exception {		
		Key imageKey = KeyFactory.createKey("UploadedImage", "imageKey");
		
		Tag tag = new Tag();
		tag.setPhotoKey(KeyFactory.keyToString(imageKey));
		tag.setX(0);
		tag.setY(0);
		
		TagDao dao = new TagDao();
		dao.put(tag);
		
		UploadedImage image = new UploadedImage();
		image.setKey(KeyFactory.keyToString(imageKey));
		
		List<Tag> results = dao.getForImage(image);
		Tag result = results.get(0);
		assertEquals(tag, result);
		
		
	}
}
