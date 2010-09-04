package com.ikai.photosharing.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ikai.photosharing.client.services.LoginService;
import com.ikai.photosharing.client.services.LoginServiceAsync;
import com.ikai.photosharing.client.widgets.PhotoGallery;
import com.ikai.photosharing.client.widgets.PhotoShare;
import com.ikai.photosharing.shared.LoginInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

	// Login code shamelessly stolen from:
	// http://code.google.com/webtoolkit/doc/latest/tutorial/appengine.html
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Sign in to upload images!");
	private Anchor signInLink = new Anchor("Sign In");

	private PhotoGallery galleryWidget;
	private PhotoShare uploadWidget;

	public void onModuleLoad() {
		galleryWidget = new PhotoGallery(this);

		RootPanel.get("gallery").add(galleryWidget);

		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {

					@Override
					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							uploadWidget = new PhotoShare(result);
							
							// Bind it to event so uploadWidget can refresh the gallery
							uploadWidget.addGalleryUpdatedEventHandler(galleryWidget);
							
							RootPanel.get("photoSharing").add(uploadWidget);
						} else {
							loadLogin();
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

	}

	// TODO: Turn login into a UiBinder
	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("photoSharing").add(loginPanel);
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
	
}
