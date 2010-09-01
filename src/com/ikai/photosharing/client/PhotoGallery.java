package com.ikai.photosharing.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ikai.photosharing.client.services.UserImageService;
import com.ikai.photosharing.client.services.UserImageServiceAsync;
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
	
	PhotoSharing parent;
	
	public PhotoGallery(PhotoSharing parent) {
		this.parent = parent;
		
		initWidget(uiBinder.createAndBindUi(this));
		refreshGallery();
	}

	public void refreshGallery() {
		userImageService
				.getRecentlyUploaded(new AsyncCallback<List<UploadedImage>>() {

					@Override
					public void onSuccess(List<UploadedImage> images) {
						
						int currentColumn = 0;
						int currentRow = 0;
						for (final UploadedImage image : images) {

							Image imageWidget = createImageWidget(image);

							galleryTable.setWidget(currentRow, currentColumn,
									imageWidget);

							currentColumn++;
							if (currentColumn >= GALLERY_WIDTH) {
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

	private Image createImageWidget(final UploadedImage image) {
		final Image imageWidget = new Image();
		imageWidget.setUrl(image.getServingUrl() + "=s200");
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);

		imageWidget.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + source.getOffsetHeight()
						+ 10;

				simplePopup.setWidth("150px");
				simplePopup.setWidget(new HTML("Uploaded: "
						+ image.getCreatedAt()));
				simplePopup.show();
				simplePopup.setPopupPosition(left, top);
			}
		});

		imageWidget.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				simplePopup.hide();
			}
		});

		imageWidget.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ImageOverlay imageOverlay = new ImageOverlay(image, parent.getLoginInfo());
				
				final PopupPanel imagePopup = new PopupPanel(true);
				imagePopup.setAnimationEnabled(true);
				imagePopup.setWidget(imageOverlay);
				imagePopup.setGlassEnabled(true);
				imagePopup.setAutoHideEnabled(true);

				imagePopup.center();
			}
		});

		return imageWidget;
	}

}
