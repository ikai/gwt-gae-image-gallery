package com.ikai.photosharing.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ikai.photosharing.client.services.UserImageService;
import com.ikai.photosharing.client.services.UserImageServiceAsync;
import com.ikai.photosharing.shared.LoginInfo;
import com.ikai.photosharing.shared.Tag;
import com.ikai.photosharing.shared.UploadedImage;

/**
 * 
 * This class represents the ImageOverlay that pops up when a User clicks on an
 * Image. It also provides listeners for management, tagging, and other
 * functions which are considered "Menu" type functions for a given image.
 * 
 * @author Ikai Lan
 * 
 */
public class ImageOverlay extends Composite {

	private static ImageOverlayUiBinder uiBinder = GWT
			.create(ImageOverlayUiBinder.class);

	UserImageServiceAsync imageService = GWT.create(UserImageService.class);

	interface ImageOverlayUiBinder extends UiBinder<Widget, ImageOverlay> {
	}

	@UiField
	Button deleteButton;

	@UiField
	Image image;

	@UiField
	Label timestamp;

	protected UploadedImage uploadedImage;
	LoginInfo loginInfo;
	PhotoGallery gallery;

	public ImageOverlay(UploadedImage uploadedImage, LoginInfo loginInfo,
			PhotoGallery gallery) {
		this.gallery = gallery;
		this.uploadedImage = uploadedImage;
		this.loginInfo = loginInfo;

		initWidget(uiBinder.createAndBindUi(this));

		image.setUrl(uploadedImage.getServingUrl());
		timestamp.setText("Created at:" + uploadedImage.getCreatedAt());

		if (loginInfo != null
				&& (loginInfo.getId().equals(uploadedImage.getOwnerId()))) {
			deleteButton.setText("Delete image");
			deleteButton.setVisible(true);
		} else {
			deleteButton.setVisible(false);
		}

	}

	@UiHandler("image")
	void onClickImage(MouseDownEvent e) {
		Element imageElement = e.getRelativeElement();
		int x = e.getRelativeX(imageElement);
		int y = e.getRelativeY(imageElement);
		// Window.alert("X: " + x + " Y: "+ y);
		TagDialog tagDialog = new TagDialog(image, x, y);
		tagDialog.showAndFocus();

	}

	/**
	 * 
	 * Handles clicking of the delete button if owned.
	 * 
	 * @param {{@link ClickEvent} e
	 */
	@UiHandler("deleteButton")
	void onClick(ClickEvent e) {
		final ImageOverlay overlay = this;
		imageService.deleteImage(uploadedImage.getKey(),
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						gallery.refreshGallery();
						overlay.removeFromParent();
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * @author Ikai Lan
	 *
	 *	 This is the dialog box to ask the user to tag the image
	 */
	private class TagDialog extends DialogBox {
		
		private TextBox textBox;

		public TagDialog(Image image, final int x, final int y) {

			// Set the dialog box's caption.
			setText("Tagging X: " + x + " Y: " + y);
			
			setAutoHideEnabled(true);

			int dialogX = image.getAbsoluteLeft() + x;
			int dialogY = image.getAbsoluteTop() + y;

			setPopupPosition(dialogX, dialogY);

			VerticalPanel tagPanel = new VerticalPanel();

			textBox = new TextBox();
			tagPanel.add(textBox);

			textBox.addKeyPressHandler(new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getCharCode() == KeyCodes.KEY_ENTER) {
						saveTag(x, y, textBox);
					}

				}
			});

			Button ok = new Button("Tag");
			ok.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					saveTag(x, y, textBox);
				}
			});

			tagPanel.add(ok);

			setWidget(tagPanel);
			
		}
		
		public void showAndFocus() {
			show();
			textBox.setFocus(true);
		}

		private void saveTag(final int x, final int y, final TextBox textBox) {
			Tag tag = new Tag();

			// TODO: Change this to also pass the Image
			tag.setPhotoKey(uploadedImage.getKey());
			tag.setBody(textBox.getValue());
			tag.setX(x);
			tag.setY(y);

			imageService.tagImage(tag, new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(String result) {
					TagDialog.this.hide();
				}
			});
		}
	}

}
