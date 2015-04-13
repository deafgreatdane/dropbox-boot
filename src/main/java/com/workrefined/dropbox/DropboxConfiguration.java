package com.workrefined.dropbox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfiguration {
	@Bean
	public DropboxClientFactory dropboxClientFactory() {
		return new DropboxClientFactory();
	}
	
	@Bean
	public DropboxConfigForm dropboxConfigForm() {
		return new DropboxConfigForm();
	}
}
