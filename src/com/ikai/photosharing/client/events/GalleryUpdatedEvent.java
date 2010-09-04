package com.ikai.photosharing.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class GalleryUpdatedEvent extends GwtEvent<GalleryUpdatedEventHandler> {

    public static Type<GalleryUpdatedEventHandler> TYPE = new Type<GalleryUpdatedEventHandler>();

	@Override
	public Type<GalleryUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GalleryUpdatedEventHandler handler) {
		handler.onGalleryUpdated(this);
	}

}
