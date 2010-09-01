package com.ikai.photosharing.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ikai.photosharing.shared.LoginInfo;

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<LoginInfo> async);
}