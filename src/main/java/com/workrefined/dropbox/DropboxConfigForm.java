package com.workrefined.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

/**
 * A utility method to create a fragment of an application.properties file
 * needed for configuring the Dropbox DbxClient.
 */
@Component
@ConfigurationProperties(prefix="com.workrefined.dropbox")
public class DropboxConfigForm {

//	@Value("${clientIdentifier}")
	private String clientIdentifier;
//	@Value("${appKey}")
	private String appKey;
//	@Value("${appSecret}")
	private String appSecret;
//	@Value("${accessToken}")
	private String accessToken;

	/**
	 * A utility method to create a fragment of an application.properties file
	 * needed for configuring the Dropbox DbxClient.
	 * @throws DbxException 
	 */
	public void prompt() throws IOException, DbxException {
		String localClientIdentifier = getAnswer("How should this app identify itself to Dropbox?", clientIdentifier);
		String localAppKey = getAnswer("Enter your app key", appKey);
		String localAppSecret = getAnswer("Enter your app secrete", appSecret);

		DbxAppInfo appInfo = new DbxAppInfo(localAppKey, localAppSecret);

		DbxRequestConfig config = new DbxRequestConfig(localClientIdentifier, Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		// Have the user sign in and authorize your app.
		String authorizeUrl = webAuth.start();
		System.out.println("Now go to: \n\t" + authorizeUrl);
		System.out.println("(you might have to login first) then click \"allow\"2. Click \"Allow\"");
		String code = getAnswer("Enter the authcode here", null ) ;

		// This will fail if the user enters an invalid authorization code.
		DbxAuthFinish authFinish = webAuth.finish(code);
		String localAccessToken = authFinish.accessToken;

		DbxClient client = new DbxClient(config, localAccessToken);

		System.out.println("Excellent! You linked account: " + client.getAccountInfo().displayName);
		
		System.out.println("Here is a fragment of values you can enter into your application.properties file");
		System.out.println("########");
		System.out.println("com.workrefined.dropbox.clientIdentifier="+ localClientIdentifier);
		System.out.println("com.workrefined.dropbox.appKey="+ localAppKey);
		System.out.println("com.workrefined.dropbox.appSecret="+ localAppSecret);
		System.out.println("com.workrefined.dropbox.accessToken="+ localAccessToken);
		System.out.println("########");
	}

	public String getAnswer(String prompt, String defaultAnswer) throws IOException {
		if (!StringUtils.isEmpty(defaultAnswer)) {
			System.out.println(prompt + " [" + defaultAnswer + "]");
		} else {
			System.out.println(prompt);
		}
		String answer = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		if (! StringUtils.isEmpty(answer)) {
			return answer ;
		} else if ( ! StringUtils.isEmpty(defaultAnswer)) {
			return defaultAnswer;
		}
		System.out.println("try again...");
		return getAnswer(prompt, defaultAnswer);

	}
}
