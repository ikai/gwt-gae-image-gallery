package com.ikai.photosharing.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.ikai.photosharing.shared.UploadedImage;

public class PhotoShare extends Composite {

	private static PhotoShareUiBinder uiBinder = GWT
			.create(PhotoShareUiBinder.class);

	UserImageServiceAsync userImageService = GWT.create(UserImageService.class);

	interface PhotoShareUiBinder extends UiBinder<Widget, PhotoShare> {
	}

	@UiField
	Button uploadButton;

	@UiField
	FormPanel uploadForm;

	@UiField
	Label uploadLabel;

	@UiField
	FileUpload uploadField;

	@UiField
	Image uploadedImage;

	public PhotoShare() {
		initWidget(uiBinder.createAndBindUi(this));

		uploadButton.setText("Upload");
		uploadButton.setText("Loading...");
		uploadButton.setEnabled(false);

		uploadField.setName("image");

		startNewBlobstoreSession();

		uploadForm
				.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						uploadForm.reset();
						startNewBlobstoreSession();

						String key = event.getResults();

						userImageService.get(key,
								new AsyncCallback<UploadedImage>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(UploadedImage result) {
										uploadedImage.setUrl(result
												.getServingUrl());

										// TODO: Add something here that says,
										// hey, upload succeeded

										final PopupPanel imagePopup = new PopupPanel(
												true);
										imagePopup.setAnimationEnabled(true);
										imagePopup.setWidget(uploadedImage);
										imagePopup.setGlassEnabled(true);
										imagePopup.setAutoHideEnabled(true);

										imagePopup.center();

									}
								});

					}
				});
	}

	private void startNewBlobstoreSession() {
		userImageService.getBlobstoreUploadUrl(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				uploadLabel.setText("Upload target: " + result);

				uploadForm.setAction(result);
				uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
				uploadForm.setMethod(FormPanel.METHOD_POST);

				uploadButton.setText("Upload");
				uploadButton.setEnabled(true);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	@UiHandler("uploadButton")
	void onSubmit(ClickEvent e) {
		uploadForm.submit();
	}

}
