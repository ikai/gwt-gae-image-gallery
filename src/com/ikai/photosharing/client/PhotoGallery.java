package com.ikai.photosharing.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.ikai.photosharing.shared.UploadedImage;

public class PhotoGallery extends Composite {

	private static PhotoGalleryUiBinder uiBinder = GWT
			.create(PhotoGalleryUiBinder.class);

	UserImageServiceAsync userImageService = GWT.create(UserImageService.class);

	interface PhotoGalleryUiBinder extends UiBinder<Widget, PhotoGallery> {
	}

	private static final int GALLERY_WIDTH = 5;

	@UiField
	FlexTable galleryTable;

	public PhotoGallery() {
		initWidget(uiBinder.createAndBindUi(this));
		
		userImageService.getRecentlyUploaded(new AsyncCallback<List<UploadedImage>>() {
			
			@Override
			public void onSuccess(List<UploadedImage> images) {
				int currentColumn = 0;
				int currentRow = 0;
				for(UploadedImage image : images) {
					galleryTable.setText(currentRow, currentColumn, "Some text here");
					
					currentColumn++;
					if(currentColumn >= GALLERY_WIDTH) {
						currentColumn = 0;
						currentRow++;
					}
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
