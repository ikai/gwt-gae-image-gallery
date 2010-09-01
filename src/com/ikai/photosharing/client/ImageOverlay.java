package com.ikai.photosharing.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ikai.photosharing.shared.UploadedImage;

public class ImageOverlay extends Composite {

	private static ImageOverlayUiBinder uiBinder = GWT
			.create(ImageOverlayUiBinder.class);

	interface ImageOverlayUiBinder extends UiBinder<Widget, ImageOverlay> {
	}

	@UiField
	Button deleteButton;
	
	@UiField
	Image image;
	
	@UiField
	Label timestamp;
	
	UploadedImage uploadedImage;
	LoginInfo loginInfo;

	public ImageOverlay(UploadedImage uploadedImage, LoginInfo loginInfo) {
		this.uploadedImage = uploadedImage;
		this.loginInfo = loginInfo;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		image.setUrl(uploadedImage.getServingUrl());
		timestamp.setText("Created at:" + uploadedImage.getCreatedAt());
		
		
		if(loginInfo != null && (loginInfo.getId().equals(uploadedImage.getOwnerId()))) {
			deleteButton.setText("Delete image");
			deleteButton.setVisible(true);
		} else {
			deleteButton.setVisible(false);
		}
		
	}

	@UiHandler("deleteButton")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
