package com.workrefined.dropbox;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;

@Component
@ConfigurationProperties(prefix="com.workrefined.dropbox")
public class DropboxClientFactory {
	
	@Value("{clientIntifier}")
	private String clientIdentifier;
	@Value("{appKey}")
	private String appKey;
	@Value("{appSecret}")
	private String appSecret;
	@Value("{accessToken}")
	private String accessToken;

	public DropboxClientFactory() {}
	
	public DbxClient getClient() {
		DbxRequestConfig config = new DbxRequestConfig(clientIdentifier, Locale.getDefault().toString());
		return new DbxClient(config, accessToken);
	}
	


	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}
}
